package android.tom.playground.notes;

import android.content.Context;
import android.tom.playground.helper.DBHelper;

/**
 * Created by tom.saju on 12/15/2017.
 */

public class NoteEditorController implements INoteEditorController {

    Context context;
    INoteEditorView view;

    public NoteEditorController(Context context,INoteEditorView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public Note getNoteForId(String id) {
        Note note ;
        DBHelper dbHelper = new DBHelper(context,DBHelper.DB_NAME,null,DBHelper.DB_VERSION);
        note = dbHelper.getNoteForId(Integer.parseInt(id));
        return note;
    }

    @Override
    public void saveNote(Note note) {
        DBHelper dbHelper = new DBHelper(context,DBHelper.DB_NAME,null,DBHelper.DB_VERSION);
        dbHelper.insertintoNotesTable(note);
        ((INoteEditorView)view).onNoteInsertSuccess();
    }
}
