package android.tom.playground.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;
import android.widget.EditText;

public class NoteEditorActivity extends AppCompatActivity implements INoteEditorView {

    EditText title,content;
    Note note;
    String id;
    INoteEditorController noteEditorController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);


        title = (EditText) findViewById(R.id.noteTitle);
        content = (EditText) findViewById(R.id.noteContent);
        noteEditorController = new NoteEditorController(this);


        if(getIntent().getExtras()==null){
            return;
        }else{
            id=getIntent().getStringExtra("ID");
        }




    }
}
