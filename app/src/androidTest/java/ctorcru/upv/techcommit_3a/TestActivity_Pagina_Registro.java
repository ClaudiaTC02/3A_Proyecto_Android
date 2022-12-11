package ctorcru.upv.techcommit_3a;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ctorcru.upv.techcommit_3a.Pantallas.Pagina_Registro;

@RunWith(AndroidJUnit4.class)
public class TestActivity_Pagina_Registro {
    /**
     * Declaramos la actividad que vamos a probar
     */
    @Rule
    public ActivityTestRule<Pagina_Registro> activityTestRule = new ActivityTestRule<>(Pagina_Registro.class);


    /**
     * Comprueba que al pulsar la flecha de atrás se cambia a la actividad de registro 1
     */
    @Test
    public void comprobarFlechaAtras() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el botón de flecha atrás
        onView(withId(R.id.flecha_atrasRegistro)).perform(click());
        //Comprobamos que se muestra la actividad de registro 1
        onView(withId(R.id.ActivityRegistro1)).check(matches(isDisplayed()));
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
     * Comprueba que se puede pulsar el botón de registro (Este test se modificará cuando se implemente el registro)
     */
    @Test
    public void comprobarRegistroReal() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Rellenamos los campos
        onView(withId(R.id.text_nombre)).perform(typeText("Roberto"));
        closeSoftKeyboard();
        onView(withId(R.id.correo2)).perform(typeText("roberto@roberto.com"));
        closeSoftKeyboard();
        onView(withId(R.id.contrasena2)).perform(typeText("1234"));
        closeSoftKeyboard();
        onView(withId(R.id.verificarContrasenya)).perform(typeText("1234"));
        closeSoftKeyboard();
    }
}
