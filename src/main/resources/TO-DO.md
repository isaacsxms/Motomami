# TO-DO

## ENDPOINT = "localhost::8080/readInfo/{resource}/{codprov}/{date}"

### - NUMERO DE LINEAS DEL FICHERO

### - NUMERO DE LINEAS INSERTADAS

### - NUMERO DE LINEAS ACTUALIZADAS

### - NUMERO DE LINEAS CON ERROR



// check if dni exists and if content json exists
// if dni and cont json are the same we don't do anything
// if dni is the same but json is different we insert an update in interface
// if none of the above are the same we insert a new interface


##### RNF16- El incumplimiento de los RNF’s (6,7,8,9,10,11,14,15), tanto como el incumplimiento de configuraciones mediante properties,  se podrá considerar motivo de suspenso de la práctica.

“MOTOMAMI”

Es una aplicación mobile para las mujeres conductoras tanto de moto como de coche, bicicleta, patinete eléctrico y furgoneta. 
Se pueden registrar todas aquellas mujeres mayores de 18 años y con carnet de conducir para moto, furgoneta y coche y todas aquellas mayores de 16 años para bicicleta y patinete eléctrico. 


Requisitos funcionales

RF1 La aplicación permitirá el registro de partes de accidente
RF1-AD La aplicación tendrá un proceso de carga diario de un fichero de partes de accidente.

RF2 La aplicación permitirá la contratación del seguro sólo para mujeres a partir de 16 años
RF2.1 La aplicación permitirá el registro a partir de 16 años sin carnet en el caso de bici y patinete.
RF2.2 La aplicación permitirá el registro a partir de 18 años con carnet en el caso de moto, coche y furgoneta.
RF 2.3 El usuario puede registrarse en la aplicación con los datos de nombre, apellidos, fecha de nacimiento, dirección, teléfono, número de carnet o dni, carnet de conducir, dirección de correo electrónico, tipo de vehículo, matrícula si tiene, marca si tiene y el modelo del vehículo, password y sexo (hombre/mujer).  
RF 2.3.1-AD La aplicación tendrá un proceso de carga diario de un fichero de Customers  (MM_insurance_customers_CODPROV_YYYYMMDD.dat). 
RF 2.3.2-AD La aplicación tendrá un proceso de carga diario de un fichero de los vehículos de los customers (MM_insurance_vehicles_YYYYMMDD.dat). 
El formato de la fecha del fichero tiene que ser la siguiente (YYYY/MM/DD).
… 
RF4.1 La aplicación mostrará un histórico de las facturas generadas. 
RF4.2 Las facturas contendrán los campos de id, fecha, nombre empresa, cif empresa (41256985632), dirección de la empresa (C/ Vergel, 5 Madrid, 28080), nombre usuario, apellidos usuario, dirección usuario, tipo seguro, tipo de vehículo, fecha de registro, fecha de fin de contrato (un año de duración), coste, iva (21% sobre el precio). 
RF4.2-AD. La aplicación tendrá un proceso mensual que se lanzará el primer día de cada mes y genera un fichero con todas las facturas del mes anterior(nombre fichero “MM_invoices_CODPROV_YYYYMM.csv”). Para que un proveedor firme digitalmente las facturas.
…


Requisitos NO funcionales
RNF1.1-AD El formato de la fecha del nombre del fichero tiene que ser la siguiente (YYYYMMDD). 
El nombre del fichero tiene que ser(MM_insurance_parts_CODPROV_YYYYMMDD.dat), haciendo cambio de la fecha para cada ejecución (Es obligatorio que este valor se encuentre en el fichero properties, tanto el nombre como el path/ruta). Donde “CODPROV” se obtendrá de la tabla maestra de proveedores, y sólo tienen que ser los proveedores activos.
RNF1.2-AD La planificación del proceso de obtención de partes(insertará en una tabla de “interfaz/intermedia”), se tendrá que poder configurar de manera independiente(Es obligatorio que este valor se encuentre en el fichero properties). Con el formato similar a: 
'0 1 1 ? * *'.
RNF1.3-AD La planificación para procesar partes(insertará en la tabla maestro de partes), se tendrá que poder configurar de manera independiente(Es obligatorio que este valor se encuentre en el fichero properties). Con el formato similar a '0 1 1 ? * *'.
…
RNF 2.3.1.1-AD El formato de la fecha del nombre del fichero tiene que ser la siguiente (YYYYMMDD). 
El nombre del fichero tiene que ser (MM_insurance_customers_CODPROV_YYYYMMDD.dat),  haciendo cambio de la fecha para cada ejecución (Es obligatorio que este valor se encuentre en el fichero properties, tanto el nombre como el path/ruta). Donde “CODPROV” se obtendrá de la tabla maestra de proveedores, y sólo tienen que ser los proveedores activos.
RNF 2.3.1.2-AD La planificación del proceso de obtención de Customers(insertará en una tabla de “interfaz/intermedia”), se tendrá que poder configurar de manera independiente(Es obligatorio que este valor se encuentre en el fichero properties). Con el formato similar a: 
'0 1 1 ? * *'.
RNF2.3.1.3-AD La planificación para procesar Customers(insertará en la tabla maestro de Customers), se tendrá que poder configurar de manera independiente(Es obligatorio que este valor se encuentre en el fichero properties). Con el formato similar a '0 1 1 ? * *'.
RNF 2.3.2.1-AD El formato de la fecha del nombre del fichero tiene que ser la siguiente (YYYYMMDD). 
El nombre del fichero tiene que ser(MM_insurance_vehicles_CODPROV_YYYYMMDD.dat),  haciendo cambio de la fecha para cada ejecución (Es obligatorio que este valor se encuentre en el fichero properties, tanto el nombre como el path/ruta). Donde “CODPROV” se obtendrá de la tabla maestra de proveedores, y sólo tienen que ser los proveedores activos.
RNF 2.3.2.2-AD La planificación del proceso de obtención de Vehicles(insertará en una tabla de “interfaz/intermedia”), se tendrá que poder configurar de manera independiente(Es obligatorio que este valor se encuentre en el fichero properties). Con el formato similar a:
 '0 1 1 ? * *'.
