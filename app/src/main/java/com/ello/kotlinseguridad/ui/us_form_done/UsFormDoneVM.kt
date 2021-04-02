package com.ello.kotlinseguridad.ui.us_form_done

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsFormDoneVM : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
    val _listado = MutableLiveData<List<Formulario>>()


    fun Cargar()
        {
            val usuario= BIN.CARGAR_USUARIO_LOGED()
            if (usuario==null){Log.e("Error","3330");return}

        viewModelScope.launch(Dispatchers.IO) {
            CRUD.CargarTodosFormulariosRespondidos(usuario,{_listado.value=it;Log.e("CargarTodosForm done","ss");},{})
        }

    }








}