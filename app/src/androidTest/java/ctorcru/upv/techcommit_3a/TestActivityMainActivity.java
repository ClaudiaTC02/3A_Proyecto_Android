package ctorcru.upv.techcommit_3a;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ctorcru.upv.techcommit_3a.Pantallas.MainActivity;

@RunWith(AndroidJUnit4.class)
public class TestActivityMainActivity {

    /**
     * Declaramos la actividad que vamos a probar
     */
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    /**
     * Comprueba que al pulsar el botón de atrás nos lleva a la actividad inicial
     */
    @Test
    public void comprobarBotonAtras() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el botón de atrás
        onView(withId(R.id.flecha_atrasRegistro)).perform(click());
        //Comprobamos que se muestra la actividad de inicio de sesión
        onView(withId(R.id.ActivityPreLoginRegistro)).check(matches(isDisplayed()));
    }

    /**
     * Comprueba que al pulsar el logo se cambia a la actividad inicial
     */
    @Test
    public void comprobarClickLogo() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el botón de flecha atrás
        onView(withId(R.id.imagenLogoReg)).perform(click());
        //Comprobamos que se muestra la actividad de inicio de sesión
        onView(withId(R.id.ActivityPreLoginRegistro)).check(matches(isDisplayed()));
    }

    /**
     * Comprueba que al pulsar los edit text se muestra el teclado
     */
    @Test
    public void comprobarEditTexts() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el edit text de correo
        onView(withId(R.id.correo)).perform(click()).check(matches(supportsInputMethods()));
        closeSoftKeyboard();
        //Hacemos click en el edit text de contraseña
        onView(withId(R.id.contrasenya)).perform(click()).check(matches(supportsInputMethods()));
    }

    /**
     * Comprueba que al pulsar el enlace de he olvidado la contraseña se cambia a la actividad de recuperar contraseña
     */
    @Test
    public void comprobarHeOlvidadoContrasenya() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el enlace de he olvidado la contraseña
        onView(withId(R.id.olvidastecontrasena)).perform(click());
        //Comprobamos que se muestra la actividad de recuperar contraseña
        onView(withId(R.id.ContrasenyaOlvidada)).check(matches(isDisplayed()));
    }

    /**
     * Comprueba que al escribir un correo y una contraseña válidos se cambia a la actividad de mis dispositivos
     *
     * @brief Test realizado con un usuario existente en la base de datos
     */
    @Test
    public void comprobarInicioSesionCorrecto() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Escribimos un correo válido
        onView(withId(R.id.correo)).perform(click()).check(matches(supportsInputMethods()));
        onView(withId(R.id.correo)).perform(typeText("roberto@roberto.com"));
        closeSoftKeyboard();
        //Escribimos una contraseña válida
        onView(withId(R.id.contrasenya)).perform(click()).check(matches(supportsInputMethods()));
        onView(withId(R.id.contrasenya)).perform(typeText("1234"));
        closeSoftKeyboard();
        //Hacemos click en el botón de registro
        onView(withId(R.id.botonIniciarSesion)).perform(click());
        //Comprobamos que se muestra la actividad de mis dispositivos
        onView(withId(R.id.content_mis_dispositivos)).check(matches(isDisplayed()));
        CerrarSesion();
    }

    /**
     * Comprueba que al escribir un correo y una contraseña no válidos se muestra un mensaje de error
     *
     * @brief Test realizado con un usuario no existente en la base de datos
     */
//    @Test
//    public void comprobarInicioSesionIncorrecto() {
//        //Comprobamos que la actividad se muestra
//        onView(isDisplayed());
//        //Escribimos un correo no válido
//        //check(matches(supportsInputMethods())) es un ViewAssertion que comprueba que la vista está visible
//        onView(withId(R.id.correo)).perform(click()).check(matches(supportsInputMethods()));
//        onView(withId(R.id.correo)).perform(typeText("roberto@roberto.com"));
//        closeSoftKeyboard();
//        //Escribimos una contraseña no válida
//        //check(matches(supportsInputMethods())) es un ViewAssertion que comprueba que la vista está visible
//        onView(withId(R.id.contrasenya)).perform(click()).check(matches(supportsInputMethods()));
//        onView(withId(R.id.contrasenya)).perform(typeText("12345"));
//        closeSoftKeyboard();
//        //Hacemos click en el botón de registro
//        onView(withId(R.id.botonIniciarSesion)).perform(click());
//        //Comprobamos que se muestra un Toast con el mensaje de error
//        //check(matches(isDisplayed())) es un ViewAssertion que comprueba que la vista está visible
//        onView(withId(R.id.ActivityMain)).check(matches(isDisplayed()));
//    }

    /**
     * Comprueba que al pulsar el botón de registro se cambia a la actividad de registro
     */
    @Test
    public void comprobarBotonRegistro() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el botón de registro
        onView(withId(R.id.irAIniciarSesion)).perform(click());
        //Comprobamos que se muestra la actividad de registro
        //check(matches(isDisplayed())) es un ViewAssertion que comprueba que la vista está visible
        onView(withId(R.id.ActivityRegistro1)).check(matches(isDisplayed()));
    }

    /**
     * Comprueba que es posible cerrar sesión
     * @brief Test realizado con un usuario existente en la base de datos
     */
    public void CerrarSesion() {
        onView(withContentDescription("Más opciones")).perform(click());
        onView(withText("Cerrar sesión")).perform(click());
        //Darle al botón de aceptar
        onView(withText("Si")).perform(click());
        //check(matches(isDisplayed())) es un ViewAssertion que comprueba que la vista está visible
        onView(withId(R.id.ActivityPreLoginRegistro)).check(matches(isDisplayed()));
    }
}