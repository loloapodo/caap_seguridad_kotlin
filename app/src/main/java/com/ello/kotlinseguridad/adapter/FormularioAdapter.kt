package com.ello.kotlinseguridad.adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.R
import com.ello.twelveseconds.Formulario

class FormularioAdapter(mContext: Context, var iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater= LayoutInflater.from(mContext)
     var list: List<Formulario> = mutableListOf()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_form, parent, false)
        return FormularioH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as FormularioH


        holder.nombre.text=list[position].nombre
        holder.itemView.setOnClickListener{iClick(list[position].objectId)}


    }

    inner class FormularioH(view: View) : RecyclerView.ViewHolder(view) {
        var nombre:TextView = view.findViewById(R.id.item_nombre)
    }

}
