package com.example.bonree.xposeddemo.kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

class ActionActivity : AppCompatActivity(),View.OnClickListener {
    override fun onClick(v: View?) {
        fragment
    }
    lateinit var fragment: ActionFragment;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragment =ActionFragment()

        fragment.setCallback {
            toast(it)
        }
    }


    fun toast(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }


}


class ActionFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    fun setCallback(callback: (msg:String)->Unit){
        callback.invoke("hello")
    }

}