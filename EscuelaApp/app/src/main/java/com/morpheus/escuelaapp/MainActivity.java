package com.morpheus.escuelaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.morpheus.escuelaapp.Adapters.MateriaAdapter;
import com.morpheus.escuelaapp.DAO.DAO;
import com.morpheus.escuelaapp.DAO.MateriaDAO;
import com.morpheus.escuelaapp.Entidades.Materia;
import com.morpheus.escuelaapp.Entidades.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos nuestro usuario del login
        Usuario usuario = (Usuario)getIntent().getParcelableExtra("USUARIO");

        TextView txtBienvenida = (TextView)findViewById(R.id.txtBienvenida);
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);

        String mensaje = "Bienvenido " + usuario.getCategoria().getNombre() + " "
                + usuario.getNombre() + ". Esta es la lista de materias que existen";
        txtBienvenida.setText(mensaje);

        //Llenamos el recyclerView
        MateriaDAO.getInstance().getListaMaterias(this, new DAO.OnResultadoListaConsulta<Materia>()
        {
            @Override
            public void consultaSuccess(List<Materia> t)
            {
                if(t != null)
                {
                    MateriaAdapter adapter = new MateriaAdapter(t);
                    recyclerView.setAdapter(adapter);

                    //Configuracion del recycler
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
                else
                    Toast.makeText(MainActivity.this, "Error en MateriaDAO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void consultaFailed(String error, int codigo)
            {
                String TAG = MainActivity.class.getSimpleName();
                Toast.makeText(MainActivity.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
                Log.e(TAG, error + " " + codigo);
            }
        });
    }
}
