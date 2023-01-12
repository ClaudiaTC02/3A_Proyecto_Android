package ctorcru.upv.techcommit_3a;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ctorcru.upv.techcommit_3a.Logica.Logica;
import ctorcru.upv.techcommit_3a.Modelo.Medicion;
import ctorcru.upv.techcommit_3a.Modelo.TramaIBeacon;
import ctorcru.upv.techcommit_3a.Modelo.Utilidades;
import ctorcru.upv.techcommit_3a.Pantallas.Mis_Dispositivos;

/**
 * @brief Aquí se encuentra el código que hace posible que la aplicación reciba los datos de los beacons enviados por el sensor
 * Autor: Roberto Matilla Augustinus
 * Archivo: ServicioEscuchaBeacons.java
 **/

public class ServicioEscuchaBeacons extends Service {
    //Declaración de variables
    private static final String ETIQUETA_LOG = ">>>>";
    private static final int CODIGO_PETICION_PERMISOS = 11223344;
    private BluetoothLeScanner elEscanner;
    private ScanCallback callbackDelEscaneo = null;
    public Integer minorMuestra;
    public Float minorDecimal;
    public Float minorValorReal;
    public static String UUID;
    public static Integer minor;
    boolean notificacionMostrada = false;
    boolean notificacionMostrada2 = false;
    public static String nombre;
    public static Date fechaHora;
    public int contador = 0;
    public int contadorX;
    private static ServicioEscuchaBeacons myContext;
    public TextView pocaConexion, mediaConexion, buenaConexion, EbotonDetenerBusqueda,EmensajeSiConectado,SmensajeNoConectado,EmensajeDistancia,SbotonConectar,RbotonConectando;
    public String nombreDispositivo;
    public Double latitud;
    public Double longitud;

    ImageView ImagenMuyBuenAire, ImagenAireNormal, ImagenCuidadoAire;
    TextView TextoMuyBuenAire, TextoAireNormal, TextoCuidadoAire, TextoInformacionAire;
    CardView CardviewCaraOzono;

    
    public int getCounterValue() {
        return contador;
    }

    // ------------------------------------------------------------------------------------------
    /** Función que devuelve la UUID del dispositivo
     @verbatim
     public static String getUUID() {return UUID;}
     @endverbatim
     @brief Diseño: // getUUID --------> UUID
     @return UUID
     */
    public static String getUUID() {return UUID;}
    // ------------------------------------------------------------------------------------------

    // ------------------------------------------------------------------------------------------
    /** Función que devuelve el minor recibido por el dispositivo
     @verbatim
     public static Integer getMinor() {return minor;}
     @endverbatim
     @brief Diseño: // getMinor --------> minor
     @return minor
     */
    public static Integer getMinor() {return minor;}
    // ------------------------------------------------------------------------------------------

