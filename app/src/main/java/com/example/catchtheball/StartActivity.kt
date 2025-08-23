package com.example.game

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.example.catchtheball.LevelActivity
import com.example.catchtheball.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val btnStartGame: Button = findViewById(R.id.btnStartGame)
        btnStartGame.setOnClickListener {

            val intent = Intent(this, LevelActivity::class.java)
            startActivity(intent)
        }
    }
}
