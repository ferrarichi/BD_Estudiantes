package com.example.juanjo.dbdiscos;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.juanjo.dbdiscos.Objetos.Asignatura;
import com.example.juanjo.dbdiscos.Objetos.Estudiante;
import com.example.juanjo.dbdiscos.Objetos.Profesor;

public class NuevoCampo extends AppCompatActivity {

    //Variables
    private MyDBAdapter dbAdapter;
    EditText editNombre, editEdad, editCiclo, editCurso, editNota, editDespacho, editHoras;
    Button btnGuardar;
    RadioGroup radioGroup;
    RadioButton rbEstudiante, rbProfesor, rbAsignatura;
    TextView txtTitulo;
    boolean estudiante, profesor, asignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        //Conexion Vista
        txtTitulo = (TextView) findViewById(R.id.txtNuevo);
        editNombre = (EditText) findViewById(R.id.editNombre);
        editEdad = (EditText) findViewById(R.id.editEdad);
        editCiclo = (EditText) findViewById(R.id.editciclo);
        editCurso = (EditText) findViewById(R.id.editCurso);
        editNota = (EditText) findViewById(R.id.editNota);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rbEstudiante = (RadioButton) findViewById(R.id.rbCiclo);
        rbProfesor = (RadioButton) findViewById(R.id.rbNuevoProfesor);
        editDespacho = (EditText) findViewById(R.id.editDespacho);

        editHoras = (EditText) findViewById(R.id.editHoras);

        estudiante = true;

        //Cargamos Base de datos SQLite
        dbAdapter = new MyDBAdapter(this);

        //Radio Buttons
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (rbProfesor.isChecked()){
                    profesor = true;
                    asignatura = false;

                    estudiante = false;

                }  else if (rbEstudiante.isChecked()){
                    estudiante = true;
                    asignatura = false;
                    profesor = false;

                }else{
                    asignatura = true;
                    profesor = false;
                    estudiante = false;

                }

                refrescarVista();
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnGuardar:
                if (estudiante){
                    nuevoEstudiante();
                } else if(profesor){
                    nuevoProfesor();
                }else{
                    nuevaAsignatura();
                }
                break;
        }
    }

    public void refrescarVista(){
        if (estudiante){

            editEdad.setVisibility(View.VISIBLE);
            editCiclo.setVisibility(View.VISIBLE);
            editCurso.setVisibility(View.VISIBLE);
            editNota.setVisibility(View.VISIBLE);
            editDespacho.setVisibility(View.GONE);

            editHoras.setVisibility(View.GONE);

            txtTitulo.setText("Nuevo Estudiante");
        } else if (profesor){
            editDespacho.setVisibility(View.VISIBLE);
            editNota.setVisibility(View.GONE);

            editEdad.setVisibility(View.VISIBLE);
            editCiclo.setVisibility(View.VISIBLE);
            editCurso.setVisibility(View.VISIBLE);
            editHoras.setVisibility(View.GONE);

            txtTitulo.setText("Nuevo Profesor");
        }else{
            editEdad.setVisibility(View.GONE);
            editCiclo.setVisibility(View.GONE);
            editCurso.setVisibility(View.GONE);
            editNota.setVisibility(View.GONE);
            editDespacho.setVisibility(View.GONE);
            editHoras.setVisibility(View.VISIBLE);
            txtTitulo.setText("Nueva Asignatura");

        }
    }

    private void nuevoProfesor() {
        //Obtenemos datos
        String nombre = editNombre.getText().toString();
        int edad = Integer.parseInt(editEdad.getText().toString());
        String ciclo = editCiclo.getText().toString();
        String curso = editCurso.getText().toString();
        String despacho = editDespacho.getText().toString();

        //Creamos objeto estudiante
        Profesor nuevoProfesor = new Profesor(nombre,edad,ciclo,curso,despacho);

        dbAdapter.open();
        dbAdapter.insertarProfesor(nuevoProfesor);
        dbAdapter.close();

        Toast.makeText(getApplicationContext(),"Nuevo profesor " + nombre + " ha sido generado",Toast.LENGTH_LONG).show();
    }

    private void nuevoEstudiante() {
        //Obtenemos datos
        String nombre = editNombre.getText().toString();
        int edad = Integer.parseInt(editEdad.getText().toString());
        String ciclo = editCiclo.getText().toString();
        String curso = editCurso.getText().toString();
        float nota = Float.parseFloat(editNota.getText().toString());

        //Creamos objeto estudiante
        Estudiante nuevoEstudiante = new Estudiante(nombre,edad,ciclo,curso,nota);

        dbAdapter.open();
        dbAdapter.insertarEstudiante(nuevoEstudiante);
        dbAdapter.close();

        Toast.makeText(getApplicationContext(),"Nuevo estudiante " +  nombre +" ha sido generado",Toast.LENGTH_LONG).show();
    }

    private void nuevaAsignatura() {

        String nombre = editNombre.getText().toString();
        int horas = Integer.parseInt(editHoras.getText().toString());


        //Creamos objeto estudiante
        Asignatura nuevaAsignatura = new Asignatura(nombre,horas);

        dbAdapter.open();
        dbAdapter.insertarAsignatura(nuevaAsignatura);
        dbAdapter.close();

        Toast.makeText(getApplicationContext(),"Nueva asignatura: " +  nombre +" ha sido generada",Toast.LENGTH_LONG).show();

    }

    }