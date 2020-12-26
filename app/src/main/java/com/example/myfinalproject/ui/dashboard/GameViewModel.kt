package com.example.myfinalproject.ui.dashboard


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel:ViewModel() {
    companion object {
        private lateinit var game: CardMatchingGame
    }
    //一个game用来改变
    private var _game:MutableLiveData<CardMatchingGame> = MutableLiveData()
    //另一个用于外部访问
    val games=_game

    init {
        //初始化_game为空
        _game.value=CardMatchingGame(24)
    }

    fun reset(){
        _game.value?.reset()
    }

    fun chooseCardAtIndex(i:Int){
        _game.value?.chooseCardAtIndex(i)
    }
    //将fragment中对game的操作移植到viewmodel中



    /*
    //存数据(viewmodel会存储数据，所以不用再存到文件中去)
    fun saveData() {
        try {
            val output = activity.openFileOutput(gameFile, AppCompatActivity.MODE_PRIVATE)
            ObjectOutputStream(output).use {
                it.writeObject(game)
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }
    //取数据
    fun loadData(): CardMatchingGame? {
        try {
            val input = activity?.openFileInput(gameFile)
            val objectInputStream =  ObjectInputStream(input)
            val game = objectInputStream.readObject() as CardMatchingGame
            objectInputStream.close()
            input?.close()
            return game
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }*/

}