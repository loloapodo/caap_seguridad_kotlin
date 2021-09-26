package com.ello.kotlinseguridad.simple

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.kotlinseguridad.parseobj.Usuario
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.parseobj.Incidente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SInciVM() : ViewModel() {

     var ya_cargo_actividad=false

    lateinit var id_incidente:String
     val _listado_usuarios_dela_act = MutableLiveData<List<Usuario>>()
    val _incidente = MutableLiveData<Incidente>()



    suspend fun BorrarActividad(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){
        viewModelScope.launch { withContext(Dispatchers.IO) { CRUD.BorrarActividad(str_ObjectId,fg,fb) }}
    }

    fun CargarLaIncidente() {
        CRUD.CargarUnIncidenteLocal(id_incidente,{_incidente.value=it;ya_cargo_actividad=true;CargarServidor()},{CargarServidor()})
    }
    fun CargarServidor(){
        CRUD.CargarUnIncidente(id_incidente,{_incidente.value=it;ya_cargo_actividad=true},{})
    }





}