package com.morpheus.escuelaapp.DAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.morpheus.escuelaapp.Entidades.Categoria;
import com.morpheus.escuelaapp.Entidades.Credencial;
import com.morpheus.escuelaapp.Entidades.Usuario;
import com.morpheus.escuelaapp.Recursos.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Morpheus on 13/06/2018.
 */

public class UsuarioDAO
{
    private static UsuarioDAO dao;
    private ProgressDialog progressDialog;

    private UsuarioDAO()
    {
    }

    public static UsuarioDAO getInstance()
    {
        if(dao == null)
            dao = new UsuarioDAO();

        return dao;
    }

    public void obtenerDatosLogin(Context context, final String nick, final String pass, final DAO.OnResultadoConsulta<Usuario> listener)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Espere por favor...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String url = Constantes.HOST + Constantes.CARPETA_DAO + "login/acceso.php";
        Map<String, String> params = new HashMap<>();
        params.put("nick", nick);
        params.put("pass", pass);

        PeticionHTTP.POST post = PeticionHTTP.POST.getInstance(context, url, params);
        post.getResponse(new PeticionHTTP.OnConsultaListener<String>()
        {
            @Override
            public void onSuccess(String respuesta)
            {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                try
                {
                    JSONArray array = new JSONArray(respuesta);
                    JSONObject json;

                    if(array.length() > 0)
                    {
                        json = array.getJSONObject(0);
                        Credencial credencial = new Credencial(nick, pass);
                        Categoria categoria = new Categoria(Integer.parseInt(json.getString("categoriaid")),
                                json.getString("categoria"), true);
                        Usuario usuario = new Usuario(Integer.parseInt(json.getString("usuarioid")),
                                json.getString("nombre"), credencial, Boolean.parseBoolean(json.getString("activo")),
                                categoria);

                        listener.consultaSuccess(usuario);
                    }
                    else
                        listener.consultaSuccess(null);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    listener.consultaSuccess(null);
                }
            }

            @Override
            public void onFailed(String error, int respuestaHTTP)
            {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                listener.consultaFailed(error, respuestaHTTP);
            }
        });
    }
}
