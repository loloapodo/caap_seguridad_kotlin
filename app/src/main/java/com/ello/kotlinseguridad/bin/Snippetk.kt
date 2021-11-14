package com.ello.kotlinseguridad.bin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.ello.kotlinseguridad.R
import com.mikhaellopez.circularimageview.CircularImageView
import com.parse.ParseFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*

class Snippetk {


    companion object{


        fun LeerFechaR(lo:Long?,divisor:String="/"):String{


            if (lo==null){return "null"}

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lo
            val mYear = calendar[Calendar.YEAR]
            val mMonth = calendar[Calendar.MONTH]+1
            val mDay = calendar[Calendar.DAY_OF_MONTH]
            return "$mDay$divisor$mMonth$divisor$mYear"
        }



        fun CortarTitle(string: String?):String
        {
            if (string.isNullOrEmpty()){return ""}
          return string.split(" ")[0]
        }


        fun BitmapToParseFile(b:Bitmap):ParseFile{
            val stream = ByteArrayOutputStream()
            b.compress(Bitmap.CompressFormat.JPEG, BIN.COMPRESS_PERCENT, stream)
            val byteArray: ByteArray = stream.toByteArray()
            return  ParseFile("image.png", byteArray)

        }

        suspend fun PonerFotoCircular(c:CircularImageView,parsefile:ParseFile?,str:String="clase")
        {
            Log.e("ParseFile", "PonerFotoCircular")
            Log.e("clase", str)
            val parseFile: ParseFile? = parsefile
            if (parseFile != null && parseFile.isDataAvailable) {
                try {
                    val bitmap = BitmapFactory.decodeByteArray(parseFile.data, 0, parseFile.data.size)
                    withContext(Dispatchers.Main){c.setImageBitmap(bitmap);Log.e("done", "PonerFotoCircular")}

                } catch (e: Exception) {
                    Log.e("ParseFile", "Error al convertir a bitmap")
                    Log.e("ParseFile", e.message!!)
                    Log.e("clase", str)
                }
            }else
            {
                if (parseFile == null){ Log.e("ParseFile", "Null") }
                else{ Log.e("isDataAvailable", parseFile.isDataAvailable.toString()) }
                withContext(Dispatchers.Main){
                    if (str.contains("equip")){c.setImageDrawable(c.context.resources.getDrawable(R.drawable.equi));}
                    else{c.setImageDrawable(c.context.resources.getDrawable(R.drawable.male)); }
                }

            }
        }
        suspend fun PonerFoto(c:ImageView,parsefile:ParseFile?,str:String="clase")
        {

            val parseFile: ParseFile? = parsefile

            if (parseFile != null&&parseFile.isDataAvailable&&parseFile.data.size>300) {
                try {
                    val bitmap = BitmapFactory.decodeByteArray(parseFile.data, 0, parseFile.data.size)
                    Log.e("PonerFoto","Parse file size="+parseFile.data.size.toString())
                    Log.e("PonerFoto","Bitmap size: ${bitmap.height}x${bitmap.width}")
                    withContext(Dispatchers.Main){c.setImageBitmap(bitmap);Log.e("done", "PonerFoto")}

                } catch (e: Exception) {
                    Log.e("ParseFile", "Error al convertir a bitmap")
                    Log.e("ParseFile", e.message!!)
                }
            }else
            {
                if (parseFile == null){ Log.e("ParseFile", "Null") }
                else{ Log.e("isDataAvailable", parseFile.isDataAvailable.toString()) }
                withContext(Dispatchers.Main){
                    if (str.contains("equip")){c.setImageDrawable(c.context.resources.getDrawable(R.drawable.equi));}
                    else{c.setImageDrawable(c.context.resources.getDrawable(R.drawable.male));}
                }
            }
        }


        fun PonerFoto(
            foto: Bitmap?,
            imageView: ImageView

        ) {

            Log.e("snipek","poner foto")
            if (foto != null) {
                imageView.setImageBitmap(foto)
            }
            else
            {
                Log.e("snipek","poner foto null")
            }

        }

        fun LeerHoraR(lo: Long?): String? {


            if (lo==null){return "null"}
            val c=Calendar.getInstance()
            c.timeInMillis=lo

            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

           return String.format("%02d:%02d", hour, minute)


        }
        fun LeerFechaYHoraR(lo: Long?): String? {


            if (lo==null){return "null"}
            val c=Calendar.getInstance()
            c.timeInMillis=lo

            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]+1
            val mDay = c[Calendar.DAY_OF_MONTH]

            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            return String.format("%02d:%02d", hour, minute) +"  $mDay/$mMonth/$mYear "


        }


    }







}