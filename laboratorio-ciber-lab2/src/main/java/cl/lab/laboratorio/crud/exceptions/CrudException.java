package cl.lab.laboratorio.crud.exceptions;

public class CrudException extends RuntimeException {

    public CrudException() {
        super();
    }

    public CrudException(String message) {
        super(message);
    }

}
