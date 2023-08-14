package com.example.crudsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var button_create: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListener()
    }

    fun setupListener(){
        button_create = findViewById<Button>(R.id.button_create)
        button_create.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))

        }
    }
}