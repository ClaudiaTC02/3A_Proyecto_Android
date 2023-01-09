<h3 align = "center"> Aplicación Android </h3>

---
<p align = "center"> En este repositorio se encuentra el código del desarrollo de la aplicación Android.
    <br>
</p>

## 📝 Tabla de contenido

- [Introducción](#Getting_started)
- [Tests](#tests)

## 🏁 Comenzando <a name = "getting_started"> </a>

Estas instrucciones le proporcionarán una copia del proyecto en funcionamiento en su ordenador con fines de desarrollo y prueba.

### Requisitos previos

En este apartado, se verán todos los elementos necesarios para poder trabajar correctamente sobre la aplicación Android.

•	Android Studio. Deberemos instalarlo para poder editar y trabajar con el código del proyecto. Lo descargaremos desde su página oficial, eligiendo una versión superior a 2021.3.1.
```
Página oficial de Android Studio: https://developer.android.com/studio
```

•	Dispositivo Android. Es necesario tener un dispositivo Android para poder probar la aplicación correctamente. Si no se dispone de uno, también es posible utilizar un emulador, proporcionado también por Android Studio.
```
Emulador de Android Studio: https://developer.android.com/studio/run/emulator
```

•	Repositorio GitHub. Debemos tener acceso a este repositorio de GitHub, donde podremos obtener los archivos necesarios del proyecto.



### Preparación del espacio Android Studio
Una vez preparados todos los elementos nombrados anteriormente, prepararemos el entorno de desarrollo con el proyecto de GitHub desde Android Studio siguiendo los pasos:
1.	En la barra superior de Android Studio, seleccionamos la opción File, y en el panel que se ha desplegado al hacer clic, selccionamos la opción New -> Project from Version Control.

2.	Con esta opción, podremos importar el proyecto de la aplicación Android directamente desde GitHub. Es posible que en este paso te pida iniciar sesión en GitHub desde Android Studio para llevar a cabo la acción.

3.	Una vez hecho esto, ya tendremos preparado el entorno de desarrollo para trabajar.



### Ejecución del proyecto
Para ejecutar este proyecto, solamente tendremos que seleccionar la opción Run ‘app’, simbolizada con el Play de color verde. 

Si vamos a ejecutar la aplicación en un dispositivo Android físico, antes de darle a Run, debemos conectar el teléfono al ordenador mediante la depuración del teléfono.

•	Si tenemos un dispositivo con Android 11+, es posible conectarse mediante depuración inalámbrica.

•	Si la versión de Android del dispositivo es inferior a Android 11, deberemos conectarnos mediante la depuración USB.

En ambos casos, debemos activar las opciones de desarrollador en el dispositivo antes de realizar la conexión, y podremos hacerlo mediante los pasos que se muestran en la página oficial de Android.
Una vez activadas las opciones de desarrollador, solamente tendremos que buscar la opción de Depuración en el teléfono y activarla.

```
Activar opciones de desarrollador: https://developer.android.com/studio/debug/dev-options?hl=es-419#:~:text=Si%20quieres%20habilitar%20las%20Opciones,del%20dispositivo%20>%20Número%20de%20compilación
``` 

Si vamos a ejecutar la aplicación en un emulador, solamente tendremos que darle a Run y esperar si tenemos el emulador configurado, si no, deberemos seguir el enlace sobre el emulador.
```
Emulador de Android Studio: https://developer.android.com/studio/run/emulator
```


## 🔧 Ejecutando las pruebas <a name = "tests"> </a>
Para ejecutar las pruebas automáticas, deberemos seguir los siguientes pasos:
1.	Abrimos Android Studio.

2.	Dentro de la carpeta java, visible en la zona derecha del IDE, hacemos clic derecho en la carpeta ctorcru.upv.techcommit_3a con la indicación de (androidTest).

3.	Seleccionamos en el desplegable la opción Run Tests.
