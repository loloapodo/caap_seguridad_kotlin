package com.ello.kotlinseguridad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.parseobj.Usuario
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.parseobj.Incidente
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class IncidAdapter(mContext: Context, val iClick:(str:Incidente)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater= LayoutInflater.from(mContext)
     var list: List<Incidente> = mutableListOf()






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_incidente, parent, false)
        return IncidenteH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as IncidenteH
        with(list[position]){


            holder.itemView.setOnClickListener { iClick(list[position]) }
            if(name!=null){holder.nombre.text=desc}
            if(desc!=null){holder.desc.text=desc}




        }











    }

    inner class IncidenteH(view: View) : RecyclerView.ViewHolder(view) {
        var nombre:TextView = view.findViewById(R.id.item_nombre_incidente)
       var desc:TextView = view.findViewById(R.id.item_desc_incidente)
     //   var image:CircularImageView=view.findViewById(R.id.item_imageusuario)
    }

}
