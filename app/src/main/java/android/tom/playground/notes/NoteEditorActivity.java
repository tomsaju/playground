package android.tom.playground.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.tom.playground.R;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
        noteEditorController = new NoteEditorController(getBaseContext(),this);


        if(getIntent().getExtras()==null){

        }else{
            id=getIntent().getStringExtra("ID");
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_menu, menu);//Menu Resource, Menu
        return true;
    }


    void saveNote(){
    if(title.getText().toString()!=null&&!title.getText().toString().isEmpty()||
        (content.getText().toString()!=null&&!content.getText().toString().isEmpty())){

        String titleText = title.getText().toString();
        String contentText = content.getText().toString();
        Note noteToSave = new Note();
        noteToSave.setTitle(titleText);
        noteToSave.setContent(contentText);
       // noteEditorController.saveNote();
    }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:

                return true;
            case R.id.delete:
                Toast.makeText(getApplicationContext(),"delete",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onNoteInsertSuccess() {

    }
}
