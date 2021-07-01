package com.example.ejemgrabarfichero;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText txtdatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtdatos = (EditText)findViewById(R.id.txtdatos);

    }

    @Override
    protected void onPause() {
        super.onPause();

        grabarFichero(view);

    }




    public void leerFichero(View v)
    {

        //METODO QUE NOS DEVUELVE UN ARRAY CON LA LISTA DE ARCHIVOS
        String[] archivos = fileList();
        //SI EXISTE, LEEREMOS LOS DATOS
        if (existe(archivos, "datos.txt"))
            try {
                //RECUPERAMOS UN FICHERO READER
                InputStreamReader archivo = new InputStreamReader(
                        openFileInput("datos.txt"));
                //OBJETO PARA LEER LOS DATOS
                BufferedReader lector = new BufferedReader(archivo);
                //LEEMOS UNA LINEA DEL ARCHIVO
                String linea = lector.readLine();
                String contenido = "";
                //UNA FORMA DE LEER ARCHIVOS ES LINEA A LINEA
                //HASTA QUE NO HAYA MAS LINEAS DENTRO DEL ARCHIVO
                while (linea != null)
                {
                    contenido = contenido + linea + "\n";
                    linea = lector.readLine();
                    //ESCRIBIMOS EL CONTENIDO DENTRO DE LA ETIQUETA

                }
                //CERRAMOS EL LECTOR
                lector.close();
                archivo.close();
                this.txtdatos.setText(contenido);
                // QUITAMOS EL TECLADO DE ANDROID
                closetecladomovil();


            } catch (IOException ex) {
                Toast.makeText(this, "Error: " + ex,
                        Toast.LENGTH_LONG).show();
            }

    }

    private void closetecladomovil() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }


    //METODO PARA BUSCAR SI EXISTE ALGUN ARCHIVO EN LA MEMORIA
    private boolean existe(String[] archivos, String nombrefichero) {
        for (String fic: archivos)
        {
            if (nombrefichero.equals(fic))
            {
                return true;
            }
        }
        return false;
    }

    //METODO PARA ESCRIBIR EN UN ARCHIVO   tiene que haver un try y un catch
    public void grabarFichero(View v) {
        try {
            //UTILIZAMOS UN OBJETO WRITER PARA ACCEDER A NUESTRO ARCHIVO
            //EL METODO OPENFILEOUTPUT DE LA CLASE ACTIVITY, RECUPERA UN ARCHIVO INTERNO
            //POR SU NOMBRE, SI NO EXISTE EL FICHERO LO CREA
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(
                    "datos.txt", Activity.MODE_PRIVATE));
            //RECUPERAMOS EL CONTENIDO QUE DESEAMOS ALMACENAR
            // Modo de acceso al archivo: “Context.MODE_PRIVATE” admite el acceso
            // únicamente por la aplicación.

            String contenido = this.txtdatos.getText().toString();

            //ESCRIBIMOS EN EL ARCHIVO LO QUE HEMOS RECUPERADO DE LA CAJA DE TEXTO
            archivo.write(contenido);

            //LIBERAMOS LA MEMORIA
            archivo.flush();

            //CERRAMOS
            archivo.close();

        } catch (IOException ex) {
            Toast mensaje = Toast.makeText(this, "Error: " + ex,
                    Toast.LENGTH_LONG);
        }
        Toast mensaje = Toast.makeText(this, "Datos almacenados",
                Toast.LENGTH_SHORT);
        mensaje.show();
        String contenido = "";
        this.txtdatos.setText(contenido);
        //finish();
    }
}