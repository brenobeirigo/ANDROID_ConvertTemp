package com.bbgo.converttemp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
TextView fahreinheit;
    private class ConvertTemp extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String temp = null;
            String url = params[0];
            HttpHelper helper = new HttpHelper();
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("Celsius", params[1]);
            String retorno = null;
            try {
                retorno = helper.doPost(url, parametros, "UTF-8");
                temp = IOUtils.readTemperature(retorno);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(String s) {
            fahreinheit.setText(s);
            super.onPostExecute(s);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fahreinheit  = (TextView) findViewById(R.id.textViewF);
        Button converter = (Button) findViewById(R.id.buttonConvert);
        converter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.w3schools.com/xml/tempconvert.asmx/CelsiusToFahrenheit";
                EditText celsius = (EditText) findViewById(R.id.editTextCelsius);
                System.out.println("Celsius: "+celsius.getText());
                ConvertTemp converte = new ConvertTemp();
                converte.execute(url, celsius.getText().toString());

            }
        });
    }
}
