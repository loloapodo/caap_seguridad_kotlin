package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.databinding.IRDoneBinding
import com.ello.kotlinseguridad.databinding.IRespondiendoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext

class RespondidoAdapter(val mContext: Context, val LoadFotoClick:(image:ImageView, position:Int)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    var listResp:   List<Respuesta> = ArrayList()
    var listPreg:     List<Pregunta> = ArrayList()

    var listFotos:     ArrayList<Bitmap?> = ArrayList()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBind = IRDoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RespH(mBind)
    }

    override fun getItemCount(): Int {
        return listResp.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as RespH


        holder.checkPreg.text=listPreg[position].nombre

        with(listResp[position]){


            //holder.imageV.setOnClickListener(View.OnClickListener {})
            holder.descrip.setText(respuesta)


            //GlobalScope.launch { Snippetk.PonerFoto(holder.imageV,foto) }




        }





    }


    fun SetPregResp(p:List<Pregunta>,r:List<Respuesta>) {

        listPreg=p
        listResp=r
        Log.e("RespondidoAdapter","Adapter cargó ${p.size} preguntas y ${r.size} respuestas")

    }

    inner class RespH(mBind: IRDoneBinding) : RecyclerView.ViewHolder(mBind.root) {

        val checkPreg=mBind.itemNombrePregunta
        //val imageV=mBind.imageRes
        val descrip=mBind.editactivDescrip

    }

}
