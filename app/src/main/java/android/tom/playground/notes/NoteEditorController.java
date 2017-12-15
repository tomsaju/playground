package android.tom.playground.notes;

import android.content.Context;

/**
 * Created by tom.saju on 12/15/2017.
 */

public class NoteEditorController implements INoteEditorController {

    Context context;

    public NoteEditorController(Context context) {
        this.context = context;
    }

    @Override
    public Note getNoteForId(String id) {
        return null;
    }
}
