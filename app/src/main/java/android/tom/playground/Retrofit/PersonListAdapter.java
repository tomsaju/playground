package android.tom.playground.Retrofit;

import android.content.Context;
import android.tom.playground.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tom.saju on 2/2/2018.
 */

public class PersonListAdapter extends BaseAdapter {

    Context context;
    List<Person> personList;


    public PersonListAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }


    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int i) {
        return personList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.personlist_layout, viewGroup, false);
        }
        TextView name = (TextView)  view.findViewById(R.id.userName);
        TextView email = (TextView) view.findViewById(R.id.userEmail);
        ImageView image = (ImageView) view.findViewById(R.id.thumb);

        name.setText(personList.get(i).getName().getFirst());
        email.setText(personList.get(i).getEmail());

        Picasso.with(context).load(personList.get(i).getPicture().getThumb()).into(image);


        return view;
    }
}
