package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.BIN.Snippetk
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EmpleadoAdapter(mContext: Context, val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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


            holder.nombre.text=nom_apell
            if (rol.isNullOrEmpty() ||rol.equals(BIN.EMPTY_ROL)){holder.adm.visibility=View.GONE}
            else{holder.adm.text=rol}


            holder.itemView.setOnClickListener { iClick(list[position].objectId) }


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
