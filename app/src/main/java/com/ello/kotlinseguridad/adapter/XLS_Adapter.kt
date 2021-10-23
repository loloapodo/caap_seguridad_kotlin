package com.ello.kotlinseguridad.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.databinding.IXlsBinding
import com.ello.kotlinseguridad.parseobj.Actividad
import com.ello.kotlinseguridad.parseobj.Respuesta
import com.ello.kotlinseguridad.parseobj.Usuario


class XLS_Adapter(mContext: Context,private val AbrirPlantilla: (respuesta_Id: String) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    var listTrueRespuestas:     ArrayList<Respuesta> = ArrayList()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBind = IXlsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return XLS_H(mBind)
    }

    override fun getItemCount(): Int {
        return listTrueRespuestas.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as XLS_H
        Log.e("onBindViewHolder","EvidenciaAdapter $position")
        with(listTrueRespuestas[position]){


              val a= ref_actividad?.fetch<Actividad>()
            if(a!=null){holder.nombre_act.text=a.nombre}

            val u= ref_usuario?.fetch<Usuario>()
            if(u!=null){holder.nombre_usu.text=u.nom_apell}

            holder.fecha_resp.text=Snippetk.LeerFechaR(fecha)

            holder.itemView.setOnClickListener {
                AbrirPlantilla(this.objectId)

            }



        }





    }





    fun addResp(resp: List<Respuesta>) {
        listTrueRespuestas.addAll(resp.filter {respuesta->
            respuesta.firs_of_list })



    }


    fun clearResp() {
        listTrueRespuestas.clear()

    }

    fun get_Respuestas(): ArrayList<Respuesta> {
        return listTrueRespuestas;
    }


    inner class XLS_H(mBind: IXlsBinding) : RecyclerView.ViewHolder(mBind.root) {


        val nombre_act=mBind.itemNombreActRespuesta
        val fecha_resp=mBind.itemFechaRespuesta
        val nombre_usu=mBind.itemNombreUsuarioRespuesta


        //val imageDel=mBind.imageDelete


    }

}
