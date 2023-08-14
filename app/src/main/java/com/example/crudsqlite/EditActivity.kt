package com.example.crudsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.crudsqlite.room.Constant
import com.example.crudsqlite.room.Note
import com.example.crudsqlite.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private lateinit var button_save: Button
    private lateinit var edit_title: EditText
    private lateinit var edit_note: EditText
    private val db by lazy {NoteDB(this)}
    private var noteId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        button_save = findViewById<Button>(R.id.button_save)
        edit_title = findViewById<EditText>(R.id.edit_title)
        edit_note = findViewById<EditText>(R.id.edit_note)
        setupView()
        setupListener()

    }

    fun setupView(){
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_CREATE -> {

            }
        }
    }

    fun setupListener(){

        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    Note(0, edit_title.text.toString(), edit_note.text.toString())
                )
                finish()
            }
        }

    }

    fun getNote(){
        noteId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notesList = db.noteDao().getNote(noteId)
            if (notesList.isNotEmpty()) {
                val notes = notesList[0]
                edit_title.setText(notes.title)
                edit_note.setText(notes.note)
            }
        }
    }


}