package com.example.myfinalproject.ui.watch

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WatchViewModel : ViewModel() {
    //表示second是可变的
    private var _second : MutableLiveData<Int> = MutableLiveData()
    private var running =false
    //外部不能修改，所以定义为LiveData
    val seconds: LiveData<Int> = _second
    init {
        runTimer()
    }

    fun start(){
        running=true
    }

    fun stop(){
        running=false
    }

    fun restart(){
        running=true
        _second.value=0
    }

    fun runTimer(){
        val handler =  Handler()
        val runnable=object :Runnable{
            override fun run() {
                if(running){
                    val sec=_second.value ?:0
                    _second.value=sec+1
                }
                handler.postDelayed(this,1000)
            }
        }
        handler.post(runnable)
    }
}