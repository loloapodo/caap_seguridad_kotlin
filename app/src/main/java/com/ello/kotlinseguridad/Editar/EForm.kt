package com.ello.kotlinseguridad.Editar

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.AnadirPregAdapter
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityEditarFormularioBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.min

class EForm : AppCompatActivity() {


    private lateinit var mBind: ActivityEditarFormularioBinding
    private lateinit var vm: EFormVM
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AnadirPregAdapter
    private var mObjIdForm_toEdit:String? = ""
    companion object{
        var EXTRA_OBJ_ID="extra_editar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Init()
        InitRecycler()

        mAdapter.addField("")
        mBind.imageVOtrapregunta.setOnClickListener {mAdapter.addField("")}



/*todo Cargar en el formulario editado las preguntas
            val query = ParseQuery.getQuery<Pregunta>(Pregunta.class_name)
            query.whereEqualTo(Pregunta.field_ref_formulario,formulario)
            query.findInBackground(FindCallback { list, e ->
                if (e==null){fg(list)}
                else
                { fb();Log.e("Error","Buscar All Preguntas") }
            })
  */

    }


    fun AcpetarClick(view: View) {



        if (!vm.EstaListoParaCrear()){
            Toast.makeText(getThis(),resources.getString(R.string.complete_fechas),Toast.LENGTH_SHORT).show()
            return
        }

        with(mBind){

            if (vm.CamposEstanMal(editFormularNombre))
            {
                Toast.makeText(getThis(),resources.getString(R.string.toast_campos_mal),Toast.LENGTH_SHORT).show();
                return
            }


                if (mObjIdForm_toEdit.isNullOrEmpty())//Crear
                {
                    Log.e("Crear","Crear")
                   vm.CrearFormulario(
                           editFormularNombre.text.toString(),
                           Calendar.getInstance().timeInMillis,Calendar.getInstance().timeInMillis,mAdapter.list,
                           {Toast.makeText(getThis(),resources.getString(R.string.formulario_creado),Toast.LENGTH_SHORT).show();finish()},
                           {Toast.makeText(getThis(),resources.getString(R.string.formulario_creado_error),Toast.LENGTH_SHORT).show()}
                           )



                }else //Editar
                {
                    Log.e("Editar","Editar")
                 vm.EditarFormulario(mObjIdForm_toEdit!!,
                         mBind.editFormularNombre.text.toString(),
                         Calendar.getInstance().timeInMillis,Calendar.getInstance().timeInMillis,mAdapter.list,
                         {Toast.makeText(getThis(),resources.getString(R.string.formulario_editado),Toast.LENGTH_SHORT).show();finish()},
                         {Toast.makeText(getThis(),resources.getString(R.string.formulario_creado_error),Toast.LENGTH_SHORT).show()}
                         )

                }

            }
    }


    private fun Init() {
        mBind= ActivityEditarFormularioBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleEformulario)
        vm= EFormVM(this)
        setContentView(mBind.root)
        if (intent.hasExtra(EXTRA_OBJ_ID)) { mObjIdForm_toEdit=intent.getStringExtra(EXTRA_OBJ_ID) }

    }

    private fun InitRecycler() {
        mRecyclerView=mBind.recyclerAnadirpreg
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= AnadirPregAdapter(this,{}) ;
        mRecyclerView.adapter=mAdapter;
    }
    private fun getThis(): Context =this








    fun MostrarDatePicker(view: View) {

        val newFragment = DatePickerFragment()
        newFragment.textView=mBind.datePick
        newFragment.vm=vm
        newFragment.show(supportFragmentManager, "datePicker")

    }
    fun MostrarTimePicker(view: View) {

        val t:TimePickerFragment= TimePickerFragment()
        t.textView=mBind.timePick
        t.vm=vm
        t.show(supportFragmentManager, "timePicker")

    }
    class  TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        lateinit var textView: TextView;
        lateinit var vm: EFormVM



        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)


            Log.e("E","onCreateDialog Time")
            // Create a new instance of TimePickerDialog and return it
            return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        }



        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            Log.e("E","onTimeSet Date")
            textView.text=String.format("%02d:%02d", hourOfDay, minute)
            vm.hour=hourOfDay
            vm.minutes=minute
            // Do something with the time chosen by the user
        }
    }
    class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
        lateinit var textView: TextView;
        lateinit var vm: EFormVM

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            // Create a new instance of DatePickerDialog and return it
            Log.e("E","onCreateDialog Date")
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
            Log.e("E","onDateSet Date")
            textView.text=day.toString()+"/"+(month+1).toString()+"/"+year.toString()
            vm.year=year
            vm.month=month
            vm.day=day



        }
    }


}