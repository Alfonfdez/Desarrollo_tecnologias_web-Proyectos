package com.alfonso.ghibliapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    //DECLARACIONES
    EditText et_email;
    EditText et_password;
    TextView tv_website;
    Button btn_login;
    SharedPreferences prefs;
    boolean ir_a_listado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FIND VIEWERS BY ID
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_website = findViewById(R.id.tv_website);
        ir_a_listado = false;

        //INICIALIZACIÓN
        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        //VER SI HEMOS GUARDADO UN 'LOGIN' ANTERIORMENTE
        //EN CASO AFIRMATIVO: EN 'SHARED PREFERENCES' EL "BOOLEAN: ir_a_listado" SERÁ "TRUE" E IREMOS A 'Listado.class'
        //EN CASO NEGATIVO: EL "BOOLEAN: ir_a_listado" SERÁ "FALSE" Y CONTINUAREMOS EN ESTA 'ACTIVITY'
        ir_a_listado = prefs.getBoolean("user_login", false);

        if(ir_a_listado==true){

            //UN "INTENT" SIRVE PARA INVOCAR COMPONENTES ("ACTIVITIES")
            Intent intent = new Intent(MainActivity.this, Listado.class);
            //IR A 'Listado.class'
            startActivity(intent);

            finish();
        }

        //CLIC AL BOTÓN DE 'LOGIN'
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(email.equals("")&&password.equals("")){
                    //SI EL CAMPO 'EMAIL' Y EL CAMPO 'PASSWORD' ESTÁN VACÍOS SALTARÁ UN "TOAST"
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_login), Toast.LENGTH_LONG).show();
                }

                else if (email.equals("")) {
                    //SI EL CAMPO 'EMAIL' ESTÁ VACÍO SALTARÁ UN "TOAST"
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_login_email), Toast.LENGTH_LONG).show();
                }

                else if(password.equals("")){
                    //SI EL CAMPO 'PASSWORD' ESTÁ VACÍO SALTARÁ UN "TOAST"
                    Toast.makeText(getApplicationContext(),getString(R.string.toast_login_password),Toast.LENGTH_LONG).show();
                }

                else {

                    //GUARDAR EL 'EMAIL' Y EL 'PASSWORD' EN "SHARED PREFERENCES" PARA QUE LA PRÓXIMA VEZ VAYA DIRECTAMENTE
                    //A LA 'ACTIVITY: Listado.class'
                    ir_a_listado = true;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("user_login", ir_a_listado);
                    editor.commit();

                    //UN "INTENT" SIRVE PARA INVOCAR COMPONENTES ("ACTIVITIES")
                    Intent intent = new Intent(MainActivity.this, Listado.class);
                    //IR A 'Listado.class'
                    startActivity(intent);

                    finish();
                }
            }

        });

        tv_website.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.studioghibli.com.au"));
                startActivity(intent);

            }

        });

    }



}
