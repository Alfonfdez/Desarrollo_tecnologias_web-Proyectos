package com.alfonso.ghibliapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {

    ArrayList<Film> objects; //Corresponde al array de cosas del ListadoActivity.
    Context context; //Referencia al MainActivity
    int resource; //identificador del layout fila

    //CONSTRUCTOR
    public FilmAdapter(Context context, int resource, ArrayList<Film> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(resource, parent, false);

        //MOSTRARLO AL ROW
        //TITLE
        TextView textView_title = (TextView)row.findViewById(R.id.tv_title);
        textView_title.setText(objects.get(position).getTitle());

        //RELEASE DATE(YEAR)
        TextView textView_release_date = (TextView)row.findViewById(R.id.tv_release_date);
        textView_release_date.setText(String.valueOf(objects.get(position).getRelease_date()));

        return row;

    }

}
