package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.ParseObj.Actividad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SEmpleadoVM() : ViewModel() {

    lateinit var id_usuario:String
    val _empleado = MutableLiveData<Usuario>()
    var ya_cargo=false


     fun BorrarUsuario(fg: () -> Unit,fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.BorrarUsuario(id_usuario,fg,fb) } }
    }



    fun Cargar() {
        viewModelScope.launch (Dispatchers.Main){
        CRUD.CargarUnUsuarioLocal(id_usuario,{_empleado.value=it;ya_cargo=true;CargarServidor()},{CargarServidor()})
    }


}

    private fun CargarServidor() {
        viewModelScope.launch (Dispatchers.IO){
            CRUD.CargarUnUsuario(id_usuario,{_empleado.value=it;ya_cargo=true;},{})
        }
    }


}
