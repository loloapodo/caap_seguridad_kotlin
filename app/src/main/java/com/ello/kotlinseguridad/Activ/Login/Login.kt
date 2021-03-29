package com.ello.kotlinseguridad.Activ.Login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private  var model: LoginVM= LoginVM(this)
    private lateinit var mBind:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Init()
    }

    private fun Init() {


        mBind=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mBind.root)

    }

    fun ClickLogearse(view: View) {

        with(mBind){
            model.Autentificar(edituser.text.toString(),editpass.text.toString(),
                {},
                {Toast.makeText(view.context,resources.getString(R.string.logueado_seccion_adm),Toast.LENGTH_SHORT).show()},
                {Toast.makeText(view.context,resources.getString(R.string.query_not_found),Toast.LENGTH_SHORT).show()},
                {Toast.makeText(view.context,resources.getString(R.string.error_loguin),Toast.LENGTH_SHORT).show()}
            );
        }
    }
}