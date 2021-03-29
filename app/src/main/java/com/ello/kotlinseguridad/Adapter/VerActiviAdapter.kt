package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.R
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseObject

class VerActiviAdapter(mContext: Context, val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
     var list: ArrayList<String> = ArrayList()


    override fun getItemViewType(position: Int): Int {

        return R.layout.item_ver_usuario;


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


                val view = layoutInflater.inflate(viewType, parent, false)
                return UsuarH(view)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {

        if (holderT is UsuarH){
            val holder = holderT
            holder.usuar.setText(list[position])
        }




    }

    inner class UsuarH(view: View) : RecyclerView.ViewHolder(view) {
        var usuar: TextView = view.findViewById(R.id.item_nombre_usuario)
    }

    fun setLista(listica:List<Usuario>){

        list.clear()
        for (p in listica) {
            if (p.nom_apell == null) {
                list.add("")
            } else {
                list.add(p.nom_apell!!)
            }

        }

    }





}
