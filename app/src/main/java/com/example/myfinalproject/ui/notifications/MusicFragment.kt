package com.example.myfinalproject.ui.notifications


import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle


import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myfinalproject.R
import com.example.weather.CityViewModel
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.io.IOException
import kotlin.concurrent.thread

class MusicFragment: Fragment() {

    private lateinit var viewModel: MusicViewModel
    val TAG="MainActivity"
    val mediaPlayer= MediaPlayer()

    val Channel_ID="my channel"
    val Notification_ID=1

    //线程是否开启
    var flag:Boolean=true

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
      //  viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MusicViewModel::class.java)
       // viewModel=ViewModelProvider(this).get(MusicViewModel::class.java)


        mediaPlayer.setOnPreparedListener{
            it.start()
        }
        mediaPlayer.setOnCompletionListener {
            viewModel.OnCompletionListener()
            //play()
        }
        if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }
        else{
            Log.d("看看执行到这里没有","xcdev")
            getMusicList()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        button_play.setOnClickListener {
            play()
        }
        button_pause.setOnClickListener{
            if(viewModel.isPauses.value!!){
                mediaPlayer.start()
                viewModel.nopause()

                //isPause=false
            }else{
                mediaPlayer.pause()
                //isPause=true
                viewModel.pause()
            }
        }
        button_Stop.setOnClickListener {
            mediaPlayer.stop()
        }
        button_next.setOnClickListener {
            viewModel.OnCompletionListener()
            play()
        }
        button_prev.setOnClickListener {
            viewModel.onprev()
            play()
        }

        thread {
            while (true){
                //Log.d("flag________________","${flag}")
                if(flag.equals(false)){
                    break
                }else{
                    requireActivity().runOnUiThread{
                        seekBar.max=mediaPlayer.duration
                        seekBar.progress=mediaPlayer.currentPosition
                    }
                }
                Thread.sleep(1000)
            }
        }
    }

    fun play(){
        if(viewModel.musicLists.value?.size==0) return
        val path=viewModel.musicLists.value?.get(viewModel.currents.value!!)
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
            textView_mn.text=viewModel.musicNameLists.value!![viewModel.currents.value!!]
            textView_count.text="${viewModel.currents.value!!+1}/${viewModel.musicLists.value?.size}"
            notification()
        }catch(e: IOException){
            e.printStackTrace()
        }

    }
    fun notification(){
        //将当前歌曲名字显示在通知中
        val notificationManager=requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel= NotificationChannel(Channel_ID,"test", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
            builder= Notification.Builder(requireContext(),Channel_ID)
        }else{
            builder= Notification.Builder(requireContext())
        }
        val intent= Intent(requireActivity(),MusicFragment::class.java)
        val pendingIntent= PendingIntent.getActivity(requireContext(),1,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification=builder.setSmallIcon(R.drawable.ic_notification)
                // .setContentTitle("Notification")
                .setContentText(viewModel.musicNameLists.value!![viewModel.currents.value!!])
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        notificationManager.notify(Notification_ID,notification)
    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getMusicList()
    }

    override fun onPause() {
        super.onPause()
        flag=false
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
        Log.d("flag+++++++++++++++++","${flag}")

        mediaPlayer.release()
        Log.d("onDetach","结束了")
    }


    private fun getMusicList(){
        viewModel.getMusicList()

    }
}