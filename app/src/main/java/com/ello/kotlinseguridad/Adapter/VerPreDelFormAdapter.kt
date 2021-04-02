package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.ParseObj.Pregunta
import com.ello.kotlinseguridad.R

class VerPreDelFormAdapter(mContext: Context, val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
     var list: ArrayList<String> = ArrayList()


    override fun getItemViewType(position: Int): Int {
        return R.layout.i_ver_pre_del_form;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


                val view = layoutInflater.inflate(viewType, parent, false)
                return PregH(view)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {

        if (holderT is PregH){
            val holder = holderT
            holder.preg.setText(list[position])
            holder.No.text=(position+1).toString()+"-"


            holder.preg.addTextChangedListener { list.set(position,it.toString()) }


        }




    }

    inner class PregH(view: View) : RecyclerView.ViewHolder(view) {
        var preg: TextView = view.findViewById(R.id.item_nombre_pregunta)
        var No: TextView = view.findViewById(R.id.numero_preguntando)

    }
    fun setPreguntas(listica:List<Pregunta>){
        list.clear()
        for(p in listica)
        {
            if (p.nombre==null){list.add("")}
            else{list.add(p.nombre!!)}

        }
    }

    fun setPreguntas2(listica:List<String>){
        list.clear()
        list.addAll(listica)
    }




}
