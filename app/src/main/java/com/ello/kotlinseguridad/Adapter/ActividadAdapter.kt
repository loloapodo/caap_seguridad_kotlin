package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.R
import java.util.*

class ActividadAdapter(mContext: Context,val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
     var list: List<Actividad> = mutableListOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_actividad, parent, false)
        return ActividadH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as ActividadH
        holder.nombre.text=list[position].nombre
       var fecha= list[position].LeerFechaR()
       var hoy=Snippetk.LeerFechaR(Calendar.getInstance().timeInMillis)

        if (hoy.isNotEmpty() && hoy == fecha){holder.fecha.text="Hoy"}
        else { holder.fecha.text=fecha }



        holder.itemView.setOnClickListener{iClick(list[position].objectId)}



    }

    inner class ActividadH(view: View) : RecyclerView.ViewHolder(view) {
        var nombre:TextView = view.findViewById(R.id.item_nombre)
        var fecha:TextView = view.findViewById(R.id.item_fecha_activ)
    }

}
