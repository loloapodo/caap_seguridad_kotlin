package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.IVerEstadoDelFormBinding
import com.ello.twelveseconds.Formulario
import java.util.*
import kotlin.collections.ArrayList

class VerEstDelFormAdapter(mContext: Context, val iClick:(usuario: Usuario,f: Formulario)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
     var list: ArrayList<Respuesta> = ArrayList()


    override fun getItemViewType(position: Int): Int {
        return R.layout.i_ver_estado_del_form;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val mBind = IVerEstadoDelFormBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EstadoH(mBind)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {

        if (holderT is EstadoH){
            val holder = holderT

            Log.e("VerEstDelFormAdapter",position.toString())





            with(list[position]){

               val u= ref_usuario as Usuario
               val f= ref_formulario as Formulario
                holder.Nombre.text=u.nom_apell


                if (fecha==null||fecha==0L){

                    if (EstaATiempo(f)){holder.EstadoEnvio.text= BIN.STR_POR_LLENAR}
                    else               {holder.EstadoEnvio.text= BIN.STR_EXPIRADO}
                    holder.Fecha_Entrega.text=""
                    holder.HoraEntrega.text=""
                    holder.itemView.setOnClickListener {}

                }
                else{


                    holder.Fecha_Entrega.text=Snippetk.LeerFechaR(fecha)
                    holder.HoraEntrega.text=Snippetk.LeerHoraR(fecha)
                    holder.itemView.setOnClickListener {
                        iClick(u,f)
                    }



                    if (FueATiempo(f, fecha!!)){holder.EstadoEnvio.text= BIN.STR_ENVIADO}
                    else               {holder.EstadoEnvio.text= BIN.STR_EXPIRADO}

                }
            }
        }
    }

    private fun FueATiempo(f: Formulario, fecha: Long): Boolean {

        val form = f.fecha_limite!!
        if (fecha<form){return true}
        return false
    }


    private fun EstaATiempo(f: Formulario): Boolean {

       val now= Calendar.getInstance().timeInMillis
       val form = f.fecha_limite!!

        if (now>form){return false}
        return true



    }

    fun setRespuestas(listica:ArrayList<Respuesta>){
        list.clear()
        list.addAll(listica)
    }

    inner class EstadoH(view: IVerEstadoDelFormBinding) : RecyclerView.ViewHolder(view.root) {
        val Nombre=view.itemNombreUsuario
        val Fecha_Entrega=view.itemFecha
        val EstadoEnvio=view.itemEstado
        val HoraEntrega=view.itemHora

    }





}
