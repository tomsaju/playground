package android.tom.playground.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.tom.playground.notes.Note;

import java.util.ArrayList;

/**
 * Created by tom.saju on 12/15/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db = this.getWritableDatabase();
    public static String DB_NAME = "playground_DB";
    public static int DB_VERSION = 1;

    //Table names
    public static String TABLE_NOTES = "Notes_Table";

    //fields for table_notes
    public static final String NOTE_ID = "ID";
    public static final String NOTE_TITLE = "Title";
    public static final String NOTE_CONTENT = "Content";
    public static final String NOTE_DATE = "Date";



    //Table creation statements
    public static final String NOTES_TABLE_CREATE_STATEMENT = "CREATE TABLE "+TABLE_NOTES+" ( "+NOTE_ID +" INTEGER ,"+
            NOTE_TITLE +" TEXT ,"+
            NOTE_CONTENT + " TEXT ,"+
            NOTE_DATE + " TEXT );";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NOTES_TABLE_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<Note> getAllNotes(){
        db = this.getWritableDatabase();
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor;
        cursor = db.query(TABLE_NOTES,null,null,null,null,null,null);
        if(cursor.moveToNext()){
            Note note = new Note();
            note.setId(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_ID)));
            note.setContent(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_CONTENT)));
            note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_TITLE)));
            note.setEdittedDate(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_DATE)));
            notes.add(note);
        }
        return notes;
    }

    public void insertintoNotesTable(Note note){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_ID,note.getId());
        cv.put(NOTE_CONTENT,note.getContent());
        cv.put(NOTE_DATE,note.getEdittedDate());
        cv.put(NOTE_TITLE,note.getTitle());

        db.insert(TABLE_NOTES,null,cv);

        db.close();
    }
}
