package com.example.catchtheball

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LevelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        val btnEasy: Button = findViewById(R.id.btnEasy)
        val btnMedium: Button = findViewById(R.id.btnMedium)
        val btnHard: Button = findViewById(R.id.btnHard)

        btnEasy.setOnClickListener { startGame("Easy") }
        btnMedium.setOnClickListener { startGame("Medium") }
        btnHard.setOnClickListener { startGame("Hard") }
    }

    private fun startGame(level: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("LEVEL", level)
        startActivity(intent)
    }
}
