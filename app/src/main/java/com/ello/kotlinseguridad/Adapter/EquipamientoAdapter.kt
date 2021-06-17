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
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EquipamientoAdapter(mContext: Context, val iClick:(equ:Equip,str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater= LayoutInflater.from(mContext)
     var list: List<Equip> = mutableListOf()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_usuario, parent, false)
        return EquipamientoH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as EquipamientoH
        with(list[position]){

            holder.nombre.text=nombre
            holder.itemView.setOnClickListener {


                iClick(list[position],list[position].objectId) }


            GlobalScope.launch {
                Snippetk.PonerFotoCircular(holder.image,list[position].foto)
            }
        }



    }

    inner class EquipamientoH(view: View) : RecyclerView.ViewHolder(view) {
        var nombre:TextView = view.findViewById(R.id.item_nombre)
        var image:CircularImageView=view.findViewById(R.id.item_imageusuario)
    }

}
