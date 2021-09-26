package com.ello.kotlinseguridad.editar

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.adapter.AnadirPregAdapter
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityEFormBinding
import com.ello.kotlinseguridad.drawer1
import java.util.*


class EForm : AppCompatActivity() {


    private lateinit var mBind: ActivityEFormBinding
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
        InitSpinners()
        InitRecycler()


        //mBind.imageVOtrapregunta.setOnClickListener {mAdapter.addField("")}



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

    private fun InitSpinners() {

        val arr1= ArrayList<String>()
        arr1.add(BIN.TIPO_FORMULARIO0)
        arr1.add(BIN.TIPO_FORMULARIO1)
        arr1.add(BIN.TIPO_FORMULARIO2)
        arr1.add(BIN.TIPO_FORMULARIO3)
        arr1.add(BIN.TIPO_FORMULARIO4)


        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr1)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBind.spinnerTipoFormulario.adapter = adapter

        val arr2= ArrayList<String>()
        arr2.add("Cantidad de preguntas")
        for(a in 1..24){arr2.add(a.toString())}

        //arr2.add("10")

        var adapter2: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr2)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBind.spinnerCantPreguntas.adapter = adapter2


        mBind.spinnerCantPreguntas.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                Log.e("Seleccionada la pos","$position")
                mAdapter.CrearLaListaVacia(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }




    }


    fun AcpetarClick(view: View) {




        with(mBind){

            if (spinnerTipoFormulario.selectedItem.toString()==BIN.TIPO_FORMULARIO0){
                Toast.makeText(getThis(), resources.getString(R.string.toast_seleccione_tipo_formulario), Toast.LENGTH_SHORT).show();
                return
            }

            if (vm.CamposEstanMal(editFormularNombre))
            {
                Toast.makeText(getThis(), resources.getString(R.string.toast_campos_mal), Toast.LENGTH_SHORT).show();
                return
            }
            if(mAdapter.itemCount==0){
                Toast.makeText(getThis(), resources.getString(R.string.toast_anadelaspreguntas), Toast.LENGTH_SHORT).show();
                return
            }

            if (mAdapter.TienePreguntasSinLlenar()){
                Toast.makeText(getThis(), resources.getString(R.string.toast_preguntas_sin_llenar), Toast.LENGTH_SHORT).show();
                return
            }






                if (mObjIdForm_toEdit.isNullOrEmpty())//Crear
                {
                    Log.e("Crear", "Crear")
                   vm.CrearFormulario(
                           editFormularNombre.text.toString(),
                           spinnerTipoFormulario.selectedItem.toString(),
                           Calendar.getInstance().timeInMillis, mAdapter.list,
                           { Toast.makeText(getThis(), it, Toast.LENGTH_SHORT).show(); },
                           { Toast.makeText(getThis(), resources.getString(R.string.formulario_creado), Toast.LENGTH_SHORT).show();setResult(drawer1.RES_OK_CREAR_FORMULARIO);finish() },
                           { Toast.makeText(getThis(), resources.getString(R.string.formulario_creado_error), Toast.LENGTH_SHORT).show() }
                   )



                }else //Editar
                {
                    Log.e("Editar", "Editar")
                 vm.EditarFormulario(mObjIdForm_toEdit!!,
                         mBind.editFormularNombre.text.toString(),
                        spinnerTipoFormulario.selectedItem.toString(),
                         Calendar.getInstance().timeInMillis, mAdapter.list,
                         { Toast.makeText(getThis(), it, Toast.LENGTH_SHORT).show(); },
                         { Toast.makeText(getThis(), resources.getString(R.string.formulario_editado), Toast.LENGTH_SHORT).show();setResult(drawer1.RES_OK_CREAR_FORMULARIO);finish() },
                         { Toast.makeText(getThis(), resources.getString(R.string.formulario_creado_error), Toast.LENGTH_SHORT).show() }
                 )

                }

            }
    }


    private fun Init() {
        mBind= ActivityEFormBinding.inflate(layoutInflater)

        vm= EFormVM(this)
        setContentView(mBind.root)
        if (intent.hasExtra(EXTRA_OBJ_ID)) { mObjIdForm_toEdit=intent.getStringExtra(EXTRA_OBJ_ID) }
        if (mObjIdForm_toEdit.isNullOrEmpty()){mBind.included.toolbar.title = resources.getString(R.string.titleCformulario)}
        else{ mBind.included.toolbar.title = resources.getString(R.string.titleEformulario) }






    }

    private fun InitRecycler() {
        mRecyclerView=mBind.recyclerAnadirpreg
        val llm = LinearLayoutManager(this);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= AnadirPregAdapter(this, {}) ;
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
/*
        val t= TimePickerFragment()
        t.textView=mBind.timePick
        t.vm=vm
        t.show(supportFragmentManager, "timePicker")
*/
    }
    class  TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

        lateinit var textView: TextView;
        lateinit var vm: EFormVM



        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)


            Log.e("E", "onCreateDialog Time")
            // Create a new instance of TimePickerDialog and return it
            return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        }



        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            Log.e("E", "onTimeSet Date")
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
            Log.e("E", "onCreateDialog Date")
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
            // Do something with the date chosen by the user
            Log.e("E", "onDateSet Date")
            textView.text=day.toString()+"/"+(month+1).toString()+"/"+year.toString()
            vm.year=year
            vm.month=month
            vm.day=day



        }
    }

    fun CancelarClick(view: View) {finish()}


}
