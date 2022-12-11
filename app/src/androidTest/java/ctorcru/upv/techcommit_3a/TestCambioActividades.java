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

import ctorcru.upv.techcommit_3a.Pantallas.Mis_Dispositivos;
import ctorcru.upv.techcommit_3a.Pantallas.Pagina_Registro;
import ctorcru.upv.techcommit_3a.Pantallas.Pre_Login_Registro;

@RunWith(AndroidJUnit4.class)
public class TestCambioActividades {
    /**
     * Declaramos la actividad que vamos a probar. En este test se probará el proceso completo de inicio de sesión y visita de todas las actividades dentro de la app como tal.
     */
    @Rule
    public ActivityTestRule<Pre_Login_Registro> activityTestRule = new ActivityTestRule<>(Pre_Login_Registro.class);

    /**
     *
     * @brief Test realizado con un usuario existente en la base de datos
     */
    @Test
    public void procesoCompleto() {
        onView(withId(R.id.botonIniciarSesion)).perform(click());
        //Escribimos un correo válido
        onView(withId(R.id.correo)).perform(click()).check(matches(supportsInputMethods()));
        onView(withId(R.id.correo)).perform(typeText("prueba@prueba.com"));
        closeSoftKeyboard();
        //Escribimos una contraseña válida
        onView(withId(R.id.contrasenya)).perform(click()).check(matches(supportsInputMethods()));
        onView(withId(R.id.contrasenya)).perform(typeText("1234"));
        closeSoftKeyboard();
        //Hacemos click en el botón de registro
        onView(withId(R.id.botonIniciarSesion)).perform(click());
        //Comprobamos que se muestra la actividad de mis dispositivos
        onView(withId(R.id.content_mis_dispositivos)).check(matches(isDisplayed()));
        onView(withContentDescription("Abrir menú")).perform(click());
        onView(withId(R.id.nav_Mapa)).perform(click());
        onView(isDisplayed());
        onView(withContentDescription("Abrir menú")).perform(click());
        onView(withId(R.id.nav_Mis_Dispositivos)).perform(click());
        onView(isDisplayed());
        onView(withContentDescription("Abrir menú")).perform(click());
        onView(withId(R.id.nav_Mi_Perfil)).perform(click());
        onView(isDisplayed());
        onView(withContentDescription("Más opciones")).perform(click());
        onView(withText("Cerrar sesión")).perform(click());
        //Darle al botón de aceptar
        onView(withText("Si")).perform(click());
    }


}
