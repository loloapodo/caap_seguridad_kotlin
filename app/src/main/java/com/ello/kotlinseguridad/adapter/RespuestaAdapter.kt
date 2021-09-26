package com.ello.kotlinseguridad.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.parseobj.Pregunta
import com.ello.kotlinseguridad.parseobj.Respuesta
import com.ello.kotlinseguridad.databinding.IRespondiendoBinding

class RespuestaAdapter(mContext: Context, val LoadFotoClick:(image:ImageView,position:Int)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    var listResp:ArrayList<Respuesta> = ArrayList()
    var listPreg:     ArrayList<Pregunta> = ArrayList()
    var listFotos:     ArrayList<Bitmap?> = ArrayList()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBind = IRespondiendoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RespH(mBind)
    }

    override fun getItemCount(): Int {
        return listPreg.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as RespH


        holder.checkPreg.text=listPreg[position].nombre

        with(listResp[position]){


            holder.checkPreg.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                checked=isChecked
            })
            holder.descrip.addTextChangedListener {
                respuesta=it.toString()
                holder.checkPreg.isChecked = it.toString().isNotEmpty()
            }
            //holder.imageV.setOnClickListener(View.OnClickListener {
              //  LoadFotoClick(holder.imageV,position)
                    //})


        }





    }

    fun SetPreguntas(list: ArrayList<Pregunta>) {

        listPreg=list


        for (i in listPreg) {

            val temp=Respuesta()//no sacarlo del for.
            temp.checked=false
            temp.respuesta=""
            listResp.add(temp)
            listFotos.add(BIN.GET_EMPTY_BITMAP())
        }



    }

    inner class RespH(mBind: IRespondiendoBinding) : RecyclerView.ViewHolder(mBind.root) {

        val checkPreg=mBind.itemNombrePregunta
        //val imageV=mBind.imageRes
        val descrip=mBind.editactivDescrip

    }

}
