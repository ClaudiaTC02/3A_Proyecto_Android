package ctorcru.upv.techcommit_3a;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.Manifest;
import android.app.UiAutomation;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.uiautomator.UiDevice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ctorcru.upv.techcommit_3a.Pantallas.Pagina_QR;

@RunWith(AndroidJUnit4.class)
public class TestActivity_Pagina_QR {

    /**
     * Declaramos la actividad que vamos a probar
     */
    @Rule
    public ActivityTestRule<Pagina_QR> activityTestRule = new ActivityTestRule<>(Pagina_QR.class);


    /**
     * Comprueba que al pulsar la flecha de atrás se cambia a la actividad inicial
     */
    @Test
    public void comprobarFlechaAtras() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el botón de flecha atrás
        onView(withId(R.id.flecha_atrasQR)).perform(click());
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
     * Comprueba que al pulsar el botón de vincular se cambia a la actividad de registro
     */
    @Test
    public void comprobarEscanerQR() {
        UiAutomation uiAutomation = InstrumentationRegistry.getInstrumentation().getUiAutomation();
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en la imagen para hacer la foto
        onView(withId(R.id.frameLayout3)).perform(click());
        //Se abre la camara
        uiAutomation.grantRuntimePermission("ctorcru.upv.techcommit_3a", Manifest.permission.CAMERA);
        //Volvemos atrás
        uiDevice.pressBack();
    }


    /**
     * Comprueba que al pulsar el enlace inferior se cambia a la actividad de inicio de sesión
     */
    @Test
    public void comprobarEnlaceIniciarSesion() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el enlace inferior
        onView(withId(R.id.irAIniciarSesion)).perform(click());
        //Comprobamos que se muestra la actividad de inicio de sesión
        onView(withId(R.id.ActivityMain)).check(matches(isDisplayed()));
    }

    /**
     * Comprueba que al pulsar el edit text de QR se muestra el teclado para introducir el código
     */
    @Test
    public void comprobarEditTextQR() {
        //Comprobamos que la actividad se muestra
        onView(isDisplayed());
        //Hacemos click en el edit text de QR y comprobamos que es posible introducir texto
        onView(withId(R.id.escaneo_QR)).perform(click()).check(matches(supportsInputMethods()));
    }

}