    // ------------------------------------------------------------------------------------------
    /** Funcion que devuelve la fecha y hora del dispositivo
     @verbatim
     public static Date getFechaHora() {return fechaHora;}
     @endverbatim
     @brief Diseño: // getFechaHora --------> fechaHora
     @return fechaHora
     */
    public static Date getFechaHora() {return fechaHora;}
    // ------------------------------------------------------------------------------------------

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /** Función que muestra la información del dispositivo BTLE
     * @verbatim
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado ) {...}
     @endverbatim
     @brief Diseño: // ScanResult result --------> mostrarInformacionDispositivoBTLE --------> void
     @param ScanResult resultado
     */
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado ) {
        pocaConexion = Mis_Dispositivos.getInstance().findViewById(R.id.pocaconexion);
        mediaConexion = Mis_Dispositivos.getInstance().findViewById(R.id.mediaconexion);
        buenaConexion = Mis_Dispositivos.getInstance().findViewById(R.id.totalconexion);
        EbotonDetenerBusqueda = Mis_Dispositivos.getInstance().findViewById(R.id.EbotonDetenerBusqueda);
        EmensajeSiConectado = Mis_Dispositivos.getInstance().findViewById(R.id.EmensajeSiConectado);
        SmensajeNoConectado = Mis_Dispositivos.getInstance().findViewById(R.id.SmensajeNoConectado);
        SbotonConectar = Mis_Dispositivos.getInstance().findViewById(R.id.SbotonBusqueda);
        RbotonConectando = Mis_Dispositivos.getInstance().findViewById(R.id.RbotonConectando);
        EmensajeDistancia = Mis_Dispositivos.getInstance().findViewById(R.id.EmensajeDistancia);

        ImagenMuyBuenAire = Mis_Dispositivos.getInstance().findViewById(R.id.hombreContento);
        ImagenAireNormal = Mis_Dispositivos.getInstance().findViewById(R.id.hombreFeliz);
        ImagenCuidadoAire = Mis_Dispositivos.getInstance().findViewById(R.id.imagenConMascarilla);
        TextoMuyBuenAire = Mis_Dispositivos.getInstance().findViewById(R.id.conTranquilidad);
        TextoAireNormal = Mis_Dispositivos.getInstance().findViewById(R.id.MedioMdio);
        TextoCuidadoAire = Mis_Dispositivos.getInstance().findViewById(R.id.CuidadoAire);
        TextoInformacionAire = Mis_Dispositivos.getInstance().findViewById(R.id.InfoRelacionAire);
        CardviewCaraOzono = Mis_Dispositivos.getInstance().findViewById(R.id.CardviewCaraOzono);


        //Se obtiene la información del dispositivo BTLE
        BluetoothDevice bluetoothDevice = resultado.getDevice();

        //Se obtienen los bytes del escaneo
        byte[] bytes = resultado.getScanRecord().getBytes();

        //Se obtienen el rssi (indicador de la intensidad de la señal)
        int rssi = resultado.getRssi();

        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " ************ DISPOSITIVO DETECTADO BTLE *********** ");
        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
        Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
        Log.d(ETIQUETA_LOG, " rssi = " + rssi );
        Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
        Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));

        //Se crea la trama de beacons a partir de los bytes
        TramaIBeacon tib = new TramaIBeacon(bytes);
        UUID = Utilidades.bytesToString(tib.getUUID());
        minor = Utilidades.bytesToInt(tib.getMinor());
        nombre = bluetoothDevice.getName();

        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
        Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
        Log.d(ETIQUETA_LOG, " advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
        Log.d(ETIQUETA_LOG, " advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
        Log.d(ETIQUETA_LOG, " companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
        Log.d(ETIQUETA_LOG, " iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
        Log.d(ETIQUETA_LOG, " iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( " + tib.getiBeaconLength() + " ) ");
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( " + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
        Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( " + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
        Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
//        Log.d("CodigoDispositivo",nombreDispositivo);
//        Log.d(ETIQUETA_LOG, "nombreee = " + nombreDispositivo);


        //Si el nombre del beacon recibido es el que se busca, se muestra la información en el LogCat (por el momento)
        if(nombre != null && nombre.equals("GTI-3ARoberto")){
            if (!notificacionMostrada) {
                Mis_Dispositivos.getInstance().lanzarNotificacionDispositivoEncontrado();
                notificacionMostrada = true;
                EbotonDetenerBusqueda.setVisibility(View.VISIBLE);
                EmensajeSiConectado.setVisibility(View.VISIBLE);
                EmensajeDistancia.setVisibility(View.VISIBLE);
                SmensajeNoConectado.setVisibility(View.INVISIBLE);
                SbotonConectar.setVisibility(View.INVISIBLE);
                RbotonConectando.setVisibility(View.INVISIBLE);
            }
            contador++;
            fechaHora = Calendar.getInstance().getTime();
            Log.d(ETIQUETA_LOG, " Momento de encuentro con EPSG-ROBERTO-PRO: " + fechaHora);
            minorMuestra = Utilidades.bytesToInt(tib.getMinor());
            //minor a float con 5 decimales
            DecimalFormat df = new DecimalFormat("#.#####");
            df.setRoundingMode(RoundingMode.CEILING);
            minorDecimal = Float.parseFloat(df.format(minorMuestra));
            minorValorReal = minorDecimal/10000;
            Log.d(ETIQUETA_LOG, "Valor en ppm recibido recibido = " + minorValorReal);
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            try{
                longitud = location.getLongitude();
                latitud = location.getLatitude();
            }catch(Exception e) {
                longitud = 0.0;
                latitud = 0.0;
            }


            Medicion medicion = new Medicion(minorValorReal.toString(),latitud.toString(),longitud.toString(),nombreDispositivo);
            Log.d("medidaParaBD",medicion.toJSON());
            new Logica().insertarMedida(medicion);
            int rssis = rssi;
            Log.d(ETIQUETA_LOG, "rssi Roberto= " + rssis);
            if (rssis >= -84) {
                Log.d("distancia", "Buena conexión");
                //Acceder a un textView y mostrar el valor de txPower
                buenaConexion.setVisibility(View.VISIBLE);
                mediaConexion.setVisibility(View.INVISIBLE);
                pocaConexion.setVisibility(View.INVISIBLE);
            }
            else if (rssis > -92) {
                Log.d("distancia", "Media conexión");
                //Acceder a un textView y mostrar el valor de txPower
                buenaConexion.setVisibility(View.INVISIBLE);
                mediaConexion.setVisibility(View.VISIBLE);
                pocaConexion.setVisibility(View.INVISIBLE);
            }
            else if(rssis > -100) {
                Log.d("distancia", "Poca conexión");
                //Acceder a un textView y mostrar el valor de txPower
                buenaConexion.setVisibility(View.INVISIBLE);
                mediaConexion.setVisibility(View.INVISIBLE);
                pocaConexion.setVisibility(View.VISIBLE);
                Mis_Dispositivos.getInstance().lanzarNotificacionAltaDistanciaSensor();
            }
            else {
                Log.d("distancia", "Sin señal");
                //Acceder a un textView y mostrar el valor de txPower
                buenaConexion.setVisibility(View.INVISIBLE);
                mediaConexion.setVisibility(View.INVISIBLE);
                pocaConexion.setVisibility(View.INVISIBLE);
            }

//minorValorReal > 1.8
            //0.0 < minorValorReal && minorValorReal < 1
            //1.0 < minorValorReal && minorValorReal < 1.8
            // Comprobar si el límite es excedido
            if(minorValorReal > 0.24){
                CardviewCaraOzono.setCardBackgroundColor(Color.parseColor("#72F44336"));
                if (!notificacionMostrada2) {
                    Mis_Dispositivos.getInstance().lanzarNotificacionMaximoExcedido();
                    notificacionMostrada2 = true;
                }

                TextoInformacionAire.setVisibility(View.INVISIBLE);
                TextoCuidadoAire.setVisibility(View.VISIBLE);
                ImagenCuidadoAire.setVisibility(View.VISIBLE);

                TextoAireNormal.setVisibility(View.INVISIBLE);
                ImagenAireNormal.setVisibility(View.INVISIBLE);

                TextoMuyBuenAire.setVisibility(View.INVISIBLE);
                ImagenMuyBuenAire.setVisibility(View.INVISIBLE);
            }
            else if (0.06 < minorValorReal && minorValorReal < 0.1){
                CardviewCaraOzono.setCardBackgroundColor(Color.parseColor("#5295D529"));
                TextoInformacionAire.setVisibility(View.INVISIBLE);
                TextoMuyBuenAire.setVisibility(View.VISIBLE);
                ImagenMuyBuenAire.setVisibility(View.VISIBLE);

                TextoCuidadoAire.setVisibility(View.INVISIBLE);
                ImagenCuidadoAire.setVisibility(View.INVISIBLE);

                TextoAireNormal.setVisibility(View.INVISIBLE);
                ImagenAireNormal.setVisibility(View.INVISIBLE);

            }
            else if (0.09 < minorValorReal && minorValorReal < 0.1){
                CardviewCaraOzono.setCardBackgroundColor(Color.parseColor("#6DFEDC46"));
                TextoInformacionAire.setVisibility(View.INVISIBLE);
                TextoAireNormal.setVisibility(View.VISIBLE);
                ImagenAireNormal.setVisibility(View.VISIBLE);

                TextoMuyBuenAire.setVisibility(View.INVISIBLE);
                ImagenMuyBuenAire.setVisibility(View.INVISIBLE);

                TextoCuidadoAire.setVisibility(View.INVISIBLE);
                ImagenCuidadoAire.setVisibility(View.INVISIBLE);
            }
        }

        Log.d(ETIQUETA_LOG, "Hola buenas" + contador);
        Log.d(ETIQUETA_LOG, "Hola buenasX" + contadorX);

        //----------------------------------------------------
    } // ()



    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /** Función que busca un dispositivo BTLE
     @verbatim
     private void buscarEsteDispositivoBTLE(final String dispositivoBuscado ) {...}
     @endverbatim
     @brief Diseño: // String dispositivoBuscado --------> buscarEsteDispositivoBTLE --------> void
     @param String dispositivoBuscado
     */
    private void buscarEsteDispositivoBTLE(final String dispositivoBuscado ) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");
        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");

        //Se crea el callback del escaneo
        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            //onScanResult --------> void
            public void onScanResult( int callbackType, ScanResult resultado ) {
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");
                mostrarInformacionDispositivoBTLE( resultado );
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");

            }
        };
        ScanFilter sf = new ScanFilter.Builder().setDeviceName( dispositivoBuscado ).build();
        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado );
        this.elEscanner.startScan( this.callbackDelEscaneo );
    } // ()


    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /** Función que detiene la búsqueda de dispositivos BTLE
     * @verbatim
    private void detenerBusquedaDispositivosBTLE() {...}
     @endverbatim
     @brief Diseño: // void --------> detenerBusquedaDispositivosBTLE --------> void
     */
    // Función que detiene la búsqueda de dispositivos BTLE
    private void detenerBusquedaDispositivosBTLE() {

        if ( this.callbackDelEscaneo == null ) {
            return;
        }
        this.elEscanner.stopScan( this.callbackDelEscaneo );
        this.callbackDelEscaneo = null;

    } // ()
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @SuppressLint("MissingPermission")
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    /** Función que inicializa el bluetooth del dispositivo
     * @verbatim
    private void inicializarBlueTooth() {...}
     @endverbatim
     @brief Diseño: // void --------> inicializarBlueTooth --------> void
     */
    public void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");
        bta.enable();
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");
        this.elEscanner = bta.getBluetoothLeScanner();

    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @Override
    public void onCreate() {
        inicializarBlueTooth();
        super.onCreate();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int IdProceso) {
        nombreDispositivo = intent.getExtras().getString("nombreSensor");
        //El START_STICKY es para que el servicio se reinicie si se destruye
        buscarEsteDispositivoBTLE( nombreDispositivo );
        //cambiarvalor();
        //comprobacion();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        detenerBusquedaDispositivosBTLE();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void comprobacion(){
        final Handler handler = new Handler();
        final int delay = 10000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if(contadorX==contador){
                    Mis_Dispositivos.getInstance().lanzarNotificacionYaNoReciboBeacons();
                    detenerBusquedaDispositivosBTLE();
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void cambiarvalor(){
        final Handler handler = new Handler();
        final int delay = 5000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                contadorX=contador;
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public ServicioEscuchaBeacons() {
        myContext =  this;
    }

    public static ServicioEscuchaBeacons getInstance() {
        return myContext;
    }


}
