package com.example.myfinalproject.ui.dashboard

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.example.myfinalproject.R
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

const val gameFile = "gameFile"

//变量是game.score，其实就是game会发生改变，将game提取到viewmodel中去
class GameFragment : Fragment() {

    val cardButtons = mutableListOf<Button>()
    lateinit var adapter:CardRecyclerViewAdapter
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val rgame = loadData()
//        if (rgame != null) {
//            game = rgame
//        }else{
//            game = CardMatchingGame(24)
//        }
        viewModel=ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(GameViewModel::class.java)
        //viewModel= ViewModelProvider(this).get(GameViewModel::class.java)

        //得到game
        viewModel.games.observe(viewLifecycleOwner, Observer {
            Log.d("games","${it}")
            adapter = CardRecyclerViewAdapter(it)
            val recylerView = view.findViewById<RecyclerView>(R.id.recylerView2)
            val reset = view.findViewById<Button>(R.id.reset)
            recylerView.adapter = adapter
            val configuration = resources.configuration
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                recylerView.layoutManager = GridLayoutManager(activity, 6)
            }else{
                val gridLayoutManager = GridLayoutManager(activity, 5)
                recylerView.layoutManager = gridLayoutManager
            }
            updateUI()
            adapter.setOnCardClickListener {
                viewModel.chooseCardAtIndex(it)
                // game.chooseCardAtIndex(it)
                updateUI()
            }
            reset.setOnClickListener {
                //game.reset()
                viewModel.reset()
                updateUI()
            }
        })

    }


    fun updateUI() {
        val score = view?.findViewById<TextView>(R.id.score)

        adapter.notifyDataSetChanged()

        viewModel.games.observe(viewLifecycleOwner, Observer {
            score?.text = String.format("%s%d",getString(R.string.score),it.score)
            score?.text = getString(R.string.score) + it.score
        })

    }

//    override fun onStop() {
//        super.onStop()
//        saveData()
//    }

}