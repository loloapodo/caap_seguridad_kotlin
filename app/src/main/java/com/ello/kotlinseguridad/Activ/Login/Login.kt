package com.ello.kotlinseguridad.Activ.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ello.kotlinseguridad.Estado
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private  lateinit var vm: LoginVM
    private lateinit var mBind:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Init()

        vm.estado.observe(this, Observer {
            if (it==Estado.Idle){mBind.button.visibility=View.VISIBLE;mBind.loginProgressbar.visibility=View.GONE}
            else{ mBind.button.visibility=View.GONE;mBind.loginProgressbar.visibility=View.VISIBLE}
        })

    }

    private fun Init() {


        mBind=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBind.root)
        vm= LoginVM(this)
        if(vm.Continuar_Con_Seccion()){finish()}


    }

    fun ClickLogearse(view: View) {

        if (vm.isNotIdle()){Toast.makeText(view.context,resources.getString(R.string.en_progreso),Toast.LENGTH_SHORT).show();return }

        with(mBind){
            vm.Autentificar(edituser.text.toString(),editpass.text.toString(),
                {finish()},
                {Toast.makeText(view.context,resources.getString(R.string.logueado_seccion_adm),Toast.LENGTH_SHORT).show();finish()},
                {Toast.makeText(view.context,resources.getString(R.string.query_not_found),Toast.LENGTH_SHORT).show()},
                {Toast.makeText(view.context,resources.getString(R.string.error_loguin),Toast.LENGTH_SHORT).show()}
            );
        }
    }
}