RNF 2.3.2.3-AD La planificación para procesar Vehicles(insertará en la tabla maestro de Vehicles), se tendrá que poder configurar de manera independiente(Es obligatorio que este valor se encuentre en el fichero properties). Con el formato similar a '0 1 1 ? * *'.

…
RNF 4.2-AD. El formato de la fecha del nombre del fichero tiene que ser la siguiente (YYYYMMDD). 
El nombre del fichero tiene que ser (“MM_invoices_CODPROV_YYYYMM.csv), haciendo cambio de la fecha para cada ejecución (Es obligatorio que este valor se encuentre en el fichero properties, tanto el nombre como el path/ruta). Donde “CODPROV” se obtendrá de la tabla maestra de proveedores, y sólo tienen que ser los proveedores activos.

RNF5-AD Los campos fecha en el Json tienen que tener el siguiente formato(configurable en el properties): "yyyy-MM-dd'T'HH:mm:ss.SSSZ".

RNF6-AD Las operaciones con BBDD se tienen que realizar obligatoriamente, realizando uso de las operaciones de hibernate.

RNF7-AD La url para obtener la información de los ficheros tienen que ser: “localhost:8080/appInsurance/v1/readInfoFileVCS/{RESOURCE}/{CODPROV}/{DATE}”:
(donde VCS tienen que ser vuestras credenciales)
*{RESOURCE}--> indica que la fuente de información que se quiere leer(OBLIGATORIO).
*{CODPROV}--> indica el código del proveedor que queremos leer la información(OPCIONAL), en caso de vacío se obtienen de todos los proveedores activos.
*{DATE}--> indica la fecha que queremos leer la información(OPCIONAL), en caso de vacío se obtiene la fecha actual de sistema.

RNF8-AD La url para obtener la información de los ficheros tienen que ser: “localhost:8080/appInsurance/v1/processInfoFileVCS/{RESOURCE}/{CODPROV}/{DATE}”:
(donde VCS tienen que ser vuestras credenciales)
*{RESOURCE}--> indica la fuente de información que se quiere procesar(OBLIGATORIO).
*{CODPROV}--> indica el código del proveedor que queremos procesar la información(OPCIONAL), en caso de vacío se obtienen de todos los proveedores activos.
*{DATE}--> indica la fecha que queremos procesar la información(OPCIONAL), en caso de vacío se obtiene la fecha actual de sistema.

RNF9-AD La url para generar los ficheros de factura: “localhost:8080/appInsurance/v1/genInvoiceFileVCS/{RESOURCE}/{CODPROV}/{DATE}”:
(donde VCS tienen que ser vuestras credenciales)
*{RESOURCE}--> indica la fuente de información que se quiere procesar(OBLIGATORIO).
*{CODPROV}--> indica el código del proveedor que queremos procesar la información(OPCIONAL), en caso de vacío se obtienen de todos los proveedores activos.
*{DATE}--> indica la fecha que queremos procesar la información(OPCIONAL), en caso de vacío se obtiene la fecha actual de sistema.

RNF10-AD Cada uno de los procesos planificados se tienen que poder desactivar de manera independiente o de manera global.(realizar una tabla maestra, indicando si se encuentra activo o no y a partir de la fecha que aplicará la configuración)

RNF11-AD El proyecto tiene que tener un fichero .README que explique cómo se tiene que ejecutar la aplicación, y cada uno de sus apartados/configuraciones para que sirven.

RNF12-AD El proyecto tiene que tener una parte de consulta de documentación de la API(Esto se realiza mediante Swagger y la ruta tiene que ser “localhost:8080/appInsurance/v1/docu_VCS”).
Donde VCS tienen que ser vuestras iniciales.


RNF13-AD El proyecto tiene que tener una parte de consulta del estado de la aplicación (Esto se realiza utilizando actuators: health, status, dump,... ).

RNF14-AD El proyecto tiene que utilizar JAVA 17, Maven como gestor de dependencias.

RNF15- El proyecto se tiene que llamar “MotoMammiApplicationVCS”, y los siguientes campos tienen que tener los valores:
* Group: “com.dam.tfg”
*ArtifactId y Name: “MotoMammiApplicationVCS”, donde VCS tienen que ser vuestras credenciales.

RNF16- El incumplimiento de los RNF’s (6,7,8,9,10,11,14,15), tanto como el incumplimiento de configuraciones mediante properties,  se podrá considerar motivo de suspenso de la práctica.

RNF17- Hay que usar en las dependencias los groupId org.hibernate y org.hibernate.orm en el pom.xml.



