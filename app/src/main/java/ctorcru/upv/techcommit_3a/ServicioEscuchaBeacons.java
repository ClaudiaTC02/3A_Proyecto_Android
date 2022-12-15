package ctorcru.upv.techcommit_3a;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    public static String nombre;
    public static Date fechaHora;
    private SharedPreferences preferencias;

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
        String nombreDispositivo = preferencias.getString("CodigoDispositivo", "ninguno");
        Log.d("CodigoDispositivo",nombreDispositivo);
        //Si el nombre del beacon recibido es el que se busca, se muestra la información en el LogCat (por el momento)
        if(nombre != null && nombre.equals(nombreDispositivo)){
            fechaHora = Calendar.getInstance().getTime();
            Log.d(ETIQUETA_LOG, " Momento de encuentro con EPSG-ROBERTO-PRO: " + fechaHora);

            minorMuestra = Utilidades.bytesToInt(tib.getMinor());
            //minorrr a float con 5 decimales
            DecimalFormat df = new DecimalFormat("#.#####");
            df.setRoundingMode(RoundingMode.CEILING);
            minorDecimal = Float.parseFloat(df.format(minorMuestra));
            minorValorReal = minorDecimal/10000;
            Log.d(ETIQUETA_LOG, "Valor en ppm recibido recibido = " + minorValorReal);
        }
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
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");
        bta.enable();
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");
        this.elEscanner = bta.getBluetoothLeScanner();

        //Si el escaner es nulo, es que el dispositivo no tiene bluetooth
        if ( this.elEscanner == null ) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): voy a perdir permisos (si no los tuviera) !!!!");

        // Pedimos permisos para acceder al bluetooth si no los tuviéramos
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(Mis_Dispositivos.getInstance(), new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");

        }
    } // ()

    // --------------------------------------------------------------
    // --------------------------------------------------------------
    // --------------------------------------------------------------
    @Override
    public void onCreate() {
        inicializarBlueTooth();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int IdProceso) {
        //El START_STICKY es para que el servicio se reinicie si se destruye
        buscarEsteDispositivoBTLE( "GTI-3ARoberto" );
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
}
