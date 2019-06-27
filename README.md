# API de personas 

_Se expone una **REST API** para realizar operaciones CRUD sobre personas, consultar un informe y la posibilidad de crear parentescos entre personas y consultarlo._

## Comenzando 🚀

_Estas instrucciones te permitirán obtener una copia del proyecto en funcionamiento en tu máquina local para propósitos de desarrollo y pruebas._

Mira **Deployment** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

_Que cosas necesitas para instalar el software_

```
Java JDK 1.8.0_171
Apache Maven 3.6.0
```

### Instalación 🔧

_Clonar el repositorio en la maquina local e instalar las dependencias con maven_

```
mvn install
```

_Luego puedes empaquetar el projecto para deployar_

```
mvn package
```

_Por ultimo, puede levantar la instancia tomcat con el siguiente comando parado en la carpeta del proyecto_

```
java -jar target/person-0.0.1-SNAPSHOT.jar
```

_Puedes utilizar las siguientes URIs desde un browser o desde un REST Client para probarlo_

```
http://localhost:8080/person            // Consulta listado de personas
http://localhost:8080/estadisticas      // Consulta el informe de personas
```
Mas documentacion en https://person-nhuallpa.herokuapp.com/swagger-ui.html

## Ejecutando las pruebas ⚙️

_Para ejecutar las pruebas unitarias y de integración, utilizar el siguiente comando_

```
mvn test
```

## Deployment 📦

_El deploy es realizado en un cuenta de heroku con el siguiente comando_

```
git push heroku master
```
_Para verificar los servicios, se exponen los siguiente recursos_

```
https://person-nhuallpa.herokuapp.com/person            // Consulta listado de personas
https://person-nhuallpa.herokuapp.com/estadisticas      // Consulta el informe de personas
```

## Construido con 🛠️

_Herramientas utilizadas en el proyecto_

* [Spring Boot](https://spring.io/projects/spring-boot) - El framework web usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Heroku](https://www.heroku.com/) - Cloud Application Platform
* [JUnit4](https://junit.org/junit4/) - JUnit
* [Gatling](https://gatling.io/) - Gatling Load test as code

## Autores ✒️


* **Huallpa Nestor** - *Trabajo Inicial* - [nhuallpa](https://www.linkedin.com/in/nestor-huallpa-7239b011)

