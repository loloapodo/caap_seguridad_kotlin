package com.ello.kotlinseguridad.responder

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.bin.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.kotlinseguridad.parseobj.Pregunta
import com.ello.kotlinseguridad.parseobj.Respuesta
import com.ello.kotlinseguridad.parseobj.Usuario
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.launch


class RDoneVM : ViewModel() {



    lateinit var id: String
    lateinit var usuarId: String
    lateinit var form: Formulario
    lateinit var act: Actividad
     var usuar=MutableLiveData<Usuario>()
    var f_nombre=MutableLiveData<String>()



    var _listPRE = MutableLiveData<List<Pregunta>>()
    var _listRES = MutableLiveData<List<Respuesta>>()

    var estado = MutableLiveData<Estado>()

    init {
        estado.value=Estado.Idle
    }

    fun Cargar()//Inicializa los valores del usuario y formulario
    {




        estado.value=Estado.Network

            form=BIN.getThisForm()!!
            act=BIN.getThisAct()!!
            usuar.value=BIN.getThisUser()

                viewModelScope.launch {

                    CRUD.CargarTodasPreguntasDelFormularioLocal(form,{PREG->
                        Log.e("TodasPreguntasDelFormL","$PREG.size")
                        CRUD.CargarTodasRespuestasLocal(usuar.value!!,form,act,{RESP->
                            Log.e("TodasRespL","$RESP.size")
                            estado.value=Estado.Idle
                            _listPRE.value=PREG
                            _listRES.value=RESP
                            CargarServidor(PREG)
                        },{CargarServidor(PREG)})

            },{}) }

    }

    private fun CargarServidor(PREG:List<Pregunta>) {
        Log.e("CargarServidor","CargarTodasRespuestas")
        CRUD.CargarTodasRespuestas(usuar.value!!,form,act, { RESP ->
            _listPRE.value = PREG
            _listRES.value = RESP
        },{})


    }


    fun isNotIdle(): Boolean {
       return estado.value!=Estado.Idle
    }


}