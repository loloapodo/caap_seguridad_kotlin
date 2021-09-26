package com.ello.kotlinseguridad.simple

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.kotlinseguridad.parseobj.Usuario
import com.ello.kotlinseguridad.bin.CRUD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SActivVM() : ViewModel() {

     var ya_cargo_actividad=false

    lateinit var id_actividad:String
     val _listado_usuarios_dela_act = MutableLiveData<List<Usuario>>()
    val _actividad = MutableLiveData<Actividad>()
    val ya_cargo_formul_de_la_act = MutableLiveData<Boolean>()

    init {
        ya_cargo_formul_de_la_act.value=false
    }

    suspend fun BorrarActividad(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){
        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.BorrarActividad(str_ObjectId,fg,fb) }}
    }

    fun CargarLaActividad() {
        CRUD.CargarUnActividadLocal(id_actividad,{_actividad.value=it;ya_cargo_actividad=true;CargarServidor()},{CargarServidor()})

    }
    fun CargarServidor(){
        CRUD.CargarUnActividad(id_actividad,{_actividad.value=it;ya_cargo_actividad=true},{})
    }



    fun CargarTodosUsuariosdeActividad(act: Actividad) {
        viewModelScope.launch { withContext(Dispatchers.Main) {
            CRUD.CargarTodosUsuariosdeActividadLocal(act,{_listado_usuarios_dela_act.value=it;},{CargarServidor(act)}) } }
    }

    fun CargarServidor(a:Actividad){
     //  fue realizado al abrir la aplicacion. Aqui lo llamo por si acaso
      viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.CargarTodosUsuariosdeActividad(a,{_listado_usuarios_dela_act.value=it},{}) } }

    }

    fun PUEDE_REVISAR_PREGUNTAS(): Boolean {
        return ya_cargo_actividad
    }


}