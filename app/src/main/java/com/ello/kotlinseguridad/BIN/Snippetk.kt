package com.ello.kotlinseguridad.BIN

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


        fun LeerFechaR(lo:Long?):String{


            if (lo==null){return "null"}

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lo
            val mYear = calendar[Calendar.YEAR]
            val mMonth = calendar[Calendar.MONTH]+1
            val mDay = calendar[Calendar.DAY_OF_MONTH]
            return "$mDay/$mMonth/$mYear"
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

        suspend fun PonerFotoCircular(c:CircularImageView,parsefile:ParseFile?)
        {
            Log.e("ParseFile", "PonerFotoCircular")
            val parseFile: ParseFile? = parsefile
            if (parseFile != null) {
                try {
                    val bitmap = BitmapFactory.decodeByteArray(parseFile.data, 0, parseFile.data.size)
                    withContext(Dispatchers.Main){c.setImageBitmap(bitmap);Log.e("done", "PonerFotoCircular")}

                } catch (e: Exception) {
                    Log.e("ParseFile", "Error al convertir a bitmap")
                    Log.e("ParseFile", e.message)
                }
            }else
            {
                withContext(Dispatchers.Main){c.setImageDrawable(c.context.resources.getDrawable(R.drawable.male));Log.e("done", "PonerFotoCircular")}
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


    }







}