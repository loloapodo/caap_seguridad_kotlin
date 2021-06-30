package com.ello.kotlinseguridad.ui.us_form_undone

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsFormUnDoneVM : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    val _listado = MutableLiveData<List<Actividad>>()


    fun Cargar()
        {
            val usuario= BIN.CARGAR_USUARIO_LOGED()
            if (usuario==null){Log.e("Error","3330");return}

        viewModelScope.launch(Dispatchers.Main) {
            CRUD.CargarTodosActividadesNoRespondidosLocal(usuario,{
                _listado.value=it;CargarServidor(usuario);},{})
        }

    }

    fun CargarServidor(u:Usuario){

        viewModelScope.launch(Dispatchers.IO){
            CRUD.CargarTodosActividadesNoRespondidos(u,{
                _listado.value=it;Log.e("CargarTodosACT undone","red");},{})
        CRUD.CargarTodasPreguntas({},{})
        }



    }








}