package com.example.myfinalproject.ui.watch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myfinalproject.R
import kotlinx.android.synthetic.main.watch_fragment.*

class WatchFragment : Fragment() {

    companion object {
        fun newInstance() = WatchFragment()
    }

    private lateinit var viewModel: WatchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.watch_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        //界面已经存在，acticity也存在
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WatchViewModel::class.java)

        // viewModel.seconds.observe(requireActivity(), Observer {
        viewModel.seconds.observe(viewLifecycleOwner, Observer {
            val hours=it/3600
            val minutes=(it%3600)/60
            val secs=it % 60
            textView_timer.text=String.format("%02d:%02d:%02d",hours,minutes,secs)
        })


        button_start.setOnClickListener {
            viewModel.start()
        }
        button_stop.setOnClickListener {
            viewModel.stop()
        }
        button_restart.setOnClickListener {
            viewModel.restart()
        }
    }
    override fun onPause() {
        super.onPause()
        Log.d("onPause","结束了")
    }
    override fun onStop() {
        super.onStop()
//        mediaPlayer.release()
//        flag=false
        Log.d("onStop","结束了")
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        mediaPlayer.release()
//        flag=false
        Log.d("onDestroyView","结束了")
    }

    override fun onDestroy() {
        super.onDestroy()
//        mediaPlayer.release()
//        flag=false
        Log.d("onDestroy","结束了")
    }

    override fun onDetach() {
        super.onDetach()
        //        mediaPlayer.release()
//        flag=false
        Log.d("onDetach","结束了")
    }


}