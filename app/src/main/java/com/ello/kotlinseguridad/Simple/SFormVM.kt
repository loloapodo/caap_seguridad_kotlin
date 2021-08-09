package com.ello.kotlinseguridad.Simple

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.launch


class SFormVM() : ViewModel() {

    lateinit var  nombre_formulario:String
    lateinit var id_formulario:String
    lateinit var f:Formulario
    lateinit var a: Actividad

    lateinit var id_usuario:String


    var lista_resueltos= ArrayList<Respuesta>()
    var esta_resuelto=MutableLiveData<Boolean>()
    var estado = MutableLiveData<Estado>()

    init {
        estado.value= Estado.Idle
        esta_resuelto.value=false
    }


    fun CargarElFormulario(fg: (f:Formulario) -> Unit, fb: () -> Unit){
        viewModelScope.launch {
            CRUD.CargarUnFormularioLocal(id_formulario,{ nombre_formulario=it.nombre!!;fg(it)},{
                CRUD.CargarUnFormulario(id_formulario,{nombre_formulario=it.nombre!!;fg(it)},fb)
            })
        }
    }


     fun CargarTodasPreguntasDelFormulario(formulario: Formulario, fg: (list:List<Pregunta>) -> Unit, fb: () -> Unit){

    viewModelScope.launch { CRUD.CargarTodasPreguntasDelFormularioLocal(formulario, fg,
            {viewModelScope.launch {CRUD.CargarTodasPreguntasDelFormulario(formulario,fg,fb)}})}

    }





     fun BorrarFormulario(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit) {
    viewModelScope.launch { CRUD.BorrarFormulario(str_ObjectId,fg,fb) }
}

    fun DeterminarListadoEnvios(fg: (listUnaRespPorFormu:ArrayList<Respuesta>)-> Unit,fb: () -> Unit) {
        estado.value=Estado.Network
        Log.e("DeterminarListadoEnvios","SFormVM")

        a=BIN.getThisAct()!!
        f=BIN.getThisForm()!!;



            Log.e("Before ","CargarTodosUsuariosdeActividadLocal")
            viewModelScope.launch {

                CRUD.CargarTodosUsuariosdeActividadLocal(a, {
                    CargarRespuestas(it,fg)

                }, {
                    viewModelScope.launch {
                        CRUD.CargarTodosUsuariosdeActividad(a, {
                            CargarRespuestas(it,fg)
                        }, {})
                    }
                })


            }









    }

    //2222222222222222222222222222222222222222222222222222222
    private fun CargarRespuestas(todosUsYAdm: List<Usuario>, fg: (listUnaRespPorFormu: ArrayList<Respuesta>) -> Unit) {
        viewModelScope.launch { CRUD.CargarTodasRespuestas(a,f,{ todasResp_delActForm->
            Determinar(todosUsYAdm,todasResp_delActForm,f,a,fg);//Local
            CRUD.CargarTodasRespuestas(a,f,{t->Determinar(todosUsYAdm,t,f,a,fg); },{})//DEL SERVIDOR
        },{CRUD.CargarTodasRespuestas({t->Determinar(todosUsYAdm,t,f,a,fg); },{})})
        }//DEL SERVIDOR


    }

    private fun Determinar(todosUsYAdm: List<Usuario>, todasResp: List<Respuesta>,f:Formulario,esta_actividad:Actividad?,fg: (listUnaRespPorFormu:ArrayList<Respuesta>) -> Unit) {
        Log.e("Determinar","Called")
        val todosUs=PurgarAdmins(todosUsYAdm)


        val retList= ArrayList<Respuesta>()
        userloop@for (u in todosUs.indices){
            var added=false
            Log.e("Indice de Usuario",u.toString())


            resp_loop@for (r in todasResp){
                Log.e("Buscando Respuesta", "${r.respuesta!!}  ${r.objectId}")
                if (r.ref_usuario!!.objectId==todosUs[u].objectId)
                {
                    retList.add(r)
                    added=true
                    Log.e("Añadida","Usuario:${todosUs[u].nom_apell} RespuestaId:${r.objectId} Descrip:${r.respuesta} Fecha:${r.fecha.toString()}")
                    break@resp_loop
                }
            }
            if (!added){
                Log.e("Añadida","Usuario:${todosUs[u].nom_apell} ")
                val emptyRes=Respuesta()
                emptyRes.ref_usuario=todosUs[u]
                emptyRes.ref_formulario=f
                emptyRes.ref_actividad=esta_actividad
                emptyRes.fecha=0L
                retList.add(emptyRes);
            }
        }

        fg(retList);
        estado.value=Estado.Idle
    }



    private fun PurgarAdmins(todos: List<Usuario>): ArrayList<Usuario> {

        val retList=ArrayList<Usuario>()
        for (u in todos){
            if (!u.adm){retList.add(u)}
            else       {Log.e("Admin Excluido",u.nom_apell!!)}
        }
        return retList
    }

    fun PreguntaSiFueResuelto(c:Context) {
        BIN.ESTA_RESUELTO_LA_ACT_PARA_ESTE_USUARIO(c) { esta_resuelto.value = true }
    }


}
