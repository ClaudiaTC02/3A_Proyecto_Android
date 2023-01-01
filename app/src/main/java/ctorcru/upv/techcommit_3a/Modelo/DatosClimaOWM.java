package ctorcru.upv.techcommit_3a.Modelo;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import ctorcru.upv.techcommit_3a.R;

public class DatosClimaOWM {

        private static final String API_KEY = "72e488c4c13161409855ff863821db0b";
        private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&units=metric&appid=%s";

        public static void getWeather(double latitude, double longitude, TextView temperatureTextView, ImageView weatherIconImageView) {
            // Crear la URL de la API con la latitud y longitud proporcionadas
            String url = String.format(API_URL, latitude, longitude, API_KEY);

            // Realizar la llamada a la API en segundo plano
            HttpGetRequest getRequest = new HttpGetRequest();
            getRequest.execute(url);

            try {
                // Obtener el resultado de la llamada a la API
                String result = getRequest.get();

                // Crear un objeto JSON a partir del resultado
                JSONObject jsonObject = new JSONObject(result);

                //Obtener la temperatura en grados Kelvin de la API
              double temperature = jsonObject.getJSONObject("main").getDouble("temp");

              //Obtener el valor entero de la temperatura
              double temperatureDouble = Double.parseDouble(String.valueOf(temperature));
              int temperatureInt = (int) Math.round(temperatureDouble);

             //Dar el formato adecuado a la temperatura y mostrarla en el TextView
              String temperatureString = Integer.toString(temperatureInt);
              temperatureTextView.setText(temperatureString + "°C");

                // Obtener la condición meteorológica del objeto JSON
                String weatherCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");

                int weatherIconId = 0;

// Seleccionar la imagen adecuada para la condición meteorológica
                switch (weatherCondition) {
                    case "Clear":
                        weatherIconId = R.drawable.sol;
                        break;
                    case "Clouds":
                        weatherIconId = R.drawable.nube;
                        break;
                    case "Rain":
                        weatherIconId = R.drawable.lluvia;
                        break;
                    case "Snow":
                        weatherIconId = R.drawable.nieve;
                        break;
                    case "Thunderstorm":
                        weatherIconId = R.drawable.tormenta;
                        break;
                }

// Mostrar la imagen en el ImageView
                weatherIconImageView.setImageResource(weatherIconId);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



