package com.ello.kotlinseguridad.simple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ello.kotlinseguridad.bin.BIN
import com.ello.kotlinseguridad.editar.EUsuario
import com.ello.kotlinseguridad.R
import com.ello.kotlinseguridad.bin.Snippetk
import com.ello.kotlinseguridad.databinding.ActivitySUsBinding
import kotlinx.coroutines.launch


class SUsuario : AppCompatActivity() {

    private  var vm: SUsuarioVM = SUsuarioVM()

    private lateinit var mBind: ActivitySUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




         Init()
        //CreateMyOptionMenu()
        vm.CargarElUsuario({o->

            //mBind.included.toolbar.title = Snippetk.CortarTitle(o.nom_apell)
            //mBind.included.toolbar.setTitleTextColor(Color.BLACK)
            //mBind.included.toolbar.setSubtitleTextColor(Color.BLACK)


            mBind.unaPersonaName.text=o.nom_apell+" "+o.apell
            mBind.unUsuarioNombreusuario.text=o.usuario
            mBind.unUsuarioContrasena.text=o.contrasena
            mBind.unUsuarioCedula.text=o.cedula
            mBind.unUsuarioTelefono.text=o.telefono
            mBind.unUsuarioDireccion.text=o.direccion
            mBind.unUsuarioRol.text=o.rol

            lifecycleScope.launch {
                Snippetk.PonerFotoCircular(mBind.unaPersonaImage,o.foto,"simple usuario")
            }




        },{})



    }

    private fun Init() {
        mBind = ActivitySUsBinding.inflate(layoutInflater)
        mBind.included.toolbar.title = resources.getString(R.string.titleusuario)
        val id= intent.getStringExtra("id");
        vm.id_usuario=id!!;
        setContentView(mBind.root)
    }

/*    private fun CreateMyOptionMenu() {

        val menu=mBind.included.toolbar.menu
        menuInflater.inflate(R.menu.simpleusuariomenu, menu)
        val acercaItem: MenuItem? = menu?.findItem(R.id.menu_item_eliminar)
        acercaItem?.setOnMenuItemClickListener {

    vm.BorrarUsuario({
            Toast.makeText(getThis(),resources.getString(R.string.usuario_borrado),Toast.LENGTH_SHORT).show()
            finish()
        },{

        });



            return@setOnMenuItemClickListener false;
        }

        val editarItem: MenuItem? = menu?.findItem(R.id.menu_item_editar)
        editarItem?.setOnMenuItemClickListener {


   lifecycleScope.launch {
        val i = Intent(getThis(), EUsuario::class.java);
        i.putExtra(EUsuario.EXTRA_OBJ_ID,vm.id_usuario)

        startActivity(i)
    }

            return@setOnMenuItemClickListener true;
        }
    }
 */


    private fun getThis(): Context {
return this;
    }

    fun EliminarClick(view: View) {
        if(!BIN.TengoInternet(getThis(),true)){return}
        vm.BorrarUsuario({
            Toast.makeText(getThis(),resources.getString(R.string.usuario_borrado),Toast.LENGTH_SHORT).show()
            finish()
        },{

        });
    }

    fun EditarClick(view: View) {
        if(!BIN.TengoInternet(getThis(),true)){return}
        lifecycleScope.launch {
        val i = Intent(getThis(), EUsuario::class.java);
        i.putExtra(EUsuario.EXTRA_OBJ_ID,vm.id_usuario)

        startActivity(i)
    }}

    fun CancelarClick(view: View) {
        finish()
    }


}