package com.ello.kotlinseguridad.ui.us_form_undone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ello.kotlinseguridad.Adapter.FormularioAdapter
import com.ello.kotlinseguridad.BIN.BIN
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.Simple.SActivPlus
import com.ello.kotlinseguridad.Simple.SForm
import com.ello.kotlinseguridad.Simple.SFormPlus

class UsFormUndoneFrag : Fragment() {

    private lateinit var vm: UsFormUnDoneVM
    private lateinit var mAdapter: FormularioAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        vm = ViewModelProvider(requireActivity()).get(UsFormUnDoneVM::class.java)
        val root = inflater.inflate(R.layout.frag_adm_form, container, false)
        //val textView: TextView = root.findViewById(R.id.text_gallery)

        InitRecycler(root)

    vm._listado.observe(viewLifecycleOwner, Observer {
                Log.e("observer done","observerd done");
                mAdapter.list=it
                mAdapter.notifyDataSetChanged()
            })
        vm.Cargar()
        return root
    }

    private fun InitRecycler(root:View) {
        mRecyclerView=root.findViewById(R.id.recycler)
        val llm = LinearLayoutManager(root.context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter=
                FormularioAdapter(root.context){
                    startActivityForResult(
                            Intent(activity, SForm::class.java).putExtra("id",it).putExtra(SForm.EXTRA_RESUELTO,false),
                            BIN.REQ_LLENAR_FORMULARIO)}
        mRecyclerView.adapter=mAdapter;

        if (!BIN.ES_ADMIN()){
        BIN.PUEDE_FORMULARIOS {
            mAdapter.iClick={activity?.startActivity(Intent(activity, SFormPlus::class.java).putExtra("id",it))}
            Log.e("Actividades","MODO ADMIN PARA LAS ACTIVIDADES")
        }
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==BIN.REQ_LLENAR_FORMULARIO){

            Log.e("REQ_LLENAR_FORMULARIO","SE VA REFRESCAR DEBIDO A QUE REQ_LLENAR_FORMULARIO HA FINALISADO CON RESULT $resultCode")
            vm.Cargar()

        }
        super.onActivityResult(requestCode, resultCode, data)

    }



}