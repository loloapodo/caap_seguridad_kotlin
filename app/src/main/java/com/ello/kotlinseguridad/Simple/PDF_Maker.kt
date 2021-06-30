package com.ello.kotlinseguridad.Simple

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.ello.kotlinseguridad.BIN.Snippetk
import com.ello.kotlinseguridad.BIN.Snippets
import com.ello.kotlinseguridad.ParseObj.Actividad
import com.ello.kotlinseguridad.ParseObj.Respuesta
import com.ello.kotlinseguridad.ParseObj.Usuario
import com.ello.twelveseconds.Formulario
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class PDF_Maker(val c:Context,name:String)  {

    private val bounds = Rect()
    private val pageWidth = 550;
    private val pageheight = 860;
    private val pathHeight = 2F
    private val myPdfDocument = PdfDocument()
    private val paint = Paint()
    private val paint2 = Paint()
    private val path = Path()
    private var myPageInfo = PageInfo.Builder(pageWidth, pageheight, 1).create()
    private lateinit var documentPage:PdfDocument.Page ;
    private lateinit var canvas: Canvas
    private var y = 25F // x = 10,
    private var x = 10F
    lateinit var path_name:String
    private var PageNo = 0
    private var PregNo = 1


    init {

       val temp=File(Environment.getExternalStorageDirectory().absolutePath+"/Reportes Seguridad/")
        temp.mkdirs();
        path_name= File(temp.absoluteFile,"$name.pdf").absolutePath


    }




    fun createpdf(titulo: String, subtitulo: String, ciudad: String, lugar: String) {



        EscribirCentro(titulo)
        EscribirCentro(subtitulo)

        BajarLinea()
        LineaHorizontal()
        BajarLinea()
        Escribir(lugar)
        Escribir(ciudad)
        BajarLinea()
        LineaHorizontal()
        BajarLinea()
        ImprimirFoto(R.drawable.ic_menu_camera,100,50)
        SalvarPDF()
        AbrirPDF()


    }

    public fun SalvarPDF() {

        val file = File(path_name)
        try {
            myPdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        myPdfDocument.close()
    }



    private fun Escribir(str: String?) {
        if (str.isNullOrEmpty()){return}
        y+= (paint.descent() - paint.ascent()).toInt()
        val x = 10F
        canvas.drawText(str, x, y, paint)
    }

    private fun EscribirCentro(str: String){
        if (str.isNullOrEmpty()){return}
        paint.getTextBounds(str, 0, str.length, bounds)
        val x = (canvas.width / 2 - bounds.width() / 2).toFloat()
        y+= (paint.descent() - paint.ascent()).toInt()
        canvas.drawText(str, x, y, paint)


    }

    private fun BajarLinea(){
        y+=(paint.descent() - paint.ascent()).toInt()
        canvas.drawText("", x, y, paint)

    }

    private fun LineaHorizontal() {
        path.lineTo(pageWidth.toFloat(), pathHeight)
        paint2.color = Color.GRAY
        paint2.style = Paint.Style.STROKE
        path.moveTo(x, y)
        canvas.drawLine(0F, y, pageWidth.toFloat(), y, paint2)
    }


   public fun AbrirPDF() {

        val file = File(path_name)
        val intent = Intent(Intent.ACTION_VIEW)
        val u= FileProvider.getUriForFile(c, c.applicationContext.packageName.toString() + ".provider", file)
        intent.setDataAndType(u, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        c.startActivity(intent)
    }

     fun ImprimirPagina(respuestas: ArrayList<Respuesta>) {
        if(respuestas.isEmpty()){return}
        else
        {
            SiguientePagina(false)
            Log.e("PDF_Maker","Imprimiendo pagina No $PageNo")
            Log.e("PDF_Maker","cantidad de respuestas en pag ${respuestas.size}")


            with(respuestas[0]){

                val usuario= ref_usuario!!.fetchIfNeeded<Usuario>()
                val actividad= ref_actividad!!.fetchIfNeeded<Actividad>()
                val formulario= ref_formulario!!.fetchIfNeeded<Formulario>()

                Log.e("ES PRIMERA PREGUNTA", firs_of_list.toString())

                LineaHorizontal()
                BajarLinea()
                EscribirCentro(usuario.nom_apell+" "+usuario.apell)
                BajarLinea()
                LineaHorizontal()

                BajarLinea()
                Escribir("Actividad: "+actividad.nombre)
                Escribir("Formulario: "+formulario.nombre)
                Escribir("Tipo Formulario: "+formulario.tipo)

                if (fecha!=null){Escribir("Fecha de envío: "+Snippetk.LeerFechaR(fecha)+" ("+Snippetk.LeerHoraR(fecha)+")")}
                if (fecha!=null&&formulario.fecha_limite!=null){
                    Escribir("Fecha límite: "+Snippetk.LeerFechaR(formulario.fecha_limite))
                    if (fecha!! <formulario.fecha_limite!!){Escribir("Entregado a tiempo: Si")}
                    else{Escribir("Entregado a tiempo: No")}
                }





                EscribirCentro("Respuestas al Formulario: ")
                BajarLinea()
                PregNo=1;
                for (r in respuestas){
                    Escribir("   ${PregNo}- " + r.respuesta)
                    PregNo++
                }
                BajarLinea()

                if (!equipos.isNullOrEmpty()){
                    var strEqu=equipos?.replace("*",",")
                    strEqu=strEqu!!.substring(0,equipos?.length!!.minus(1))
                    EscribirCentro("Equipamiento:")
                    Escribir(strEqu)
                }

                AgregarEvidencias(this)
                myPdfDocument.finishPage(documentPage)
            }
        }
    }

    private fun AgregarEvidencias(r: Respuesta) {

        if (r.e0!=null){

            SiguientePagina()
            EscribirCentro("Evidencias: ")
            BajarLinea()

            if (r.e0!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e0))
            }
            if (r.e1!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e1),true)
            }
            if (r.e2!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e2))
            }
            if (r.e3!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e3),true)
            }
            if (r.e4!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e4))
            }
            if (r.e5!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e5),true)
            }

            if (r.e6!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e6))
            }
            if (r.e7!=null){
                ImprimirFoto(Snippets.FileToBitmap(r.e7),true)
            }


        }

    }

    private fun SiguientePagina(finish_current:Boolean=true) {
        y = 25F // x = 10,
        x = 10F
        if (finish_current){myPdfDocument.finishPage(documentPage)}
        PageNo++
        myPageInfo=PageInfo.Builder(pageWidth, pageheight, PageNo).create()
        documentPage= myPdfDocument.startPage(myPageInfo);
        canvas =documentPage.canvas
    }

    private fun ImprimirFoto(bi: Bitmap,bajar: Boolean =false) {
        val ancho=(pageWidth/2)-16
        val largo=0.67*ancho
        val b = Bitmap.createScaledBitmap(bi,ancho ,largo.toInt(), false)
        canvas.drawBitmap(b, x, y, paint)

        if (bajar){
            x = 10F
            y= (y+largo+14).toFloat()
        }
        else{
            x += ancho + 4
        }

    }
    private fun ImprimirFoto(resourseId: Int,ancho:Int,largo:Int) {
        val res: Resources = c.resources
        val bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_menu_camera)
        val b = Bitmap.createScaledBitmap(bitmap, ancho, largo, false)
        canvas.drawBitmap(b, x, y, paint)
        y += 25
        //canvas.drawText("Nombre de App", 120F, y, paint)

    }

}