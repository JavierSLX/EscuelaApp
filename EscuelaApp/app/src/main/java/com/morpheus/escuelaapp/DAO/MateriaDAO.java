package com.morpheus.escuelaapp.DAO;

import android.app.ProgressDialog;
import android.content.Context;

import com.morpheus.escuelaapp.Entidades.Materia;
import com.morpheus.escuelaapp.Recursos.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morpheus on 14/06/2018.
 */

public class MateriaDAO
{
    private static MateriaDAO dao;
    private ProgressDialog progressDialog;

    private MateriaDAO()
    {
    }

    public static MateriaDAO getInstance()
    {
        if(dao == null)
            dao = new MateriaDAO();

        return dao;
    }

    public void getListaMaterias(Context context, final DAO.OnResultadoListaConsulta<Materia> listener)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Espere, por favor...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String url = Constantes.HOST + Constantes.CARPETA_DAO + "main/listaMaterias.php";
        PeticionHTTP.GET get = new PeticionHTTP.GET(context, url);
        get.getResponseString(new PeticionHTTP.OnConsultaListener<String>()
        {
            @Override
            public void onSuccess(String respuesta)
            {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                try
                {
                    JSONArray array = new JSONArray(respuesta);
                    if(array.length() > 0)
                    {
                        //Armamos la lista de objetos de tipo Materia
                        List<Materia> lista = new ArrayList<Materia>();
                        for(int i = 0; i < array.length(); i++)
                        {
                            JSONObject json = array.getJSONObject(i);
                            Materia materia = new Materia(Integer.parseInt(json.getString("id")),
                                    json.getString("clave"), json.getString("nombre"),
                                    Boolean.parseBoolean(json.getString("activo")));

                            lista.add(materia);
                        }

                        //Regresamos de manera asincrona la lista
                        listener.consultaSuccess(lista);
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
