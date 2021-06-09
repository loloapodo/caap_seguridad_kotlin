package com.ello.kotlinseguridad.Simple

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import kotlinx.coroutines.launch


class SFormVM() : ViewModel() {

    lateinit var  nombre_formulario:String
    lateinit var id_formulario:String
    lateinit var id_usuario:String

    var estado = MutableLiveData<Estado>()

    init {
        estado.value= Estado.Idle
    }


    fun CargarElFormularioLocal(fg: (f:Formulario) -> Unit, fb: () -> Unit){
        viewModelScope.launch { CRUD.CargarUnFormularioLocal(id_formulario,{
            nombre_formulario=it.nombre!!;
            fg(it)},fb)
        }
    }


     fun CargarTodasPreguntasDelFormulario(formulario: Formulario, fg: (list:List<Pregunta>) -> Unit, fb: () -> Unit){

    viewModelScope.launch { CRUD.CargarTodasPreguntasDelFormulario(formulario, fg, fb) }
    }





     fun BorrarFormulario(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit) {
    viewModelScope.launch { CRUD.BorrarFormulario(str_ObjectId,fg,fb) }
}

    fun DeterminarListadoEnvios(fg: (listUnaRespPorFormu:ArrayList<Respuesta>) -> Unit,fb: () -> Unit) {
        estado.value=Estado.Network

        viewModelScope.launch { CRUD.CargarUnFormularioLocal(id_formulario,{f ->

            viewModelScope.launch { CRUD.CargarTodosUsuarioLocal({todosUsYAdm->
                viewModelScope.launch { CRUD.CargarTodasRespuestas({todasResp->



                    val todosUs=PurgarAdmins(todosUsYAdm)
                    val retList= ArrayList<Respuesta>(todosUs.size)
                    for (u in todosUs.indices){
                        var added=false
                        Log.e("Indice de Usuario",u.toString())


                        resp_loop@for (r in todasResp){
                            Log.e("Buscando Respuesta",r.respuesta!!)
                            if (r.ref_formulario?.objectId==id_formulario&&r.ref_usuario?.objectId==todosUs[u].objectId)
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
                            emptyRes.fecha=0L
                            retList.add(emptyRes);
                        }
                    }

                    fg(retList);
                    estado.value=Estado.Idle
                },{}) }
            },{})}
        },{})}
















    }

    private fun PurgarAdmins(todos: List<Usuario>): ArrayList<Usuario> {

        val retList=ArrayList<Usuario>()
        for (u in todos){
            if (!u.adm){retList.add(u)}
            else       {Log.e("Admin Excluido",u.nom_apell!!)}
        }
        return retList
    }





}
