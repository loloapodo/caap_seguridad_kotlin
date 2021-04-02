package com.ello.kotlinseguridad.Adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.kotlinseguridad.R

class CheckAdapter(mContext: Context, val iClick:(str:String)->Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
     var list: List<Usuario> = mutableListOf()
     var checked_list: ArrayList<Usuario> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = layoutInflater.inflate(R.layout.i_check_us, parent, false)
        return CheckH(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as CheckH
        holder.check.text=list[position].nom_apell
        holder.check.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){checked_list.add(list[position])}
            else{ checked_list.remove(list[position]) }

        })



    }

    inner class CheckH(view: View) : RecyclerView.ViewHolder(view) {
        var check: CheckBox = view.findViewById(R.id.item_check)
    }

}
