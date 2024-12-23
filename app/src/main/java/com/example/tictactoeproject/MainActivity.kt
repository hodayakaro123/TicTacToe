package com.example.tictactoeproject

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val board = MutableList(9) { "" }
    private var currentPlayer = "X"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout: GridLayout = findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener { onCellClicked(button, i) }
        }


        val playAgainButton: Button = findViewById(R.id.playAgainBtn)
        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(button: Button, index: Int) {
        if (board[index].isEmpty()) {
            board[index] = currentPlayer
            button.text = currentPlayer


            val result = checkForWinner()

            if (result != null) {
                if (result == "Tie") {
                    updateGameStatus("Tie")
                    displayPlayAgainButton()
                } else {
                    updateGameStatus(result)
                    displayPlayAgainButton()
                }
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateGameStatus("$currentPlayer's turn")
            }
        }
    }

    private fun checkForWinner(): String? {
        val winningCombinations = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )

        for (combination in winningCombinations) {
            val (a, b, c) = combination
            if (board[a] == board[b] && board[b] == board[c] && board[a] != "") {
                return board[a]
            }
        }

        if (!board.contains("")) {
            return "Tie"
        }

        return null
    }

    private fun updateGameStatus(status: String) {
        val gameStatusTextView: TextView = findViewById(R.id.PlayerTurn)
        if (status == "Tie") {
            gameStatusTextView.text = "It's a draw!"
        } else {
            gameStatusTextView.text = "$status's turn"
        }
    }

    private fun displayPlayAgainButton() {
        val playAgainButton: Button = findViewById(R.id.playAgainBtn)
        playAgainButton.visibility = View.VISIBLE
    }

    private fun resetGame() {
        board.clear()
        board.addAll(MutableList(9) { "" })

        val gridLayout: GridLayout = findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = ""
        }

        updateGameStatus("Player X's Turn")
        val playAgainButton: Button = findViewById(R.id.playAgainBtn)
        playAgainButton.visibility = View.GONE
    }
}
