package com.ello.kotlinseguridad.adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.util.rangeTo
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.parseobj.Pregunta
import com.ello.kotlinseguridad.R
import com.google.android.material.textfield.TextInputEditText

class AnadirPregAdapter(mContext: Context, val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
     var list: ArrayList<String> = ArrayList()


    override fun getItemViewType(position: Int): Int {
        return R.layout.i_preguntantdo;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = layoutInflater.inflate(viewType, parent, false)
                return AnadPregH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {

        if (holderT is AnadPregH){
            val holder = holderT
            holder.preg.setText(list[position])
            holder.No.text=(position+1).toString()+"-"



        }




    }






    inner class AnadPregH(view: View) : RecyclerView.ViewHolder(view) {
        var preg: TextInputEditText = view.findViewById(R.id.item_nombre_pregunta)
        var No: TextView = view.findViewById(R.id.numero_preguntando)
        init {
            preg.addTextChangedListener { list[adapterPosition]=it.toString() }
        }
    }



    fun CrearLaListaVacia(total_preg: Int) {
         list = ArrayList(total_preg)
        for (i in 0 until  total_preg){list.add("")}
        notifyDataSetChanged()
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

    fun TienePreguntasSinLlenar(): Boolean {
    for (i in list){if (i.isEmpty()){return true}}
        return false
    }
}
