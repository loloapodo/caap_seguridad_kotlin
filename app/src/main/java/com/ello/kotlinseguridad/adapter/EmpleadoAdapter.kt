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
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EmpleadoAdapter(mContext: Context, val iClick:(str:Usuario)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater= LayoutInflater.from(mContext)
     var list: List<Usuario> = mutableListOf()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_empleado, parent, false)
        return UsuarioH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as UsuarioH
        with(list[position]){


            holder.nombre.text=nom_apell+" "+apell
            if (rol.isNullOrEmpty() ||rol.equals(BIN.EMPTY_ROL)){holder.adm.visibility=View.GONE}
            else{holder.adm.visibility=View.VISIBLE;holder.adm.text=rol}


            holder.itemView.setOnClickListener { iClick(list[position]) }


            GlobalScope.launch {
                Snippetk.PonerFotoCircular(holder.image,list[position].foto,"empleado adapter")
            }
        }











    }

    inner class UsuarioH(view: View) : RecyclerView.ViewHolder(view) {
        var nombre:TextView = view.findViewById(R.id.item_nombre)
        var adm:TextView = view.findViewById(R.id.item_admin)
        var image:CircularImageView=view.findViewById(R.id.item_imageusuario)
    }

}
