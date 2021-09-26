package com.ello.kotlinseguridad.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.databinding.ICheckEquipamBinding


class EquipaCheckAdapter(mContext: Context,val hide_check:Boolean, val LoadFotoClick: (image: ImageView, position: Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    var listStr:     ArrayList<String> = ArrayList()
    var listBoolean:     ArrayList<Boolean> = ArrayList()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBind = ICheckEquipamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EquipCheck(mBind)
    }

    override fun getItemCount(): Int {
        return listStr.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as EquipCheck

        Log.e("onBindViewHolder","EquipCheck $position")


        with(listStr[position]){

            if (hide_check){
                holder.checkE.buttonDrawable = null

            }else{
                holder.checkE.setOnCheckedChangeListener { buttonView, isChecked -> listBoolean[position]=isChecked }
            }
            holder.checkE.text = this



        }





    }





//Se le pasa el quipamiento separado por *
    fun addrawEq(str: String) {
        Log.e("add raw adapter",str)
        listStr= str.split("*") as ArrayList<String>

    }

    fun addEq(str: String) {
        Log.e("add adapter",str)
        listStr.add(str)
        listBoolean.add(false)

    }
    fun clearEq() {
        listStr.clear()
        listBoolean.clear()

    }

    fun get_only_chequed(): String {

        var juntos= ""

        for (i in 0 until listBoolean.size){
            if (listBoolean[i]){ juntos += listStr[i];juntos+="*" }
        }

        return juntos
        }




    inner class EquipCheck(mBind: ICheckEquipamBinding) : RecyclerView.ViewHolder(mBind.root) {

        val checkE=mBind.checkboxEquipam



    }

}
