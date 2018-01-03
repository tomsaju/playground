package android.tom.playground.notes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.tom.playground.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tom.saju on 1/3/2018.
 */

public class NoteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Note> notelist;

    public NoteListAdapter(Context context, ArrayList<Note> notelist) {
        this.context = context;
        this.notelist = notelist;
    }

    @Override
    public int getCount() {
        return notelist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         view = inflater.inflate(R.layout.notelistitemlayout,viewGroup,false);
        TextView title  =(TextView) view.findViewById(R.id.noteTitle);
        TextView content = (TextView) view.findViewById(R.id.noteContent);
        TextView date = (TextView) view.findViewById(R.id.dateNote);

        title.setText(notelist.get(i).getTitle());
        content.setText(notelist.get(i).getContent());
        date.setText(notelist.get(i).getEdittedDate());

        return view;
    }
}
