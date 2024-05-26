# Motomami

## Descripción General:
MotoMammi es una aplicación de gestión de información que procesa datos relacionados con clientes, vehículos y partes.

## Instrucciones de Ejecución:
Para ejecutar la aplicación, asegúrate de tener instalado Java y Maven. Luego, clona el repositorio y ejecuta, o bien directamente en base al cron @Scheduler o 
via llamadas a los diferentes endpoints de nuestro controlador.

## Puntos de Entrada:
La aplicación expone dos puntos de entrada principales:
- `/readInfoFileIDS/{resource}/{codprov}/{date}`: Lee información de un archivo para un recurso, código de proveedor y fecha dados. Transaforma los datos en DTO's
 y luego estos van siendo insertados en una tabla intermedia llamada interfaces.
- `/processInfoFileIDS/{resource}/{codprov}/{date}/{id_interface}`: Este maneja los datos insertados en interfaces para realizar inserciones o actualizaciones en
 las tavlas maestras (customer, vehicle, parts)