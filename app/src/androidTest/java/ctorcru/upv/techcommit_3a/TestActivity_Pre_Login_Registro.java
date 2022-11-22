package ctorcru.upv.techcommit_3a;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ctorcru.upv.techcommit_3a.Pantallas.Pre_Login_Registro;

@RunWith(AndroidJUnit4.class)
public class TestActivity_Pre_Login_Registro {

    /**
     * Declaramos la actividad que vamos a probar
     */
    @Rule
    public ActivityTestRule<Pre_Login_Registro> activityTestRule = new ActivityTestRule<>(Pre_Login_Registro.class);

    /**
     * Comprueba que al pulsar el botón de iniciar sesión se cambia a la actividad de inicio de sesión
     */
    @Test
    public void comprobarCambioAInicioSesion() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el botón de iniciar sesión
        onView(withId(R.id.botonIniciarSesion)).perform(click());
        //Comprobamos que se muestra la actividad de inicio de sesión
        onView(withId(R.id.ActivityMain)).check(matches(isDisplayed()));
    }

    /**
     * Comprueba que al pulsar el botón de registrarse se cambia a la actividad de registro
     */
    @Test
    public void comprobarCambioARegistro() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el botón de registro
        onView(withId(R.id.botonregistrarse)).perform(click());
        //Comprobamos que se muestra la actividad de registro
        onView(withId(R.id.ActivityRegistro1)).check(matches(isDisplayed()));
    }

}
