package cl.lab.laboratorio.crud.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginCache {

    private final int MAX_ATTEMPTS = 5;
    private final long LOCK_TIME_DURATION = 15;
    private Map<String, Integer> attemptsCache = new HashMap<>();
    private Map<String, LocalDateTime> lockTimeCache = new HashMap<>();

    public void loginFailed(String username) {
        int attempts = attemptsCache.getOrDefault(username, 0);
        attemptsCache.put(username, attempts + 1);

        if (attempts + 1 >= MAX_ATTEMPTS) {
            lockTimeCache.put(username, LocalDateTime.now());
        }
    }

    public boolean isAccountLocked(String username) {
        if (!lockTimeCache.containsKey(username)) {
            return false;
        }

        LocalDateTime lockTime = lockTimeCache.get(username);
        long minutesLocked = java.time.Duration.between(lockTime, LocalDateTime.now()).toMinutes();

        if (minutesLocked > LOCK_TIME_DURATION) {
            attemptsCache.put(username, 0);
            lockTimeCache.remove(username);
            return false;
        }

        return true;
    }

    public void lockAccount(String username) {
        lockTimeCache.put(username, LocalDateTime.now());
    }

    public void loginSucceeded(String username) {
        attemptsCache.put(username, 0);
    }
}