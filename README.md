# Jasper Report con Laravel

## Uso

Jar el cual permite generar reportes en formatos: 
- xls
- html
- pdf

Este ejecutable en JAVA permite ejecutar las siguientes opciones:

```
 -checkdbconn               Verifica la conexion a la Base de datos
 -conn                      Indicador para la conexion a la BD
 -d <driver>                Driver JDBC de la conexion a la BD
 -dbn <db name>             Nombre de la BD
 -f <.jasper path>          Ruta absoluta del reporte Jasper
 -fd <dest path>            Ruta absoluta en donde se guardaran los reportes
 -format <format>           Formatos de los reportes a exportar
 -l <ip>                    Localhost o IP de la conexion a la BD
 -p <port>                  Numero de puerto a la BD
 -params                    Lista de parametros del reporte Jasper
 -pass <password>           Password de la conexion a la BD
 -setparams <json params>   Lista de parametros necesarios para la generacion del reporte Jasper
 -u <username>              Username de la conexion a la BD
```

## Instalacion
Desde una terminal se requiere ejecutar el siguiente comando Maven dentro de la carpeta raiz:
```
mvn clean compile assembly:single
```

Dentro de la carpeta target encontraremos el compilado *jasperReporter-jar-with-dependencies.jar*. 

