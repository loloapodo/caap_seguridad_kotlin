package com.ello.kotlinseguridad.Simple

import androidx.lifecycle.MutableLiveData
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
    public val _listado_act_del_usu = MutableLiveData<List<Usuario>>()

    suspend fun BorrarActividad(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){
        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.BorrarActividad(str_ObjectId,fg,fb) }}
    }

    fun CargarLaActividad(fg:(activ:Actividad) -> Unit, fb:() -> Unit) {
        CRUD.CargarUnActividadLocal(id_actividad,fg,fb)
    }

    fun CargarTodosUsuariosdeActividad(act: Actividad) {
        viewModelScope.launch { withContext(Dispatchers.Main) {
            CRUD.CargarTodosUsuariosdeActividadLocal(act,{_listado_act_del_usu.value=it;CargarServidor(act)},{CargarServidor(act)}) } }
    }

    fun CargarServidor(a:Actividad){
        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.CargarTodosUsuariosdeActividad(a,{_listado_act_del_usu.value=it},{}) } }
    }




}