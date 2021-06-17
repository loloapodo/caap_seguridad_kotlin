package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.BIN.CRUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SUsuarioVM() : ViewModel() {

    lateinit var id_usuario:String

     fun BorrarUsuario(fg: () -> Unit,fb: () -> Unit)
    {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.BorrarUsuario(id_usuario,fg,fb) } }
    }



    fun CargarElUsuario(fg: (u:Usuario) -> Unit, fb: () -> Unit) {
        viewModelScope.launch { withContext(Dispatchers.IO){ CRUD.CargarUnUsuPIN(BIN.PIN_USU_SELECTED,fg,fb)} }
    }


}