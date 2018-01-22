package com.example.juanjo.dbdiscos;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.juanjo.dbdiscos.Adaptadores.AdaptadorAsignaturas;
import com.example.juanjo.dbdiscos.Adaptadores.AdaptadorEstudiantes;
import com.example.juanjo.dbdiscos.Adaptadores.AdaptadorProfesores;
import com.example.juanjo.dbdiscos.Objetos.Asignatura;
import com.example.juanjo.dbdiscos.Objetos.Estudiante;
import com.example.juanjo.dbdiscos.Objetos.Profesor;

import java.util.ArrayList;

public class activity_buscador extends AppCompatActivity {

    //Variables
    public MyDBAdapter dbAdapter;
    EditText editCiclo, editCurso;

    ArrayList<Estudiante> estudiantes;
    ArrayList<Profesor> profesores;
    ArrayList<Asignatura> asignaturas;

    AdaptadorEstudiantes adapterE;
    AdaptadorProfesores adapterP;
    AdaptadorAsignaturas adapterA;

    Button btnBuscar;
    TextView txtTitulo;
    RadioGroup radioGroup;
    CheckBox cbEstudiante, cbProfesor, cbAsignaturas;
    RadioButton rbCiclo, rbCurso, rbTodo, rbTotal;
    boolean ciclo, todo, curso, total;
    ListView listEstudiantes, listProfesores, listAsignaturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        //Conexion Vista
        btnBuscar = (Button) findViewById(R.id.buscar);
        editCiclo = (EditText) findViewById(R.id.buscarCiclo);
        editCurso = (EditText) findViewById(R.id.buscarCurso);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo_buscar);
        cbEstudiante = (CheckBox) findViewById(R.id.checkBox_estudiante);
        cbProfesor = (CheckBox) findViewById(R.id.checkBox_profesor);
        cbAsignaturas = (CheckBox) findViewById(R.id.checkBox_asignaturas);

        estudiantes = new ArrayList<Estudiante>();
        profesores = new ArrayList<Profesor>();
        asignaturas = new ArrayList<Asignatura>();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_buscar);
        rbCiclo = (RadioButton) findViewById(R.id.rbCiclo);
        rbCurso = (RadioButton) findViewById(R.id.rbCurso);
        rbTodo = (RadioButton) findViewById(R.id.rbTodo);
        rbTotal = (RadioButton) findViewById(R.id.rbTotal);

        listEstudiantes = (ListView) findViewById(R.id.EstudiantesView);
        listProfesores = (ListView) findViewById(R.id.ProfesoresView);
        listAsignaturas = (ListView) findViewById(R.id.AsignaturasView);

        ciclo = false;
        curso = false;
        todo = false;
        total = false;


        adapterE = new AdaptadorEstudiantes(this, estudiantes);
        listEstudiantes.setAdapter(adapterE);
        adapterP = new AdaptadorProfesores(this, profesores);
        listProfesores.setAdapter(adapterP);
        adapterA = new AdaptadorAsignaturas(this, asignaturas);
        listAsignaturas.setAdapter(adapterA);

        //Cargamos Base de datos SQLite
        dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();


        //Radio Buttons
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (rbCurso.isChecked()){
                    curso=true;
                    ciclo = false;
                    todo = false;
                    total = false;

                } else if(rbCiclo.isChecked()){
                    ciclo = true;
                    curso=false;
                    todo = false;
                    total = false;

                }else if(rbTodo.isChecked()){
                    todo = true;
                    ciclo = false;
                    curso=false;
                    total = false;

                }else if (rbTotal.isChecked()){
                    total = true;
                    todo = false;
                    ciclo = false;
                    curso=false;
                }

                refrescarVista();
            }
        });


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.buscar:

                editCurso.setVisibility(View.GONE);
                editCiclo.setVisibility(View.GONE);
                actualizar();
                break;
        }
    }

    public void refrescarVista(){
        //Estudiante / Profesor / Asignaturas
        if (todo){
            editCurso.setVisibility(View.VISIBLE);
            editCiclo.setVisibility(View.VISIBLE);
        }
        if (curso) {
            editCurso.setVisibility(View.VISIBLE);
            editCiclo.setVisibility(View.GONE);

        }
        if (ciclo) {
            editCiclo.setVisibility(View.VISIBLE);
            editCurso.setVisibility(View.GONE);

        }

        if (total){
            editCurso.setVisibility(View.GONE);
            editCiclo.setVisibility(View.GONE);
        }




    }

    private void actualizar() {

        if (cbEstudiante.isChecked()){
            listEstudiantes.setVisibility(View.VISIBLE);

            String filtros = " WHERE ";
            if (editCiclo.getText().toString().length() > 0){
                filtros += "ciclo='" + editCiclo.getText().toString() + "'";
            }

            if (editCiclo.getText().toString().length() > 0 && editCurso.getText().toString().length() > 0){
                filtros += " AND ";
            }

            if (editCurso.getText().toString().length() > 0){
                filtros += "curso='" + editCurso.getText().toString() + "'";
            }

            //Evaluador FINAL de filtros
            if (!filtros.equals(" WHERE ")){
                //Se han modificado filtros
                estudiantes = dbAdapter.filtroEstudiantes(filtros);
            } else {
                //No se han añadido filtros
                estudiantes = dbAdapter.llenarEstudiantes();
            }

            adapterE = new AdaptadorEstudiantes(this, estudiantes);
            listEstudiantes.setAdapter(adapterE);
        } else {
            listEstudiantes.setVisibility(View.GONE);
        }

        if (cbProfesor.isChecked()){
            listProfesores.setVisibility(View.VISIBLE);

            String filtros = " WHERE ";
            if (editCiclo.getText().toString().length() > 0){
                filtros += "ciclo='" + editCiclo.getText().toString() + "'";
            }

            if (editCiclo.getText().toString().length() > 0 && editCurso.getText().toString().length() > 0){
                filtros += " AND ";
            }

            if (editCurso.getText().toString().length() > 0){
                filtros += "curso='" + editCurso.getText().toString() + "'";
            }

            //Evaluador FINAL de filtros
            if (!filtros.equals(" WHERE ")){
                //Se han modificado filtros
                profesores = dbAdapter.filtroProfesores(filtros);
            } else {
                //No se han añadido filtros
                profesores = dbAdapter.llenarProfesores();
            }


            adapterP = new AdaptadorProfesores(this, profesores);
            listProfesores.setAdapter(adapterP);
        } else {
            listProfesores.setVisibility(View.GONE);
        }

        if (cbAsignaturas.isChecked()){
            listAsignaturas.setVisibility(View.VISIBLE);

            String filtros = " WHERE ";
            if (editCiclo.getText().toString().length() > 0){

            }

            if (editCiclo.getText().toString().length() > 0 && editCurso.getText().toString().length() > 0){

            }

            if (editCurso.getText().toString().length() > 0){
            }

            //Evaluador FINAL de filtros
            if (!filtros.equals(" WHERE ")){
                //Se han modificado filtros

            } else {
                //No se han añadido filtros
                asignaturas = dbAdapter.llenarAsignaturas();
            }

            adapterA = new AdaptadorAsignaturas(this, asignaturas);
            listAsignaturas.setAdapter(adapterA);
        } else {
            listAsignaturas.setVisibility(View.GONE);
        }


    }

}
