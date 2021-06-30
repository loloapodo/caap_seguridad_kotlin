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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.BIN.CRUD
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.ParseObj.Respuesta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class SDescReportesVM(val context: Context) : ViewModel() {

     var fecha_desde:Long?=null
    var fecha_hasta:Long?=null
     lateinit var maker:PDF_Maker
    var estado = MutableLiveData<Estado>()

    init {
        estado.value=Estado.Idle
    }


    fun Cargar() {

    }

     fun Exportar(nombre: String, fb: (s:String) -> Unit) {


        if (nombre.isNullOrEmpty()){
            fb("Falta nombre del archivo")
            return
        }
        if(fecha_desde==null||fecha_hasta==null|| fecha_desde!! >fecha_hasta!!){

            fb("Rectifique las fechas")
            return
        }

        if(!BIN.TengoInternet(context)){
            fb("Necesita Internet")
            return
        }

         estado.value= Estado.CompilingPDF


        maker=PDF_Maker(context,nombre)

        viewModelScope.launch (Dispatchers.IO){
            CRUD.CargarTodasRespuestasRangeFechas(fecha_desde!!,fecha_hasta!!,{respuestas->


                Log.e("RespuestasRangeFechas","Total de respuestas:${respuestas.size}")

                if (respuestas.isNullOrEmpty()) {
                    fb("No se encontraron reportes para esas fechas")
                }
                else{

                    var code=""//Codigo formado IDusuario + IDactividad
                    var respuestas_un_informe=ArrayList<Respuesta>()

                    for (r in respuestas){
                        val co=r.ref_usuario!!.objectId+r.ref_actividad!!.objectId
                        if (co!=code){
                            maker.ImprimirPagina(respuestas_un_informe)
                            respuestas_un_informe.clear()
                            respuestas_un_informe.add(r)
                            code=co
                        }else
                        {
                            respuestas_un_informe.add(r)
                        }

                    }

                    maker.ImprimirPagina(respuestas_un_informe)
                    maker.SalvarPDF()
                    maker.AbrirPDF()




                }




            },{})
        }





    }




    fun createpdf(path_name: String, titulo: String, subtitulo: String, ciudad: String, lugar: String) {
        val bounds = Rect()
        val pageWidth = 300;
        val pageheight = 470;
        val pathHeight = 2F
        val myPdfDocument = PdfDocument()
        val paint = Paint()
        val paint2 = Paint()
        val path = Path()
        val myPageInfo = PageInfo.Builder(pageWidth, pageheight, 1).create()
        val documentPage = myPdfDocument.startPage(myPageInfo)
        val canvas: Canvas = documentPage.canvas
        var y = 25F // x = 10,
        var x = 10F


        y=EscribirCentro(titulo,paint,bounds,y,canvas)
        y=EscribirCentro(subtitulo,paint,bounds,y,canvas)

        y=BajarLinea(x,y,paint,canvas)
        LineaHorizontal(path,pageWidth,pathHeight,paint2,x,y,canvas)
        y=BajarLinea(x,y,paint,canvas)
        y=Escribir(lugar,y,paint,canvas)
        y=Escribir(ciudad,y,paint,canvas)
        y=BajarLinea(x,y,paint,canvas)
        LineaHorizontal(path,pageWidth,pathHeight,paint2,x,y,canvas)
        y=BajarLinea(x,y,paint,canvas)

        y=ImprimirFoto(R.drawable.ic_menu_camera,context,100,50,canvas,x,y,paint)

        myPdfDocument.finishPage(documentPage)
        val file = File(path_name)
        try {
            myPdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        myPdfDocument.close()
        viewPdfFile(file)

    }

    private fun ImprimirFoto(resourseId: Int, c: Context, ancho: Int, largo: Int, canvas: Canvas, x: Float,y: Float,paint: Paint): Float {
        val res: Resources = c.resources
        val bitmap = BitmapFactory.decodeResource(res, R.drawable.ic_menu_camera)
        val b = Bitmap.createScaledBitmap(bitmap, ancho, largo, false)
        canvas.drawBitmap(b, x, y, paint)
        val distancia =y+ 25
        //canvas.drawText("Nombre de App", 120F, y, paint)
        return distancia
    }

    private fun Escribir(str: String, y: Float, paint: Paint, canvas: Canvas):Float {
        val distancia=y+ (paint.descent() - paint.ascent()).toInt()
        val x = 10F
        canvas.drawText(str, x, y, paint)
        return distancia

    }

    private fun EscribirCentro(str: String, paint: Paint, bounds:Rect, y:Float, canvas: Canvas):Float {
        paint.getTextBounds(str, 0, str.length, bounds)
        val x = (canvas.width / 2 - bounds.width() / 2).toFloat()
        val distancia = y+ (paint.descent() - paint.ascent()).toInt()
        canvas.drawText(str, x, y, paint)
        return distancia
    }

    private fun BajarLinea(x: Float,y: Float,paint: Paint,canvas: Canvas):Float {
        val distanciaY = y+(paint.descent() - paint.ascent()).toInt()
        canvas.drawText("", x, y, paint)
        return distanciaY;
    }

    private fun LineaHorizontal(path: Path,pageWidth:Int,pathHeight:Float,paint2: Paint,x:Float,y:Float,canvas: Canvas) {
        path.lineTo(pageWidth.toFloat(), pathHeight)
        paint2.color = Color.GRAY
        paint2.style = Paint.Style.STROKE
        path.moveTo(x, y)
        canvas.drawLine(0F, y, pageWidth.toFloat(), y, paint2)
    }


    fun viewPdfFile(file: File) {

        val intent = Intent(Intent.ACTION_VIEW)
        val u= FileProvider.getUriForFile(context, context.applicationContext.packageName.toString() + ".provider", file)
        intent.setDataAndType(u, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent)
    }








    fun PonerDesde(day: Int, month: Int, year: Int) {

        val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,0)
        c.set(Calendar.MINUTE,0)

        c.set(Calendar.YEAR,year!!)
        c.set(Calendar.MONTH,month!!)
        c.set(Calendar.DAY_OF_MONTH,day!!)
        fecha_desde=c.timeInMillis
    }

    fun PonerHasta(day: Int, month: Int, year: Int) {
        val c= Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY,23)
        c.set(Calendar.MINUTE,59)

        c.set(Calendar.YEAR,year!!)
        c.set(Calendar.MONTH,month!!)
        c.set(Calendar.DAY_OF_MONTH,day!!)
        fecha_hasta=c.timeInMillis
    }
}