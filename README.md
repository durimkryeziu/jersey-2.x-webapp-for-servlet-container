# Sample Jersey 2.x RESTful Web Application
Sample Jersey 2.x RESTful Web Application that can be deployed in a Servlet 3.0 Container. 
It can be used to help you start a Jersey Webapp quickly with very few modifications on the (NO XML) configuration files.

## Overview
- Based on Descriptor-less deployment option (No JAX-RS Deployment descriptor)
- Leverages _HikariCP_ to connect with H2 database (Embedded)
- Uses _YAML_ syntax for _Log4j2_ configuration file
- Uses _Jackson_ Library for data-binding
- Supports _URI-based_ content negotiation for _JSON_ and _XML_ (yet to come!)

## Installation
- `git clone https://github.com/durimkryeziu/jersey-2.x-webapp-servlet-container.git`
- Point **CATALINA_HOME** environment variable to your Servlet Container
- Close all other connections to the embedded mode H2 Database if any or modify the hikari.properties file to use the server mode
- `mvn clean install` or `mvn -Dmaven.test.skip=true clean install` to skip tests
- Get the _WAR_ file and deploy it on your favorite Servlet Container and you will be all set up. 

## References
- Jersey doc: https://jersey.java.net/documentation/latest/index.html
- HikariCP: http://http://brettwooldridge.github.io/HikariCP/
- H2 Database: http://www.h2database.com/html/features.html#embedded_databases
- SLF4J: https://www.slf4j.org/manual.html
- Log4j2: https://logging.apache.org/log4j/2.x/manual/index.html 

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
