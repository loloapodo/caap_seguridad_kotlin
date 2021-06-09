package com.ello.kotlinseguridad.BIN

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.ello.kotlinseguridad.ParseObj.*
import com.ello.twelveseconds.Formulario
import com.parse.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CRUD()  {

    init {

    }

companion object{
    suspend fun CrearUsuario(str_usuario: String, str_contrasena: String, str_nom_apell: String,str_cedula: String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit)
    {


        fun innerSave(u:Usuario,fg: () -> kotlin.Unit,fb: () -> Unit){
            u.saveInBackground { e2 ->
                if (e2 == null) {
                    fg();Log.e("Modificado en serv","guardado usuario con imagen")

                } else {
                    fb();Log.e("Error","modificado usuario con imagen")
                }
            }
        }

        val u=Usuario()

        u.usuario=str_usuario
        u.contrasena=str_contrasena
        u.nom_apell=str_nom_apell
        u.adm=false
        u.cedula=str_cedula


        if (foto != null) {
            val file= Snippetk.BitmapToParseFile(foto);
            file.saveInBackground(SaveCallback { e ->
                if (e==null){

                    u.foto=file
                    innerSave(u,fg,fb)


                }else{
                    fb()
                    Log.e("Error",e.message)
                    Log.e("Error","33")
                }
            })
        }
        else{
            innerSave(u,fg,fb)
        }

    }

     fun EditarUsuario(str_ObjectId: String,str_usuario: String, str_contrasena: String, str_nom_apell: String,str_cedula:String, foto: Bitmap?,fg: () -> Unit,fb: () -> Unit)
    {

        fun innerSave(u:Usuario,fg: () -> kotlin.Unit,fb: () -> Unit){
            u.saveInBackground { e2 ->
                if (e2 == null) {
                    fg();Log.e("Modificado en serv","guardado usuario con imagen")

                } else {
                    fb();Log.e("Error","modificado usuario con imagen")
                }
            }
        }


        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.getInBackground(str_ObjectId, GetCallback { u, e ->

            if (e == null) {
                // Update the fields we want to
                u.usuario=str_usuario
                u.cedula=str_cedula
                u.contrasena=str_contrasena
                u.nom_apell=str_nom_apell
                u.adm=false


                if (foto != null) {
                   val file= Snippetk.BitmapToParseFile(foto);
                    file.saveInBackground(SaveCallback { e2 ->
                        if (e2==null){
                            u.foto=file
                            innerSave(u,fg, fb)

                        }else{
                            fb()
                            Log.e("Error",e2.message)
                            Log.e("Error","33")
                        }
                    })
                }
                else{
                    innerSave(u,fg, fb)
                }


            } else {
                fb()

            }
        })



}



    suspend fun CargarTodosUsuario(fg: (list:List<Usuario>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.orderByAscending(Usuario.field_nom)
       query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list);ParseObject.pinAll(list) }
            else
            { fb();Log.e("Error","Buscar All Users") }
        })

    }

    suspend fun CargarTodosUsuarioLocal(fg: (list:List<Usuario>) -> Unit,fb: () -> Unit) {
        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.orderByAscending(Usuario.field_nom)
        query.fromLocalDatastore()
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Users local") }
        })

    }

    suspend fun CargarUnUsuario(str_ObjectId: String,fg: (u:Usuario) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.getInBackground(str_ObjectId, GetCallback { objeto, e ->
            if (e==null){fg(objeto) }
            else { fb();Log.e("Error","Buscar un usuario ${e.message}"); }
        })

    }
    suspend fun CargarUnUsuarioLocal(str_ObjectId: String,fg: (u:Usuario) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.fromLocalDatastore()
        query.getInBackground(str_ObjectId, GetCallback { objeto, e ->
            if (e==null){fg(objeto) }
            else { fb();Log.e("Error","Buscar un usuario") }
        })

    }

     fun CargarUnActividad(act_ObjectId: String,fg: (a:Actividad) -> Unit,fb: () -> Unit){


            val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
            query.getInBackground(act_ObjectId, GetCallback { objeto, e ->
                if (e==null){fg(objeto) }
                else { fb();Log.e("Error","Buscar un usuario") }
            })



    }
    fun CargarUnActividadLocal(act_ObjectId: String,fg: (a:Actividad) -> Unit,fb: () -> Unit){


            val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
            query.fromLocalDatastore()
            query.getInBackground(act_ObjectId, GetCallback { objeto, e ->
                if (e==null){fg(objeto) }
                else { fb();Log.e("Error","Buscar un usuario") }
            })



    }







    suspend fun BorrarUsuario(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit)
    {

        val query = ParseQuery.getQuery<Usuario>(Usuario.class_name)
        query.getInBackground(str_ObjectId, GetCallback { u, e ->

            if (e == null) {
                    u.deleteInBackground() { e2 ->
                        if (e2 == null) {
                            fg();Log.e("Borrado del serv","Borrado del Serv")
                        } else {
                            Log.e("Error","Al borrar del servidior")
                            fb();
                        }
                    }
            } else {
                fb()
                Log.e("Error borrar","Al buscar en el servidior")
            }
        })

    }


    /**Metodo existe en otra clase
     *  Crea el formulario
    2 llamadas a la api
     */
    suspend fun CrearFormulario(str_nombre: String, long_fecha: Long,long_fecha_limite:Long,listPreg: List<String> ,fg: () -> Unit,fb: () -> Unit) {

        val f = Formulario()
        val pregs= mutableListOf<Pregunta>()

        f.nombre = str_nombre
        f.fecha = long_fecha
        f.fecha_limite = long_fecha_limite


        f.saveInBackground { e ->
            if (e == null) {
                Log.e("Guardado en serv", "Formulario")

                for (i in listPreg.indices){
                    val temp=Pregunta()
                    temp.nombre=listPreg[i]
                    temp.numero=i
                    temp.ref_formulario=f
                    pregs.add(temp)
                }


                ParseObject.saveAllInBackground(pregs, SaveCallback {e2->
                    if (e2 == null) {
                        Log.e("Guardado en serv", "Preguntas dentro del formulario")
                        fg();
                    }
                    else{
                        Log.e("Error", e2.message)
                        e2.printStackTrace()
                        Log.e("Error", "guardando preguntas dentro de folmulario ")
                        fb();
                    }
                })
            } else {
                Log.e("Error", e.message)
                Log.e("Error", "guardando Formulario ")
                fb();
            }
        }
    }


    /**Metodo existe en otra clase
     *  Edita el formulario Ineficiente por completo!
     * Mientras sea ineficiente se sugiere borrar formulario por accion del usuario. Luego crear otro por el usuario!
    (en verdad lo borra el formulario, borra las preguntas luego y llama a la funcion CrearFormulario)
     4 llamadas a la Api
     2 para llamar
     2 para borrar los objetos encontrados
     2 mas para crear el nuevo
     */
    suspend fun EditarFormulario(str_ObjectId: String,str_nombre: String, str_fecha: Long,str_fecha_limite:Long,listPreg: List<String> ,fg: () -> Unit,fb: () -> Unit) {
        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.getInBackground(str_ObjectId, GetCallback { toDeleteFormulario, e1 ->
            if (e1==null){
              toDeleteFormulario.deleteInBackground {e2->
                  if (e2==null)
                  {
                      val query2 = ParseQuery.getQuery<Formulario>(Pregunta.class_name);
                      query2.whereEqualTo(Pregunta.field_ref_formulario,str_ObjectId)
                      query2.findInBackground { toDeletePregunts, e3 ->
                            if (e3==null){
                                ParseObject.deleteAllInBackground(toDeletePregunts, DeleteCallback {e4->
                                    if (e4==null){
                                        GlobalScope.launch { CrearFormulario(str_nombre, str_fecha, str_fecha_limite, listPreg, fg, fb) }
                                    }else {Log.e("Error","EF2224");fb()}
                                })
                            }else {Log.e("Error","EF2223");fb()}
                      }
                  }else {Log.e("Error","EF2222");fb()}
              }
            }else {Log.e("Error","EF2221");fb()}
            })
        }

    suspend fun CargarTodosFormularios(fg: (list:List<Formulario>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.orderByDescending(Formulario.field_created)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list);ParseObject.pinAll(list)}
            else
            { fb();Log.e("Error","Buscar All Formularios") }
        })
    }

    suspend fun CargarTodosFormulariosLocal(fg: (list:List<Formulario>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.orderByDescending(Formulario.field_created)
        query.fromLocalDatastore()
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list);}
            else
            { fb();Log.e("Error","Buscar All Formularios") }
        })
    }
















    suspend fun CargarTodasPreguntasDelFormulario(formulario: Formulario,fg: (list:List<Pregunta>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Pregunta>(Pregunta.class_name)
        query.orderByAscending(Pregunta.field_numero)
        query.whereEqualTo(Pregunta.field_ref_formulario,formulario)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list)}
            else
            { fb();Log.e("Error","Buscar All Preguntas") }
        })
    }



    /**
     * Borra el formulario, tambien las preguntas que pertencen a este
     */
    suspend fun BorrarFormulario(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit) {

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.getInBackground(str_ObjectId, GetCallback { toDeleteFormulario, e1 ->
            if (e1==null){
                toDeleteFormulario.deleteInBackground {e2->
                    if (e2==null)
                    {
                        Log.e("Borrado","Formulario")
                        val query2 = ParseQuery.getQuery<Formulario>(Pregunta.class_name);
                        query2.whereEqualTo(Pregunta.field_ref_formulario,str_ObjectId)
                        query2.findInBackground { toDeletePregunts, e3 ->
                            if (e3==null){
                                ParseObject.deleteAllInBackground(toDeletePregunts, DeleteCallback {e4->
                                    if (e4==null){
                                     Log.e("Borrado","preguntas");fg();
                                    }else {Log.e("Error","BF2224");fb()}
                                })
                            }else {Log.e("Error","BF2223");fb()}
                        }
                    }else {Log.e("Error","BF2222");fb()}
                }
            }else {Log.e("Error","BF2221");fb()}
        })
    }



    suspend fun CrearActividad(usuarios:List<Usuario> , str_nombre: String,str_desc: String,long_fecha:Long ,fg: () -> Unit,fb: () -> Unit) {

        val a= Actividad()

        a.addUsuarios(usuarios)
        a.nombre=str_nombre
        a.desc=str_desc
        a.fecha = long_fecha

        a.saveInBackground { e ->
            if (e == null) {
                fg();Log.e("Guardado en serv", "guardada actividad")
            } else {
                Log.e("Error", "guardando actividad ")
                e.printStackTrace()
                Log.e("Error", e.message)
                fb();
            }
        }
    }


        suspend fun EditarActividad(str_ObjectId: String,usuarios:List<Usuario> , str_nombre: String,str_desc: String,str_fecha:Long ,fg: () -> Unit,fb: () -> Unit){
            BorrarActividad(str_ObjectId, { GlobalScope.launch { CrearActividad(usuarios, str_nombre, str_desc, str_fecha, fg, fb) } }, {})
        }



    suspend fun CargarTodasActividades(fg: (list:List<Actividad>) -> Unit,fb: () -> Unit){
        val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        query.orderByDescending(Actividad.field_fecha)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list);ParseObject.pinAll(list)}
            else
            { fb();Log.e("Error","Buscar All Actividades") }
        })
    }

    suspend fun CargarTodasActividadesLocal(fg: (list:List<Actividad>) -> Unit,fb: () -> Unit){
        val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        query.orderByDescending(Actividad.field_fecha)
        query.fromLocalDatastore()
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list);}
            else
            { fb();Log.e("Error","Buscar All Actividades") }
        })
    }






    suspend fun CargarTodosUsuariosdeActividad(act: Actividad,fg: (list:List<Usuario>) -> Unit,fb: () -> Unit){

        val query = act.getRelation<Usuario>(Actividad.field_rel_usuarios).query
        query.orderByAscending(Usuario.field_nom)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Usuarios de actividad") }
        })
    }

    suspend fun CargarTodosUsuariosdeActividad(act_ObjectId: String,fg: (param:Actividad,list:List<Usuario>) -> Unit,fb: () -> Unit){

        CargarUnActividad(act_ObjectId, { act ->
            val query = act.getRelation<Usuario>(Actividad.field_rel_usuarios).query
            query.orderByAscending(Usuario.field_nom)
            query.findInBackground(FindCallback { list, e ->
                if (e == null) {
                    fg(act, list)
                } else {
                    fb();Log.e("Error", "Buscar All Usuarios de actividad")
                }
            })

        }, { fb();Log.e("Error", "Cargar una actividad") })



    }


    suspend fun CargarTodasActividadesDelUsuario(U: Usuario,time_mls: Long,fg: (list:List<Actividad>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        query.orderByDescending(Actividad.field_fecha)
        query.whereEqualTo(Actividad.field_rel_usuarios,U)
        query.whereGreaterThanOrEqualTo(Actividad.field_fecha,time_mls)
        query.findInBackground { list, e ->
            if (e==null){
                ParseObject.unpinAll(BIN.PIN_ACTIVIDADES_USUARIO,list)
                ParseObject.fetchAllIfNeeded(list)
                ParseObject.pinAll(BIN.PIN_ACTIVIDADES_USUARIO,list);Log.e("Tamano lista",list.size.toString());fg(list) } else { fb();Log.e("Error","Buscar All Actividades") }
        }
    }

    suspend fun CargarTodasActividadesDelUsuarioLocal(U: Usuario,time_mls:Long,fg: (list:List<Actividad>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
        query.fromPin(BIN.PIN_ACTIVIDADES_USUARIO)
        query.findInBackground { list, e ->
            if (e==null){Log.e("Tamano lista local",list.size.toString());fg(list) } else { fb();Log.e("Error","Buscar All Actividades") }
        }
    }

    suspend fun CargarTodasActividadesDelUsuario(str_ObjectId: String,time_mls:Long,fg: (list:List<Actividad>) -> Unit,fb: () -> Unit){
        CargarUnUsuario(str_ObjectId,
                { esteusuario -> GlobalScope.launch { CargarTodasActividadesDelUsuario(esteusuario,time_mls, fg, fb) } },
                fb);

    }






        suspend fun BorrarActividad(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){
            val query = ParseQuery.getQuery<Actividad>(Actividad.class_name)
            query.getInBackground(str_ObjectId, GetCallback { act, e ->
                if (e==null)     {
                    act.deleteInBackground {e2->
                        if(e2==null){ Log.e("Done","Actividad borrada");fg() }
                        else{Log.e("Error","BorrarActividad");fb()}
                    }
                } else{Log.e("Error","Encontrar Actividad");fb()}
            })
        }


    suspend fun RegistrarRespuesta(U: Usuario,F:Formulario,P:Pregunta,R:Respuesta,foto: Bitmap?,fg: () -> Unit,fb: () -> Unit) {

        R.ref_pregunta=P
        R.ref_formulario=F
        R.ref_usuario=U




        fun innerSave(R:Respuesta,fg: () -> kotlin.Unit,fb: () -> Unit){
            R.saveInBackground { e2 ->
                if (e2 == null) {
                    fg();Log.e(" "," registrada respuesta")

                } else {
                    fb();
                    Log.e("Error","No registrada respuesta")
                    Log.e("Error",e2.message)
                    e2.printStackTrace()
                }
            }
        }



        if (foto!=null){
            val file= Snippetk.BitmapToParseFile(foto);
            file.saveInBackground(SaveCallback { e ->
                if (e==null){

                    R.foto=file
                    innerSave(R,fg,fb)


                }else{
                    fb()
                    Log.e("Error",e.message)
                    Log.e("Error","63")
                }
            })
        }
        else
        {
            innerSave(R,fg,fb)
        }
    }











   fun CargarTodosFormulariosRespondidos(u:Usuario,fg: (list:List<Formulario>) -> Unit,fb: () -> Unit){
       CargarTodasRespuestasDeUnUsuario(u, { respuestasDelUsuario ->
           val formRespondidos = mutableListOf<Formulario>()
           for (r in respuestasDelUsuario) {
               if (!formRespondidos.contains(r.ref_formulario)) {
                   formRespondidos.add(r.ref_formulario!!.fetchIfNeeded())
               }
           }
           ParseObject.unpinAll(BIN.PIN_FORMULARIO_RESPONDIDOS)
           ParseObject.pinAll(BIN.PIN_FORMULARIO_RESPONDIDOS,formRespondidos)
           fg(formRespondidos)

       }, fb)
   }

    fun CargarTodosFormulariosRespondidosLocal(u:Usuario,fg: (list:List<Formulario>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.fromPin(BIN.PIN_FORMULARIO_RESPONDIDOS)
        try {
            fg(query.find())
        }
        catch (e:ParseException){
            e.printStackTrace()
        }



    }















    fun CargarTodosFormulariosNoRespondidos(u:Usuario,fg: (list:List<Formulario>) -> Unit,fb: () -> Unit){

        CargarTodosFormulariosRespondidos(u,{respondidos->
            GlobalScope.launch (Dispatchers.IO){
                CargarTodosFormularios({todos->
                    val sinResponder= BIN.RESTAR_LISTAS(todos,respondidos)
                    ParseObject.unpinAll(BIN.PIN_FORMULARIO_RESPONDIDOS)
                    ParseObject.pinAll(BIN.PIN_FORMULARIO_SIN_RESPONDER,sinResponder)
                    fg(sinResponder)
                }, fb)
            }


        },fb)
    }
    fun CargarTodosFormulariosNoRespondidosLocal(u:Usuario,fg: (list:List<Formulario>) -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.fromPin(BIN.PIN_FORMULARIO_SIN_RESPONDER)
        try {
            fg(query.find())
        }catch (e:ParseException){
            e.printStackTrace()
        }


    }


     fun CargarTodasRespuestasDeUnUsuario(u: Usuario,fg: (list: List<Respuesta>) -> Unit,fb: () -> Unit)
    {
        val query = ParseQuery.getQuery<Respuesta>(Respuesta.class_name)
        query.whereEqualTo(Respuesta.field_ref_usuario,u)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Respuestas") }
        })
    }
    fun CargarTodasRespuestas(fg: (list: List<Respuesta>) -> Unit,fb: () -> Unit)
   {
       val query = ParseQuery.getQuery<Respuesta>(Respuesta.class_name)
       query.findInBackground(FindCallback { list, e ->
           if (e==null){fg(list) }
           else
           { fb();Log.e("Error","Buscar All Respuestas") }
       })
   }
    fun CargarTodasRespuestas(u: Usuario,fg: (list: List<Respuesta>) -> Unit,fb: () -> Unit)
    {
        val query = ParseQuery.getQuery<Respuesta>(Respuesta.class_name)
        query.whereEqualTo(Respuesta.field_ref_usuario,u)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Respuestas2") }
        })
    }
    fun CargarTodasRespuestas(u: Usuario,f:Formulario,fg: (list: List<Respuesta>) -> Unit,fb: () -> Unit)
    {
        val query = ParseQuery.getQuery<Respuesta>(Respuesta.class_name)
        query.whereEqualTo(Respuesta.field_ref_usuario,u)
        query.whereEqualTo(Respuesta.field_ref_formulario,f)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Respuestas3") }
        })
    }
































    fun CargarTodasRespuestas(p: Pregunta,u:Usuario,fg: (list: List<Respuesta>) -> Unit,fb: () -> Unit)
    {
        val query = ParseQuery.getQuery<Respuesta>(Respuesta.class_name)
        query.whereEqualTo(Respuesta.field_ref_usuario,u)
        query.whereEqualTo(Respuesta.field_ref_pregunta,p)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Respuestas3") }
        })
    }

    fun PonerFoto(
            foto: Bitmap?,
            imageView: ImageView

    ) {

        if (foto != null) {
            imageView.setImageBitmap(foto)

        }
    }

    fun CargarUnFormulario(strObjid: String,fg: (f:Formulario) -> Unit,fb: () -> Unit) {

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name)
        query.getInBackground(strObjid, GetCallback { objeto, e ->
            if (e==null){fg(objeto) }
            else { fb();Log.e("Error","Buscar un usuario") }
        })
    }
    fun CargarUnFormularioLocal(strObjid: String,fg: (f:Formulario) -> Unit,fb: () -> Unit) {

        val query = ParseQuery.getQuery<Formulario>(Formulario.class_name).fromLocalDatastore()
        query.getInBackground(strObjid, GetCallback { objeto, e ->
            if (e==null){fg(objeto) }
            else { fb();Log.e("Error","Buscar un usuario") }
        })
    }

    fun CargarTodosRoles(fg: (r:List<Rol>) -> Unit,fb : () -> Unit) {
        val query = ParseQuery.getQuery<Rol>(Rol.class_name)
        query.findInBackground { list_roles, e ->
            if (e==null){fg(list_roles) }
            else { fb();Log.e("Error","Buscar roles") }
        }
    }

    fun CargarTodasEquipamiento(fg: (list: List<Equip>) -> Unit, fb: () -> Unit) {

        val query = ParseQuery.getQuery<Equip>(Equip.class_name)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Equipamiento") }
        })

    }
    fun CargarTodasRol(fg: (list: List<Rol>) -> Unit, fb: () -> Unit) {

        val query = ParseQuery.getQuery<Rol>(Rol.class_name)
        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Equipamiento") }
        })

    }

    fun CargarUnEquipamiento(idEquipa: String, fg: (activ: Equip) -> Unit, fb: () -> Unit) {

        val query = ParseQuery.getQuery<Equip>(Equip.class_name)
        query.getInBackground(idEquipa, GetCallback { objeto, e ->
            if (e==null){fg(objeto) }
            else { fb();Log.e("Error","Buscar un equipamiento") }
        })
    }
    fun CargarUnRol(idEquipa: String, fg: (activ: Rol) -> Unit, fb: () -> Unit) {

        val query = ParseQuery.getQuery<Rol>(Rol.class_name)
        query.getInBackground(idEquipa, GetCallback { objeto, e ->
            if (e==null){fg(objeto) }
            else { fb();Log.e("Error","Buscar un rol") }
        })

    }

    suspend fun BorrarEquipamiento(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Equip>(Equip.class_name)
        query.getInBackground(str_ObjectId, GetCallback { u, e ->

            if (e == null) {
                u.deleteInBackground() { e2 ->
                    if (e2 == null) {
                        fg();Log.e("Borrado del serv","Borrado del Serv")
                    } else {
                        Log.e("Error","Al borrar del servidior")
                        fb();
                    }
                }
            } else {
                fb()
                Log.e("Error borrar","Al buscar en el servidior")
            }
        })

    }

    suspend fun BorrarRol(str_ObjectId: String,fg: () -> Unit,fb: () -> Unit){

        val query = ParseQuery.getQuery<Rol>(Rol.class_name)
        query.getInBackground(str_ObjectId, GetCallback { u, e ->

            if (e == null) {
                u.deleteInBackground() { e2 ->
                    if (e2 == null) {
                        fg();Log.e("Borrado del serv","Borrado del Serv")
                    } else {
                        Log.e("Error","Al borrar del servidior")
                        fb();
                    }
                }
            } else {
                fb()
                Log.e("Error borrar","Al buscar en el servidior")
            }
        })

    }


    fun CrearEquipamiento(nombre: String,str_uso: String,str_desc: String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit) {

        fun innerSave(u:Equip,fg: () -> kotlin.Unit,fb: () -> Unit){
            u.saveInBackground { e2 ->
                if (e2 == null) {
                    fg();Log.e("Modificado en serv","guardado equip con imagen")

                } else {
                    fb();Log.e("Error","modificado equip con imagen")
                }
            }
        }

        val u=Equip()
        u.nombre=nombre
        u.descripcion=str_desc
        u.uso=str_uso

        if (foto != null) {
            val file= Snippetk.BitmapToParseFile(foto);
            file.saveInBackground(SaveCallback { e ->
                if (e==null){

                    u.foto=file
                    innerSave(u,fg,fb)


                }else{
                    fb()
                    Log.e("Error",e.message)
                    Log.e("Error","33w")
                }
            })
        }
        else{
            innerSave(u,fg,fb)
        }

    }


    fun CrearRol(nombre: String, fg: () -> Unit, fb: () -> Unit) {

        val u=Rol()
        u.nombre_rol=nombre

            u.saveInBackground { e2 ->
                if (e2 == null) { fg(); } else { fb(); }
            }




    }


    fun EditarEquipamiento(str_ObjectId: String, nombre: String,str_uso: String,str_desc: String, foto: Bitmap?, fg: () -> Unit, fb: () -> Unit)
    {
        Log.e("Pasos","22")
        fun innerSave(u:Equip,fg: () -> kotlin.Unit,fb: () -> Unit){
            u.saveInBackground { e2 ->
                if (e2 == null) {
                    fg();Log.e("Modificado en serv","guardado equip con imagen")

                } else {
                    fb();Log.e("Error","modificado equip con imagen")
                }
            }
        }

        Log.e("Pasos","23")
        val query = ParseQuery.getQuery<Equip>(Equip.class_name)
        query.getInBackground(str_ObjectId, GetCallback { u, e ->
            Log.e("Pasos","23")
            if (e == null) {
                Log.e("Pasos","24")
                // Update the fields we want to
                u.nombre=nombre
                u.descripcion=str_desc
                u.uso=str_uso
                if (foto != null) {
                    Log.e("Pasos","25")
                    val file= Snippetk.BitmapToParseFile(foto);
                    file.saveInBackground(SaveCallback { e2 ->
                        if (e2==null){
                            u.foto=file
                            innerSave(u,fg, fb)

                        }else{
                            fb()
                            Log.e("Error",e2.message)
                            Log.e("Error","33")
                        }
                    })
                }
                else{
                    innerSave(u,fg, fb)
                }


            } else {
                Log.e("Pasos","26")
                fb()

            }
        })
    }
    fun EditarRol(str_ObjectId: String,nombre:String,fg: () -> Unit,fb: () -> Unit)
    {

        val query = ParseQuery.getQuery<Rol>(Rol.class_name)
        query.getInBackground(str_ObjectId, GetCallback { u, e ->
            Log.e("Pasos","23")
            if (e == null) {
                Log.e("Pasos","24")
                // Update the fields we want to
                u.nombre_rol=nombre

                u.saveInBackground { e2 ->
                    if (e2 == null) {
                        fg();

                    } else {
                        fb();
                    }
                }


            } else {
                fb()
            }
        })




    }

    fun CargarTodasEquipamientoLocal(fg: (list: List<Equip>) -> Unit, fb: () -> Unit){
        val query = ParseQuery.getQuery<Equip>(Equip.class_name).fromLocalDatastore()

        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Equipamiento") }
        })
    }

    fun CargarTodasRolLocal(fg: (list: List<Rol>) -> Unit, fb: () -> Unit) {
        val query = ParseQuery.getQuery<Rol>(Rol.class_name).fromLocalDatastore()

        query.findInBackground(FindCallback { list, e ->
            if (e==null){fg(list) }
            else
            { fb();Log.e("Error","Buscar All Equipamiento") }
        })


    }



}


}











