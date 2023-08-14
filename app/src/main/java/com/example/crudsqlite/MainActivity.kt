package com.example.crudsqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudsqlite.room.Constant
import com.example.crudsqlite.room.Note
import com.example.crudsqlite.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var button_create: Button
    private lateinit var list_note: RecyclerView
    val db by lazy { NoteDB(this) }

    lateinit var noteAdapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListener()
        setupRecylerView()
    }

    override fun onStart(){
        super.onStart()
        loadNote()

    }
    fun loadNote(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNotes()
            Log.d("MainActivity", "dbResponse: $notes")
            withContext(Dispatchers.Main){
                noteAdapter.setData(notes)
            }
        }
    }

    fun setupListener(){
        button_create = findViewById<Button>(R.id.button_create)
        button_create.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
            intentEdit(Constant.TYPE_CREATE, 0)
        }
    }

    fun intentEdit( intentType: Int,noteId: Int){
        startActivity(
            Intent(
                applicationContext, EditActivity::class.java)
                .putExtra("intent_type", intentType)
                .putExtra("intent_id", noteId)

        )
    }

    fun setupRecylerView(){
        list_note = findViewById(R.id.list_note)
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.OnAdapterListener{
            override fun onClick(note: Note) {
                intentEdit( Constant.TYPE_READ, note.id)
            }

            override fun onUpdate(note: Note) {
                intentEdit( Constant.TYPE_UPDATE, note.id)
            }

            override fun onDelete(note: Note) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    loadNote()
                }
            }
        })
        list_note.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }
}