package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "GameFragment"

class GameViewModel : ViewModel() {

    private var _score = 0
    val score: Int get() = _score
    private var currentWordCount = 0
    private lateinit var _currentScrambledWord: String
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    val currentScrambledWord: String get() = _currentScrambledWord

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++currentWordCount
            wordsList.add(currentWord)
        }
    }

    init {
        Log.d(TAG, "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "GameViewModel destroyed!")
    }

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }
}