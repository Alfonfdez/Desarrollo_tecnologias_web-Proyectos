package com.alfonso.ghibliapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Listado extends AppCompatActivity {

    //DECLARACIONES
    ArrayList<Film> films;
    ListView listView;
    FilmAdapter adapter;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        //FIND VIEWERS BY ID
        listView = findViewById(R.id.listView);

        films = new ArrayList<Film>();

        //ADAPTER
        adapter = new FilmAdapter(this, R.layout.row, films);

        //LISTVIEW
        listView.setAdapter(adapter);

        //1º) COMPROBAR SI TENEMOS CONEXIÓN A INTERNET
        if(isConnected()){

            //Toast.makeText(getApplicationContext(),"SÍ hay conexión a Internet",Toast.LENGTH_LONG).show();

            //ProgressDialog ('Loading' de Android, lo pararemos con la línea "dialog.dismiss()" después que cargue la lista)
            //"dialog.dismiss()" está más abajo
            progressDialog = ProgressDialog.show(Listado.this, "", getString(R.string.progress_dialog_loading), true);

            //INICIALIZAR LA CLASE CREADA (PARA LIBRERÍA 'RETROFIT')
            MyService service = RetrofitClientInstance.getRetrofitInstance().create(MyService.class);

            //HACER LA LLAMADA AL LISTADO DE FILMS DE LA WEB 'https://ghibliapi.herokuapp.com/films' Y CAPTURAR EL RESULTADO O ERROR
            Call<List<Film>> call = service.listFilms();

            call.enqueue(new Callback<List<Film>>() {

                @Override
                public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {

                    //ProgressDialog
                    //AQUÍ PARAREMOS EL 'LOADING' DE ANDROID (UNA VEZ CARGADA LA LISTA DE PERSONAS DE LA WEB Y PASADAS AL "ArrayList<Persona> personas" O LA LIBRERÍA 'GSON')
                    progressDialog.dismiss();

                    //RECOGEREMOS LOS DATOS DE LA WEB Y LOS AÑADIREMOS AL 'ArrayList<Film> films'
                    films.addAll(response.body());

                    //GUARDAREMOS LOS CAMBIOS EN EL 'ADAPTER'
                    adapter.notifyDataSetChanged();

                    }
                    @Override
                    public void onFailure(Call<List<Film>> call, Throwable t) {
                    //ProgressDialog
                        // AQUÍ PARAREMOS EL 'LOADING' DE ANDROID (UNA VEZ CARGADA LA LISTA DE PERSONAS DE LA WEB Y PASADAS AL "ArrayList<Persona> personas" O LA LIBRERÍA 'GSON')
                        progressDialog.dismiss();

                        //MOSTRAR ERRORES
                    }
            });


            }

            //NO HAY CONEXIÓN A INTERNET ('False')
        else{
            Toast.makeText(getApplicationContext(),getString(R.string.progress_dialog_no_internet),Toast.LENGTH_LONG).show();
        }


        //CLIC EN UN ITEM DEL LISTADO
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                //ProgressDialog ('Loading' de Android, lo pararemos con la línea "dialog.dismiss()" después que cargue la lista)
                //"dialog.dismiss()" está más abajo
                progressDialog = ProgressDialog.show(Listado.this, "", getString(R.string.progress_dialog_loading), true);


                //INICIALIZAR LA CLASE CREADA (PARA LIBRERÍA 'RETROFIT')
                MyService service = RetrofitClientInstance.getRetrofitInstance().create(MyService.class);

                //HACER LA LLAMADA Y CAPTURAR EL RESULTADO O ERROR
                Call<List<Film>> call = service.listFilms();

                call.enqueue(new Callback<List<Film>>() {
                    @Override
                    public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {

                        //ProgressDialog
                        //AQUÍ PARAREMOS EL 'LOADING' DE ANDROID (UNA VEZ CARGADA LA LISTA DE FILMS DE LA WEB Y PASADAS AL "ArrayList<Film> films")
                        progressDialog.dismiss();

                        //SELECCIONAR FILM POR SU 'POSICION="i"' Y DESPUÉS COGER SU "ID"
                        String id = films.get(i).getId();

                        //UN "INTENT" SIRVE PARA INVOCAR COMPONENTES ("ACTIVITIES")
                        Intent intent = new Intent(Listado.this, Detalle.class);

                        //RECOGEMOS EL "ID" DE ESTA PELÍCULA ESPECÍFICA Y LO ENVIAMOS A LA 'Detalle.class'
                        intent.putExtra("valor_id",id);

                        startActivity(intent);//IR A 'Detalle.class'

                    }
                    @Override
                    public void onFailure(Call<List<Film>> call, Throwable t) {

                        //ProgressDialog
                        //AQUÍ PARAREMOS EL 'LOADING' DE ANDROID (UNA VEZ CARGADA LA LISTA DE FILMS DE LA WEB Y PASADAS AL "ArrayList<Film> films")
                        progressDialog.dismiss();

                        //MOSTRAR ERRORES
                    }
                });

            }

        });

    }

    /*/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\*/

    //FUNCIÓN PARA COMPROBAR SI TENEMOS CONEXIÓN A INTERNET
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }



}
