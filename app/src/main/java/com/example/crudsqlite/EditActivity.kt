package com.example.crudsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.crudsqlite.room.Note
import com.example.crudsqlite.room.NoteDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private lateinit var button_save: Button
    private lateinit var edit_title: EditText
    private lateinit var edit_note: EditText
    val db by lazy {NoteDB(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupListener()
    }

    fun setupListener(){
        button_save = findViewById<Button>(R.id.button_save)
        edit_title = findViewById<EditText>(R.id.edit_title)
        edit_note = findViewById<EditText>(R.id.edit_note)
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    Note(0, edit_title.text.toString(), edit_note.text.toString())
                )
                finish()
            }
        }

    }

}