package com.alfonso.ghibliapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Edicion extends AppCompatActivity {

    //DECLARACIONES
    CheckBox ch_favourite_pref;
    EditText et_comment_pref;
    EditText et_picture_pref;
    Button btn_guardar_pref;
    String idfilm;

    ArrayList<Preferencia> preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);

        //FIND VIEWERS BY IDs
        ch_favourite_pref = findViewById(R.id.ch_favourite_pref);
        et_comment_pref = findViewById(R.id.et_comment_pref);
        et_picture_pref = findViewById(R.id.et_picture_pref);
        btn_guardar_pref = findViewById(R.id.btn_guardar_pref);

        preferencias = new ArrayList<Preferencia>();


        //CÓDIGO PARA EL 'ACTIONBAR' CON LA POSIBILIDAD DE VOLVER HACIA ATRÁS
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //RECOGEMOS EL "ID" DE LA PELÍCULA ESPECÍFICA, ENVIADO DESDE 'Detalle.class'
        idfilm = getIntent().getStringExtra("valor_id");


        //BUSCAMOS POR "idfilm" LA 'PREFERENCIA' ESPECÍFICA Y NOS DEVOLVERÁ UN "ArrayList<Preferencia>()"
        // DE UN SÓLO VALOR, EN LA POSICIÓN 0
        preferencias.addAll(Preferencia.find(Preferencia.class, "idfilm = ?", idfilm));

        //SI HA PODIDO ENCONTRAR EL "idfilm" EL "SIZE" DEL "ArrayList<Preferencia>()" SERÁ MAYOR QUE 0
        if(preferencias.size() > 0 ) {

            //INSTANCIAMOS LA 'PREFERENCIA' ESPECÍFICA  DE LA POSICIÓN 0 DEL "ArrayList<Preferencia>()"
            Preferencia preferencia = preferencias.get(0);

            //IMPRIMIMOS EN PANTALLA LOS VALORES
            boolean preferida = preferencia.isPreferida();
            ch_favourite_pref.setChecked(preferida);

            String comentario = preferencia.getComentario();
            et_comment_pref.setText(comentario);

            String imagen = preferencia.getImagen();
            et_picture_pref.setText(imagen);

        }


        //CLIC AL BOTÓN DE "GUARDAR"
        btn_guardar_pref.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //SI HA PODIDO ENCONTRAR EL "idfilm" EL "SIZE" DEL "ArrayList<Preferencia>()" SERÁ MAYOR QUE 0
                //ACTUALIZAREMOS LA PREFERENCIA
                if(preferencias.size() > 0 ) {

                    String comentario = et_comment_pref.getText().toString();
                    String imagen = et_picture_pref.getText().toString();


                    if(comentario.equals(null)&&imagen.equals(null)){
                        //SI EL CAMPO 'COMENTARIO' Y EL CAMPO 'IMAGEN' ESTÁN VACÍOS INSERTARLES UN CAMPO BLANCO ("")
                        comentario = "";
                        imagen = "";

                        Preferencia preferencia = preferencias.get(0);

                        //HACEMOS 'UPDATE' DE LA "PREFERENCIA"
                        preferencia.setIdfilm(idfilm);

                        preferencia.setPreferida(ch_favourite_pref.isChecked());
                        preferencia.setComentario(comentario);

                        preferencia.setImagen(imagen);

                        preferencia.save();

                        finish();
                    }

                    else if(comentario.equals(null)){
                        //SI EL CAMPO 'COMENTARIO' ESTÁ VACÍO INSERTARLE UN CAMPO BLANCO ("")
                        comentario = "";

                        Preferencia preferencia = preferencias.get(0);

                        //HACEMOS 'UPDATE' DE LA "PREFERENCIA"
                        preferencia.setIdfilm(idfilm);

                        preferencia.setPreferida(ch_favourite_pref.isChecked());
                        preferencia.setComentario(comentario);

                        preferencia.setImagen(imagen);

                        preferencia.save();

                        finish();
                    }

                    else if(imagen.equals(null)){
                        //SI EL CAMPO 'IMAGEN' ESTÁ VACÍO INSERTARLE UN CAMPO BLANCO ("")
                        imagen = "";

                        Preferencia preferencia = preferencias.get(0);

                        //HACEMOS 'UPDATE' DE LA "PREFERENCIA"
                        preferencia.setIdfilm(idfilm);

                        preferencia.setPreferida(ch_favourite_pref.isChecked());
                        preferencia.setComentario(comentario);

                        preferencia.setImagen(imagen);

                        preferencia.save();

                        finish();
                    }

                    else{

                        Preferencia preferencia = preferencias.get(0);

                        //HACEMOS 'UPDATE' DE LA "PREFERENCIA"
                        preferencia.setIdfilm(idfilm);

                        preferencia.setPreferida(ch_favourite_pref.isChecked());
                        preferencia.setComentario(comentario);

                        preferencia.setImagen(imagen);

                        preferencia.save();

                        finish();
                    }

                }


                //SI ES UNA NUEVA 'PREFERENCIA' CREAMOS UNA NUEVA PREFERENCIA
                else{

                    String comentario = et_comment_pref.getText().toString();

                    String imagen = et_picture_pref.getText().toString();

                    if(comentario.equals(null)&&imagen.equals(null)){
                        //SI EL CAMPO 'COMENTARIO' Y EL CAMPO 'IMAGEN' ESTÁN VACÍOS INSERTARLES UN CAMPO BLANCO ("")
                        comentario = "";
                        imagen = "";

                        Preferencia preferencia = new Preferencia(idfilm,ch_favourite_pref.isChecked(),comentario,imagen);

                        preferencia.save();

                        finish();
                    }

                    else if(comentario.equals(null)){
                        //SI EL CAMPO 'COMENTARIO' ESTÁ VACÍO INSERTARLE UN CAMPO BLANCO ("")
                        comentario = "";

                        Preferencia preferencia = new Preferencia(idfilm,ch_favourite_pref.isChecked(),comentario,imagen);

                        preferencia.save();

                        finish();
                    }

                    else if(imagen.equals(null)){
                        //SI EL CAMPO 'IMAGEN' ESTÁ VACÍO INSERTARLE UN CAMPO BLANCO ("")
                        imagen = "";

                        Preferencia preferencia = new Preferencia(idfilm,ch_favourite_pref.isChecked(),comentario,imagen);

                        preferencia.save();

                        finish();
                    }

                    else{

                        Preferencia preferencia = new Preferencia(idfilm,ch_favourite_pref.isChecked(),comentario,imagen);

                        preferencia.save();

                        finish();
                    }


                }

            }

        });


    }

    /*/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\*/

    //CÓDIGO PARA EL 'ACTIONBAR' CON LA POSIBILIDAD DE VOLVER HACIA ATRÁS.
    //EL 'finish()' cerrará la página actual
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



}
