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
    lateinit var este_formulario:Formulario

    lateinit var id_usuario:String


    var lista_resueltos= ArrayList<Respuesta>()
    var esta_resuelto=MutableLiveData<Boolean>()
    var estado = MutableLiveData<Estado>()

    init {
        estado.value= Estado.Idle
        esta_resuelto.value=false
    }


    fun CargarElFormularioLocal(fg: (f:Formulario) -> Unit, fb: () -> Unit){
        viewModelScope.launch { CRUD.CargarUnFormularioLocal(id_formulario,{
            nombre_formulario=it.nombre!!;
            fg(it)},fb)
        }
    }


     fun CargarTodasPreguntasDelFormulario(formulario: Formulario, fg: (list:List<Pregunta>) -> Unit, fb: () -> Unit){

    viewModelScope.launch { CRUD.CargarTodasPreguntasDelFormularioLocal(formulario, fg, fb) }

    }





     fun BorrarFormulario(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit) {
    viewModelScope.launch { CRUD.BorrarFormulario(str_ObjectId,fg,fb) }
}

    fun DeterminarListadoEnvios(fg: (listUnaRespPorFormu:ArrayList<Respuesta>)-> Unit,fb: () -> Unit) {
        estado.value=Estado.Network
        Log.e("DeterminarListadoEnvios","11")

        val esta_actividad=BIN.getThisAct()
        Log.e("DeterminarListadoEnvios","22")
        viewModelScope.launch { CRUD.CargarUnFormularioLocal(id_formulario,{f ->
            Log.e("DeterminarListadoEnvios","33")
            viewModelScope.launch { CRUD.CargarTodosUsuarioLocal(false,{todosUsYAdm->
                Log.e("DeterminarListadoEnvios","44")
                viewModelScope.launch { CRUD.CargarTodasRespuestasLocal({todasResp->
                    Log.e("DeterminarListadoEnvios","55")
                    Determinar(todosUsYAdm,todasResp,f,esta_actividad,fg);
                    CRUD.CargarTodasRespuestas({t->Determinar(todosUsYAdm,t,f,esta_actividad,fg); },{})//DEL SERVIDOR
                },{CRUD.CargarTodasRespuestas({t->Determinar(todosUsYAdm,t,f,esta_actividad,fg); },{})}) }//DEL SERVIDOR
            },{Log.e("DeterminarListadoEnvios","44 bad")})}
        },{Log.e("DeterminarListadoEnvios","33 bad")})}
















    }

    private fun Determinar(todosUsYAdm: List<Usuario>, todasResp: List<Respuesta>,f:Formulario,esta_actividad:Actividad?,fg: (listUnaRespPorFormu:ArrayList<Respuesta>) -> Unit) {
        Log.e("Determinar","Called")
        val todosUs=PurgarAdmins(todosUsYAdm)
        val retList= ArrayList<Respuesta>(todosUs.size)
        for (u in todosUs.indices){
            var added=false
            Log.e("Indice de Usuario",u.toString())


            resp_loop@for (r in todasResp){
                Log.e("Buscando Respuesta",r.respuesta!!)
                if (r.ref_formulario?.objectId==id_formulario&&r.ref_usuario?.objectId==todosUs[u].objectId&&r.ref_actividad==esta_actividad)
                {
                    retList.add(r)
                    added=true
                    Log.e("Añadida","Usuario:${todosUs[u].nom_apell} RespuestaId:${r.objectId} Descrip:${r.respuesta} Fecha:${r.fecha.toString()}")
                    break@resp_loop
                }
            }
            if (!added){
                Log.e("Añadida","Usuario:${todosUs[u].nom_apell} NULL NULL NULL")
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
        BIN.ESTA_RESUELTO_EL_FORMULARIO_PARA_ESTE_USUARIO(c) { esta_resuelto.value = true }
    }


}
