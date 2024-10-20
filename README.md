# API Detección de Mutantes

Este proyecto implementa una API REST para detectar si una secuencia de ADN pertenece a un mutante o a un humano, basado en un examen de MercadoLibre. El servicio analiza cadenas de ADN y guarda los resultados en una base de datos H2.

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot**
- **H2 Database**
- **JUnit 5** para las pruebas unitarias
- **Mockito** para la simulación de dependencias en pruebas
- **Gradle** como sistema de construcción

## Estructura del proyecto

- `entities`: Define las entidades del sistema.
- `repositories`: Repositorios para interactuar con la base de datos.
- `services`: Lógica de negocio, incluyendo el servicio que detecta si una secuencia es mutante.
- `controllers`: Expone los endpoints REST.
- `test`: Pruebas unitarias para la lógica de detección de mutantes.

## Endpoints

### Verificación de ADN mutante

- **POST /mutant**
  - Recibe una secuencia de ADN en formato JSON.
  - Responde con un código 200 si es mutante, 403 si no lo es.

#### Ejemplo de request:
```json
{
    "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}
```
#### Ejemplo de response:
- Mutante (200 OK):
```json
{
    "id": 1,
    "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"],
    "mutant": true
}
```
- Humano (403 Forbidden):
```json
{
    "id": 2,
    "dna": ["AACA","CCCC","TCAG","GGTC"],
    "mutant": false
}
```
- **GET /mutant**
  - Recibe todos los ADN ingresados en formato JSON.

#### Ejemplo de response:
```json
[
  {
    "id": 2,
    "dna": [
      "AAAA",
      "CCCC",
      "TCAG",
      "GGTC"
    ],
    "mutant": true
  },
  {
    "id": 3,
    "dna": [
      "AACA",
      "CCCC",
      "TCAG",
      "GGTC"
    ],
    "mutant": false
  }
]
```
### Estadísticas

- **GET /stats**
  - Retorna estadísticas sobre las secuencias de ADN analizadas, incluyendo el porcentaje de mutantes y humanos.

#### Ejemplo de response:
```json
{
    "count_mutant_dna": 40,
    "count_human_dna": 100,
    "ratio": 0.4
}
```

## Cómo ejecutar el proyecto

### Requisitos

- Java 17 o superior.
- Gradle (se incluye wrapper en el proyecto, por lo que no es necesario tener Gradle instalado globalmente).
- Un IDE como IntelliJ IDEA o Eclipse (opcional).


### Instrucciones de ejecución

1. **Clonar el Repositorio**
  ```bash
  git clone https://github.com/lucianaortizutn/mutantes.git
  ```
2. **Ejecutar "MutantesApplication.java"**
   - La API estará disponible en http://localhost:8080/swagger-ui/index.html
3. **Acceso a la base de datos H2**
   - Puedes acceder a la consola de H2 en http://localhost:8080/h2-console/

### Instrucciones para ejecutar las pruebas
El proyecto cuenta con pruebas unitarias implementadas con **JUnit 5** y **Mockito**
Para ejecturar las pruebas, usa el siguiente comando desde la raiz del proyecto:
```bash
./gradlew test
```
