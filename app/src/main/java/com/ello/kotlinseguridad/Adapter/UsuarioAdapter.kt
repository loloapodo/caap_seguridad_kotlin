package com.ello.kotlinseguridad.Adapter

import android.R.attr.bitmap
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.Snippetk
import com.mikhaellopez.circularimageview.CircularImageView
import com.parse.ParseFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class UsuarioAdapter(mContext: Context,val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater= LayoutInflater.from(mContext)
     var list: List<Usuario> = mutableListOf()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_usuario, parent, false)
        return UsuarioH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as UsuarioH
        with(list[position]){


            holder.nombre.text=nom_apell
            if (adm!=null&&adm!!){holder.adm.visibility=View.VISIBLE }
            else{holder.adm.visibility=View.GONE}



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
