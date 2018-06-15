package com.morpheus.escuelaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.morpheus.escuelaapp.DAO.DAO;
import com.morpheus.escuelaapp.DAO.UsuarioDAO;
import com.morpheus.escuelaapp.Entidades.Usuario;

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText edtNick = (EditText)findViewById(R.id.edtNick);
        final EditText edtPass = (EditText)findViewById(R.id.edtPass);
        Button btAceptar = (Button)findViewById(R.id.btLogin);

        btAceptar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(edtNick.getText().length() == 0 && edtPass.getText().length() == 0)
                    Toast.makeText(LoginActivity.this, "Llene los campos", Toast.LENGTH_SHORT).show();
                else
                {
                    UsuarioDAO dao = UsuarioDAO.getInstance();
                    dao.obtenerDatosLogin(LoginActivity.this, edtNick.getText().toString(),
                            edtPass.getText().toString(), new DAO.OnResultadoConsulta<Usuario>()
                            {
                                @Override
                                public void consultaSuccess(Usuario s)
                                {
                                    if(s != null)
                                    {
                                        //Pasamos a la siguiente actividad con todos los datos del Usuario
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("USUARIO", s);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                        Toast.makeText(LoginActivity.this, "No hay datos", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void consultaFailed(String error, int codigo)
                                {
                                    Toast.makeText(LoginActivity.this, error + " " + codigo, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

}
