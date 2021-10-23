package com.ello.kotlinseguridad.simple


import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivitySReportesBinding
import java.util.*


class SDescReportes : AppCompatActivity() {


    private lateinit var mBind: ActivitySReportesBinding
    private lateinit var vm: SDescReportesVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Init()


    }

    private fun Init() {

        mBind = ActivitySReportesBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = "Reporte de Tareas"
        setContentView(mBind.root)

        actionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        vm = SDescReportesVM(this)

        vm.estado.observe(this, androidx.lifecycle.Observer {
            if (it==Estado.Idle){mBind.botonDescargar.visibility=View.VISIBLE;mBind.reportesProgressbar.visibility=View.GONE}
            else{mBind.botonDescargar.visibility=View.INVISIBLE;mBind.reportesProgressbar.visibility=View.VISIBLE}
        })
    }


    private fun getThis(): Context {
        return this;
    }

    fun CancelarClick(view: View) {
        finish()
    }

    fun DescargarClick(view: View) {



        if (!BIN.TengoInternet(this)) {
            Toast.makeText(view.context, resources.getString(R.string.sin_conexion_intern), Toast.LENGTH_SHORT).show();return
        }


        if (BIN.TengoPermisoREAD(this)) {
            Toast.makeText(this, "Descargando ...", Toast.LENGTH_SHORT).show()
            AbrirRespuestasPorFecha()

        } else {
            BIN.PedirREADPermission(this)
        }

    }

    private fun AbrirRespuestasPorFecha() {

        if(vm.fecha_desde==null||vm.fecha_hasta==null|| vm.fecha_desde!! >vm.fecha_hasta!!){

            Toast.makeText(this,"Rectifique las fechas",Toast.LENGTH_SHORT).show()
            return
        }
        else
        {

            val i=Intent(this,RespuestasPorFecha::class.java)

            i.putExtra(RespuestasPorFecha.STARTDATE,vm.fecha_desde)
            i.putExtra(RespuestasPorFecha.ENDDATE,vm.fecha_hasta)
            if (mBind.itemNombrePregunta.text.isNullOrEmpty())
            {i.putExtra(RespuestasPorFecha.FILTRO_BUSQ,mBind.itemNombrePregunta.text.toString())}

            startActivity(i)



        }





    }

    fun MostrarDatePickerdesde(view: View) {
        val newFragment = DatePickerFragment(true, false, mBind.datePickDesde, vm)
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun MostrarDatePickerhasta(view: View) {

        val newFragment = DatePickerFragment(false, true, mBind.datePickHasta, vm)
        newFragment.show(supportFragmentManager, "datePicker")
    }


    class DatePickerFragment(val desde: Boolean, val hasta: Boolean, val textView: TextView, val vm: SDescReportesVM) : DialogFragment(), DatePickerDialog.OnDateSetListener {


        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Create a new instance of DatePickerDialog and return it
            Log.e("E", "onCreateDialog Date")
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
            Log.e("E", "onDateSet Date")
            textView.text = day.toString() + "/" + (month + 1).toString() + "/" + year.toString()
            if (desde) {
                vm.PonerDesde(day, month, year)
            } else if (hasta) {
                vm.PonerHasta(day, month, year)
            } else {
                Log.e("date picker class", "Error al llamar crear esta clase necesita al menos una basndera true")
            }
        }

    }





    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode ==BIN.REQUEST_MY_PERMISSIONS_READ){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Descargando ...", Toast.LENGTH_SHORT).show()
                AbrirRespuestasPorFecha()

            }
        }
    }






}








