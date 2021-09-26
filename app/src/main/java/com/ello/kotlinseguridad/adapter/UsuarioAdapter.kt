package com.ello.kotlinseguridad.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.parseobj.Usuario
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.bin.Snippetk
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class UsuarioAdapter(mContext: Context,val iClick:(usu:Usuario)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater= LayoutInflater.from(mContext)
     var list: List<Usuario> = mutableListOf()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_usuario, parent, false)
        return UsuarioH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as UsuarioH
        with(list[position]){


            holder.nombre.text=usuario
            if (adm!=null&&adm){holder.adm.visibility=View.VISIBLE }
            else{holder.adm.visibility=View.GONE}



            holder.itemView.setOnClickListener { iClick(list[position]) }


            GlobalScope.launch {
                Snippetk.PonerFotoCircular(holder.image,list[position].foto)
            }









        }


























    }

    inner class UsuarioH(view: View) : RecyclerView.ViewHolder(view) {
        var nombre:TextView = view.findViewById(R.id.item_nombre)
        var adm:TextView = view.findViewById(R.id.item_admin)
        var image:CircularImageView=view.findViewById(R.id.item_imageusuario)
    }

}
