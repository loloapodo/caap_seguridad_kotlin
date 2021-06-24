package com.ello.kotlinseguridad.Adapter

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.databinding.IEvidenciaBinding
import com.parse.ParseFile
import java.io.File


class EvidenciaAdapter(mContext: Context, val LoadFotoClick: (image: ImageView, position: Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

     val layoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    //var listStr:     ArrayList<String> = ArrayList()
    var listBitmaps:     ArrayList<Bitmap> = ArrayList()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBind = IEvidenciaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EvidenciaH(mBind)
    }

    override fun getItemCount(): Int {
        return listBitmaps.size
    }

    override fun onBindViewHolder(holderT: RecyclerView.ViewHolder, position: Int) {
        val holder = holderT as EvidenciaH

        Log.e("onBindViewHolder","EvidenciaAdapter $position")


        with(listBitmaps[position]){
                holder.image.setImageBitmap(this)
                holder.image.setOnClickListener { LoadFotoClick(holder.image,position) }



        }





    }



    fun addImages(str: String,notify:Boolean) {
        Log.e("add adapter",str)
        val imgFile = File(str)
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            if (myBitmap!=null){
                listBitmaps.add(myBitmap)
                notifyDataSetChanged()
            }

        }

    }


    fun addImages(pf: ParseFile,notify:Boolean) {


       val myBitmap= Snippets.FileToBitmap(pf)
        if (myBitmap!=null){
            listBitmaps.add(myBitmap)
            if (notify){notifyDataSetChanged()}
        }

    }










    fun clearImages() {
        listBitmaps.clear()

    }

    fun get_Bitmaps(): ArrayList<Bitmap> {
        return listBitmaps;
    }


    inner class EvidenciaH(mBind: IEvidenciaBinding) : RecyclerView.ViewHolder(mBind.root) {

        val image=mBind.imagerecycler
        //val imageDel=mBind.imageDelete


    }

}
