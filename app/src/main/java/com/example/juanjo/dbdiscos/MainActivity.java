package com.example.juanjo.dbdiscos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Variables
    public MyDBAdapter dbAdapter;
    Button btnNuevo, btnRecargar, btnEliminar;
    TextView txtCountEstudiantes, txtCountProfesores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Conexion elementos vista
        btnNuevo = (Button) findViewById(R.id.btnAñadir);
        btnRecargar = (Button) findViewById(R.id.refrescar);
        btnEliminar = (Button) findViewById(R.id.Eliminar);
        txtCountEstudiantes = (TextView) findViewById(R.id.txtCountEstudiantes);
        txtCountProfesores = (TextView) findViewById(R.id.txtCountProfesores);

        //Cargamos Base de datos SQLite
        dbAdapter = new MyDBAdapter(this);
        dbAdapter.open();
        recargar();
    }

    @Override
    protected void onResume() {
        recargar();
        super.onResume();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnAñadir:
                Intent ventanaNuevo = new Intent(this,NuevoCampo.class);
                startActivity(ventanaNuevo);
                break;
            case R.id.refrescar:
                recargar();
                Toast.makeText(getApplicationContext(),"Elementos recargados",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Eliminar:
                Intent ventanaEliminar = new Intent(this, activity_eliminar.class);
                startActivity(ventanaEliminar);
                break;
            case R.id.Buscar:
                Intent ventanaBuscar = new Intent(this, activity_buscador.class);
                startActivity(ventanaBuscar);
                break;
        }
    }

    public void recargar(){
        //Contadores
        int estudiantes = 0;
        try {
            estudiantes = dbAdapter.contarRegistrosEstudiantes();
        }catch (Exception e){
            Log.w("#TEMP","Error!");
        }
        txtCountEstudiantes.setText("Estudiantes: " + estudiantes);
        int profesores = 0;
        try {
            profesores = dbAdapter.contarRegistrosProfesores();
        }catch (Exception e){
            Log.w("#TEMP","Error!");
        }
        txtCountProfesores.setText("Profesores: " + profesores);
    }
}