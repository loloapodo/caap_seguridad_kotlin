package com.ello.kotlinseguridad.responder

import android.content.Context
import android.graphics.Bitmap
import android.hardware.usb.UsbInterface
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import com.parse.ParseObject
import com.parse.ParsePush
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class RDoneVM : ViewModel() {



    lateinit var id: String
    lateinit var usuarId: String
    lateinit var form: Formulario
     var usuar=MutableLiveData<Usuario>()



    var _listPRE = MutableLiveData<List<Pregunta>>()
    var _listRES = MutableLiveData<List<Respuesta>>()

    var estado = MutableLiveData<Estado>()

    init {
        estado.value=Estado.Idle
    }

    fun Cargar()//Inicializa los valores del usuario y formulario
    {
        estado.value=Estado.Network
        CRUD.CargarUnFormularioLocal(id,{ f ->
            form=f
            viewModelScope.launch {CRUD.CargarUnUsuario(usuarId,{ u-> usuar.value=u;
                viewModelScope.launch {

                    CRUD.CargarTodasPreguntasDelFormulario(form,{PREG->
                        CRUD.CargarTodasRespuestas(usuar.value!!,form,{RESP->
                            estado.value=Estado.Idle
                            _listPRE.value=PREG
                            _listRES.value=RESP
                        },{})
                    },{}) }
            },{}) }
        },{})
    }


    fun isNotIdle(): Boolean {
       return estado.value!=Estado.Idle
    }


}