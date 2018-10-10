package com.alfonso.ghibliapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detalle extends AppCompatActivity {

    //DECLARACIONES
    TextView tv_title2;
    TextView tv_description2;
    TextView tv_director2;
    TextView tv_release_date2;
    ProgressDialog progressDialog;

    String idfilm;

    CheckBox ch_favourite;
    TextView tv_comment;
    TextView tv_picture;
    ImageView iv_picture;


    ArrayList<Preferencia> preferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        //FIND VIEWERS BY ID
        tv_title2 = findViewById(R.id.tv_title2);
        tv_description2 = findViewById(R.id.tv_description2);
        tv_director2 = findViewById(R.id.tv_director2);
        tv_release_date2 = findViewById(R.id.tv_release_date2);

        ch_favourite = findViewById(R.id.ch_favourite);
        tv_comment = findViewById(R.id.tv_comment);
        tv_picture = findViewById(R.id.tv_picture);
        iv_picture = findViewById(R.id.iv_picture);

        //ArrayList<Preferencia>()
        preferencias = new ArrayList<Preferencia>();

        //CÓDIGO PARA EL 'ACTIONBAR' CON LA POSIBILIDAD DE VOLVER HACIA ATRÁS
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECOGEMOS LOS VALORES DE LA PELÍCULA ENVIADOS DESDE 'Listado.class'
        idfilm = getIntent().getStringExtra("valor_id");


        //1º) COMPROBAR SI TENEMOS CONEXIÓN A INTERNET
        if(isConnected()){

            //Toast.makeText(getApplicationContext(),"SÍ hay conexión a Internet",Toast.LENGTH_LONG).show();

            //ProgressDialog ('Loading' de Android, lo pararemos con la línea "dialog.dismiss()" después que cargue la lista)
            //"dialog.dismiss()" está más abajo
            progressDialog = ProgressDialog.show(Detalle.this, "", getString(R.string.progress_dialog_loading), true);

            //INICIALIZAR LA CLASE CREADA (PARA LIBRERÍA 'RETROFIT')
            MyService service = RetrofitClientInstance.getRetrofitInstance().create(MyService.class);

            //HACER LA LLAMADA DE UN FILM EN CONCRETO(CON EL VALOR DEL ID) Y CAPTURAR EL RESULTADO O ERROR
            Call<Film> call_un_film = service.getFilm(idfilm);

            call_un_film.enqueue(new Callback<Film>() {

                @Override
                public void onResponse(Call<Film> call, Response<Film> response) {

                    //ProgressDialog
                    //AQUÍ PARAREMOS EL 'LOADING' DE ANDROID (UNA VEZ CARGADA LA LISTA DE DETALLES DEL FILM ESPECÍFICO)
                    progressDialog.dismiss();

                    //INSTANCIAR EL FILM ESPECÍFICO
                    Film film = response.body();

                    //RECOGER LOS VALORES DEL FILM ESPECÍFICO
                    String title = film.getTitle();
                    String description = film.getDescription();
                    String director = film.getDirector();
                    int release_date_int = film.getRelease_date();
                    String release_date = String.valueOf(release_date_int);

                    //IMPRIMIR EN PANTALLA TODOS LOS VALORES DE LA PELÍCULA
                    tv_title2.setText(title);
                    tv_description2.setText(description);
                    tv_director2.setText(director);
                    tv_release_date2.setText(release_date);


                }
                @Override
                public void onFailure(Call<Film> call, Throwable t) {
                    //ProgressDialog
                    //AQUÍ PARAREMOS EL 'LOADING' DE ANDROID (UNA VEZ CARGADA LA LISTA DE DETALLES DEL FILM ESPECÍFICO O DÉ ERROR)
                    progressDialog.dismiss();

                    //MOSTRAR ERRORES
                }
            });


        }

        //NO HAY CONEXIÓN A INTERNET ('False')
        else{
            Toast.makeText(getApplicationContext(),getString(R.string.progress_dialog_no_internet),Toast.LENGTH_LONG).show();
        }



    }

    /*/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\*/


    //ACTIVITY 'onResume()' -> SALE SIEMPRE CUANDO ENTRAS EN LA 'ACTIVITY' Y CUANDO VUELVES A ELLA
    @Override
    protected void onResume() {
        super.onResume();

        //LIMPIAMOS EL LISTADO DE 'PREFERENCIAS'
        preferencias.clear();

        //BUSCAMOS POR "idfilm" LA 'PREFERENCIA' ESPECÍFICA Y NOS DEVOLVERÁ UN "ArrayList<Preferencia>()"
        //DE UN SÓLO VALOR, EN LA POSICIÓN 0
        preferencias.addAll(Preferencia.find(Preferencia.class, "idfilm = ?", idfilm));

        //SI HA PODIDO ENCONTRAR EL "idfilm" EL "SIZE" DEL "ArrayList<Preferencia>()" SERÁ MAYOR QUE 0
        if(preferencias.size() > 0 ){

            Preferencia preferencia = preferencias.get(0);

            boolean preferida = preferencia.isPreferida();
            ch_favourite.setChecked(preferida);

            String comentario = preferencia.getComentario();
            tv_comment.setText(comentario);

            String imagen = preferencia.getImagen();
            tv_picture.setText(imagen);

            if(!imagen.equals("")&&imagen!=null){

                //---DECLARACIÓN PICASSO: SUBIR IMÁGENES DE INTERNET EN NUESTRA APP---
                Picasso.get().load(imagen) //AGARRO EL STRING CON LA URL (DE NUESTRA IMAGEN ESCOGIDA DE INTERNET)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(iv_picture);

                //IMAGEN PARA UTILIZAR EN EL EJEMPLO:
                // https://m.media-amazon.com/images/M/MV5BNTg0NmI1ZGQtZTUxNC00NTgxLThjMDUtZmRlYmEzM2MwOWYwXkEyXkFqcGdeQXVyMzM4MjM0Nzg@._V1_.jpg


            }


        }

    }


    /*/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\*/

    //CÓDIGO PARA EL 'ACTIONBAR' CON LA POSIBILIDAD DE VOLVER HACIA ATRÁS.
    //EL 'finish()' cerrará la página actual
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // CÓDIGO PARA MOSTRAR LOS ICONOS DE LA CARPETA 'MENU'/'MENU_DETALLE.XML' EN EL "ACTIONBAR"
    // FUENTE: https://www.journaldev.com/9357/android-actionbar-example-tutorial
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return true;
    }

    // CÓDIGO PARA CUANDO EL USUARIO CLICA EN CADA UNA DE LOS ICONOS EN EL "ACTIONBAR"
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.edit:

                //UN "INTENT" SIRVE PARA INVOCAR COMPONENTES ("ACTIVITIES")
                Intent intent = new Intent(Detalle.this, Edicion.class);

                //RECOGEMOS EL 'ID' DE ESTA PELÍCULA ESPECÍFICA Y LO ENVIAMOS A LA 'Edicion.class'
                intent.putExtra("valor_id",idfilm);

                startActivity(intent);//IR A 'Edicion.class'

                return(true);

        }

        return(super.onOptionsItemSelected(item));
    }

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
