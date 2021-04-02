package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.BIN.CRUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SActivVM() : ViewModel() {

    lateinit var id_actividad:String

    suspend fun BorrarActividad(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){

        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.BorrarActividad(str_ObjectId,fg,fb) }}

    }

    fun CargarTodosUsuariosdeActividad(act: Actividad, fg: (list:List<Usuario>) -> Unit, fb: () -> Unit) {

        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.CargarTodosUsuariosdeActividad(act,fg,fb) } }

    }

    fun CargarLaActividad(fg:(activ:Actividad) -> Unit, fb:() -> Unit) {

        CRUD.CargarUnActividadLocal(id_actividad,fg,fb)

    }


}