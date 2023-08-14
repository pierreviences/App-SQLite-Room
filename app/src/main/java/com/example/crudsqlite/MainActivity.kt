package com.example.crudsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.crudsqlite.room.Note
import com.example.crudsqlite.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var button_create: Button
    val db by lazy { NoteDB(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListener()
    }

    override fun onStart(){
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNotes()
            Log.d("MainActivity", "dbResponse: $notes")
        }
    }

    fun setupListener(){
        button_create = findViewById<Button>(R.id.button_create)
        button_create.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))

        }
    }
}