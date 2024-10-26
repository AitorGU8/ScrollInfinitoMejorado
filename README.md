# Aplicación de Lista de Tareas

Este proyecto es una aplicación de Android que permite al usuario crear, visualizar y eliminar tareas en una lista. Las tareas se guardan en preferencias compartidas para su persistencia, de manera que se mantendrán almacenadas incluso después de cerrar la aplicación.

## Características

- Añadir tareas a la lista.
- Visualizar todas las tareas guardadas.
- Eliminar tareas con confirmación del usuario.
- Guardado de tareas en preferencias compartidas.

## Estructura del Proyecto

La aplicación está dividida en las siguientes clases principales:

### 1. `MainActivity`
Clase principal de la aplicación que gestiona la lógica para añadir y eliminar tareas de la lista. Implementa un `RecyclerView` para mostrar las tareas y utiliza un adaptador (`TaskAdapter`) para gestionar cada elemento.

**Principales métodos:**
- `addTask()`: Añade una tarea si el campo de texto no está vacío.
- `deleteTask()`: Muestra un cuadro de diálogo para confirmar la eliminación de la tarea seleccionada.
- `initRecyclerView()`: Configura el `RecyclerView` con el adaptador y el layout manager.

### 2. `TaskAdapter`
Adaptador personalizado para gestionar la lista de tareas dentro del `RecyclerView`. Implementa un `ViewHolder` (`TaskViewHolder`) para renderizar cada tarea.

**Principales métodos:**
- `onCreateViewHolder()`: Infla el diseño de cada tarea.
- `onBindViewHolder()`: Enlaza cada tarea en la posición especificada a su `ViewHolder`.
- `getItemCount()`: Devuelve la cantidad de tareas en la lista.

### 3. `TaskViewHolder`
Clase de `ViewHolder` personalizada que define la estructura visual de cada tarea dentro del `RecyclerView`. Cada tarea consta de un `TextView` y un `ImageView`.

**Principales métodos:**
- `render()`: Asigna el texto de la tarea al `TextView` y configura un `onClickListener` en el `ImageView` para marcar la tarea como completada.

### 4. `Preferences`
Clase para gestionar el guardado y la recuperación de tareas en preferencias compartidas. Utiliza un archivo de preferencias denominado `myDatabase` para almacenar la lista de tareas.

**Principales métodos:**
- `saveTasks()`: Guarda la lista de tareas en las preferencias compartidas.
- `getTasks()`: Recupera la lista de tareas almacenada.

### 5. `TaskApplication`
Clase `Application` de la aplicación que inicializa una instancia de `Preferences` para manejar el almacenamiento de tareas en todo el ciclo de vida de la aplicación.
