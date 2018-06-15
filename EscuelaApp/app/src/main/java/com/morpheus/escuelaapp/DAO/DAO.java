package com.morpheus.escuelaapp.DAO;

import java.util.List;

/**
 * Created by Morpheus on 13/06/2018.
 */

public abstract class DAO
{
    //Interfaz de un solo elemento
    public interface OnResultadoConsulta<T>
    {
        void consultaSuccess(T t);
        void consultaFailed(String error, int codigo);
    }

    //Interfaz con una lista de elementos
    public interface OnResultadoListaConsulta<T>
    {
        void consultaSuccess(List<T> t);
        void consultaFailed(String error, int codigo);
    }
}
