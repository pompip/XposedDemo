package com.example.bonree.xposeddemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.bonree.xposeddemo.kotlin.android

class MainActivity:AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        application.android("hello")
    }
}