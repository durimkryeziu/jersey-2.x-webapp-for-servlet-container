# Sample Jersey 2.x RESTful Web Application
Sample Jersey 2.x RESTful Web Application that can be deployed in a Servlet 3.0 Container (i.e. [Tomcat 7+](http://tomcat.apache.org/tomcat-7.0-doc/), [Jetty 8+](http://www.eclipse.org/jetty/documentation/), [GlassFish 3.0.1+](https://glassfish.java.net/documentation.html) etc). 
It can be used to help you start a Jersey Webapp quickly with very few modifications on the _(NO XML)_ configuration files.

<img width="1013" alt="docs" src="https://cloud.githubusercontent.com/assets/11609385/24085938/8b4d5e3c-0d05-11e7-858f-e04d27ca5b07.png">

## Overview
- Based on Descriptor-less deployment [option](src/main/java/com/programmingskillz/SampleApplication.java) (No JAX-RS Deployment descriptor)
- Leverages [HikariCP](src/main/java/com/programmingskillz/repository/DatabaseConfig.java) to connect with H2 database (Embedded)
- Uses _YAML_ syntax for [Log4j2](src/main/resources/log4j2.yml) configuration file
- Uses [Jackson](src/main/java/com/programmingskillz/providers/SampleObjectMapperProvider.java) Library for data-binding
- Leverages [Jersey Test Framework](src/test/java/com/programmingskillz/resource/BookResourceIntegrationTest.java) for testing
- Validations are based on [Bean Validation](http://beanvalidation.org/). Uses both [Built-in](src/main/java/com/programmingskillz/domain/Book.java) constraints and [Custom](src/main/java/com/programmingskillz/constraint/ValidIsbn.java) constraints
- Utilizes Jersey Filters to support [Basic Authentication](src/main/java/com/programmingskillz/providers/AuthFilter.java)
- Supports _URI-based_ content negotiation for **JSON** and **XML**

    `GET /books.json` -- Returns JSON response
    
    `GET /books.xml` -- Returns XML response
- Utilizes Swagger for documentation

## Installation
- `git clone https://github.com/durimkryeziu/jersey-2.x-webapp-servlet-container.git`
- Point **CATALINA_HOME** environment variable to your Servlet Container for [log](src/main/resources/log4j2.yml#L8) files
- Close all other connections to the embedded mode H2 Database if any or modify the [**hikari.properties**](src/main/resources/hikari.properties) file to use the server mode
- `mvn clean install` or `mvn -Dmaven.test.skip=true clean install` to skip [tests](src/test/java/com/programmingskillz)
- Get the **war** file and deploy it on your favorite Servlet Container and you will be all set up. 

## References
- Jersey doc: https://jersey.java.net/documentation/latest/index.html
- HikariCP: http://http://brettwooldridge.github.io/HikariCP/
- H2 Database: http://www.h2database.com/html/features.html#embedded_databases
- SLF4J: https://www.slf4j.org/manual.html
- Log4j2: https://logging.apache.org/log4j/2.x/manual/index.html
- Hibernate Validator: http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/
- Swagger: https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5
- Flyway: https://flywaydb.org/documentation/

## License

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org>
