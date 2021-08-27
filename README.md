# logging-class

Enunciado:

Un compañero de su equipo le pide que revise el código que escribió para realizar un reporte de los usuarios de Despegar. Para ello necesito hacer que el sistema acceda a la base de datos ubicada en un host y puesto específico con usuario y contraseña. Como la cantidad de clientes es muy grande, no se puede bajar toda la información de una sola vez, sino que es necesario "scrollear" los resultados, es decir, leerlos de a 1000 elementos por vez. Cómo no sabemos de antemano cuantos elementos queremos recuperar, la base de datos nos permite saber si con la última consulta que hicimos, recuperamos todos los elementos o es necesario hacer una nueva consulta para continuar scrolleando. Por lo tanto, lo primero que deben hacer es crear el scroll ejecutando un search, luego scrollear todas las veces que sea necesario para leer los datos y, al final, cuando ya tengan toda la información, deben limpiar el scroll para liberar el recurso en la base. Es posible que en cualquier momento la base de datos tire una excepción SearchException que debió atrapar y cancelar la ejecución total del reporte.
Su compañero es muy bueno técnicamente, pero no tiene mucha experiencia logueando
(no tuvo la clase de logging  :-D ).
