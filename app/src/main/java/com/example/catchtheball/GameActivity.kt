package com.example.catchtheball

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get selected level
        val level = intent.getStringExtra("LEVEL") ?: "Easy"

        // Create GameView and set initial speed based on level
        val gameView = GameView(this)

        when (level) {
            "Easy" -> {
                gameView.setInitialSpeed(8f, 12f)
            }
            "Medium" -> {
                gameView.setInitialSpeed(12f, 16f)
            }
            "Hard" -> {
                gameView.setInitialSpeed(16f, 20f)
            }
        }

        title = "Game - $level"
        setContentView(gameView)
    }
}
