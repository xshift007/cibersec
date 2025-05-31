# Resumen de análisis SAST con Semgrep – Grupo B

| Nº de reporte | Rama / estado del código | Hallazgos totales | Hallazgos destacados |
|---------------|-------------------------|-------------------|----------------------|
| **1 – `GrupoB_SAST_reporte_1.json`** | **`main`** (Laboratorio 1 original) | **1** | • Contenedor se ejecuta como **root** (`Dockerfile` → `missing-user-entrypoint`) |
| **2 – `GrupoB_SAST_reporte_2.json`** | **baseline** de _Lab 2_ (sin vulnerabilidades añadidas) | **3** | • Mismo problema del Dockerfile<br>• 2 tokens **JWT hard-codeados** en colecciones Postman |
| **3 – `GrupoB_SAST_reporte_3.json`** | Rama **`feature/vuln_lab3`** – se **inyectan vulnerabilidades** | **6** | *Los 3 anteriores +*<br>• **Inyección SQL** en `UsuarioServiceImpl` (`formatted-sql-string`)<br>• **Criptografía débil** ×2 (uso de `MD5` en `AuthController`) |
| **4 – `GrupoB_SAST_reporte_4.json`** | Rama **`fix/laboratorio3`** – vulnerabilidades **mitigadas** | **3** | • Sólo permanecen los hallazgos del baseline (Dockerfile root + 2 JWT).<br>Los avisos de **SQLi** y **MD5** han desaparecido ✔️ |

### Evolución

1. **Base (3 findings)** – Docker como root + JWT de prueba.  
2. **Se introduce MD5 y SQL Injection** → el recuento sube a **6**.  
3. **Se corrige SQLi (consulta parametrizada) y MD5 (SHA-256/Bcrypt)** → vuelve a **3**.  
4. Pendiente de mejora: ejecutar contenedor como usuario no-root y mover/eliminar los JWT de demo para alcanzar **0 findings**.

> Estos cuatro archivos JSON permiten demostrar el ciclo completo: *detección automática → inyección de vulnerabilidad → nueva detección → mitigación y verificación*.
