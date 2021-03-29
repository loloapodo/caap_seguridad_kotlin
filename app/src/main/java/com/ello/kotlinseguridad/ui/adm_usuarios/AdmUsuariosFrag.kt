package com.ello.kotlinseguridad.ui.adm_usuarios

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
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.Adapter.UsuarioAdapter
import com.ello.kotlinseguridad.Simple.SUsuario

class AdmUsuariosFrag : Fragment() {

    private lateinit var vm: AdmUsuariosVM
    private lateinit var mAdapter: UsuarioAdapter
    private lateinit var mRecyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm = ViewModelProvider(requireActivity()).get(AdmUsuariosVM::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        InitRecycler(root)




            vm._listado.observe(viewLifecycleOwner, Observer {
                Log.e("observer done","observerd done");
                mAdapter.list=it
                mAdapter.notifyDataSetChanged()
            })
            vm.CargarUsuarios()










        return root
    }

    private fun InitRecycler(root: View) {
        mRecyclerView=root.findViewById(R.id.recycler)
        val llm = LinearLayoutManager(root.context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        mRecyclerView.layoutManager = llm;
        mAdapter= UsuarioAdapter(root.context) {activity?.startActivity(Intent(activity,
                SUsuario::class.java).putExtra("id",it))}
        mRecyclerView.adapter=mAdapter;

    }
}