package com.ello.kotlinseguridad.ui.adm_usuarios

import android.util.Log
import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.CRUD
import com.ello.kotlinseguridad.MDebug
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.parse.FindCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.*

class AdmUsuariosVM : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    public val _listado = MutableLiveData<List<Usuario>>()

   fun CargarUsuarios()
   {
       viewModelScope.launch (Dispatchers.IO){

              CRUD.CargarTodosUsuarioLocal({_listado.value=it;Log.e("local","usuario"); },{})
                delay(4000)
              CRUD.CargarTodosUsuario({_listado.value=it;Log.e("server","usuario");}, {})

       }

   }








}