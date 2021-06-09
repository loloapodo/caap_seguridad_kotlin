package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.ParseObj.Equip
import com.ello.kotlinseguridad.ParseObj.Rol
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class RolAdapter(mContext: Context, val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater= LayoutInflater.from(mContext)
     var list: List<Rol> = mutableListOf()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_rol, parent, false)
        return RolesH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as RolesH
        with(list[position]){

            holder.nombre.text=nombre_rol
            holder.itemView.setOnClickListener { iClick(list[position].objectId) }
        }



    }

    inner class RolesH(view: View) : RecyclerView.ViewHolder(view) {
        var nombre:TextView = view.findViewById(R.id.item_nombre)

    }

}
