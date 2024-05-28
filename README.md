# Motomami

## Descripción General:
MotoMammi es una aplicación de gestión de información que procesa datos relacionados con clientes, vehículos y partes.

## Instrucciones de Ejecución:
Para ejecutar la aplicación, asegúrate de tener instalado Java y Maven. Luego, clona el repositorio y ejecuta, o bien directamente en base al cron @Scheduler o 
via llamadas a los diferentes endpoints de nuestro controlador. Los schedulers hacen que las diferentes tareas corran de forma programática, y mantenerlo corriendo de forma automática sin nosotros tener que hacer nada. Por otra lado los endpoint son muchos más rapidos de utilizar y testear el código, ya que tenemos la libertad de ejecutar al instante.

En ambos casos tendremos que ir a MotomamiApplicationsIDS.java y ejecutarlo, una vez esté corriendo ya estará preparado para lanzar peticiones.

## Puntos de Entrada:
La aplicación expone tres puntos de entrada principales:
#### PRIMER PASO
- `/readInfoFileIDS/{resource}/{codprov}/{date}`: Lee información de un archivo para un recurso, código de proveedor y fecha dados. Transaforma los datos en DTO's
 y luego estos van siendo insertados en una tabla intermedia llamada interfaces.
#### SEGUNDO PASO
- `/processInfoFileIDS/{resource}/{codprov}/{date}/{id_interface}`: Este maneja los datos insertados en interfaces para realizar inserciones o actualizaciones en
 las tavlas maestras (customer, vehicle, parts)
#### TERCER PASO
- `/generateCsv/{provider}/{date}`: Este endpoint genera un archivo CSV para cierto proveedor en 
un cierto AÑO y MES, es decir si pasamos por date la siguiente fecha 2024-04-31 y codigo de proveedor CAX, nos generará un archivo MM_invoice_CAX_2024-04.csv con las rows pertenecientes a CAX con el año 2024, y mes entre 04-01 - 04-31


## Modelos

#### MM_PROVIDERS
Almacena información sobre los proveedores.

#### MM_INTERFACE
Almacena información intermedia utilizada para procesar datos.

#### MM_TRANSLATIONS
Almacena traducciones de códigos.

#### MM_VEHICLE
Almacena información sobre los vehículos.

#### MM_PARTS
Almacena información sobre las partes notificadas.

#### MM_INVOICES
Almacena información sobre las facturas.