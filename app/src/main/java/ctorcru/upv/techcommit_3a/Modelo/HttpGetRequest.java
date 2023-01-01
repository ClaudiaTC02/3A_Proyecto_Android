package ctorcru.upv.techcommit_3a.Modelo;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// -----------------------------------------------------------------------------------------
/**
 * @brief Aquí se encuentra el código de la clase que se encarga de realizar la petición GET
 * Autor: Roberto Matilla Augustinus
 * Archivo: HttpGetRequest.java
 **/
// -----------------------------------------------------------------------------------------

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    //----------------------------------
    //Declaración de variables
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    //----------------------------------

    //----------------------------------
    //Método que se ejecuta en segundo plano
    //----------------------------------
    /**
     * @brief Método que se ejecuta en segundo plano
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        //----------------------------------
        //Declaración de variables
        String stringUrl = params[0];
        String result;
        String inputLine;

        //----------------------------------
        //Se crea la conexión
        try {
            //Crear una nueva conexión
            URL myUrl = new URL(stringUrl);
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();

            //Configurar la conexión
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            //Conectar a la URL
            connection.connect();

            //Leer el resultado de la conexión
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();

            //Convertir el resultado a cadena y devolverlo
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }
    //----------------------------------

    //----------------------------------
    //Método que se ejecuta al finalizar el doInBackground
    //----------------------------------
    /**
     * @brief Método que se ejecuta al finalizar el doInBackground
     * @param result
     */
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}

