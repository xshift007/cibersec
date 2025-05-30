# 2025-01 Cybersec lab

Información necesaria para comprender mejor esta entrega parcial del Laboratorio 1 de Ciberseguridad

En la raiz del proyecto se puede ver que hay 2 pdf que son las matrices de permisos donde:

1.- el archivo Matriz_RBAC_actual.pdf actúa como la matriz AS-IS (matriz que representa el estado actual del proyecto)

2.- el archivo Matriz_RBAC.pdf actúa como la matriz TO-BE (matriz que representa el estado futuro del proyecto)

Como punto aparte, se ha modificado el pom.xml del proyecto con un plugin que permite generar documentos de javadoc mediante maven
con el comando mvn javadoc:javadoc (siempre que se tenga instalado JDK 17 y Maven en el sistema operativo y estos se encuentren habilitados
en el PATH del sistema). Este plugin ignora las warnings y errores de funciones que no se han comentado con Javadoc ya que, como se pide en
el enunciado, solo se comentó en Javadoc los módulos nuevos, es decir, todo lo relacionado a Postulante, Postulación y Beneficio.