package android.tom.playground.recyclerviewfilter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.tom.playground.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom.saju on 11/6/2018.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Contacts> contactList;
    private List<Contacts> contactListFiltered;
    public ContactsAdapter(Context context,List<Contacts> contactList) {
        this.context = context;
        this.contactList = contactList;
        contactListFiltered = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Contacts contacts = contactListFiltered.get(position);
        holder.name.setText(contacts.name);
        holder.number.setText(contacts.phone);
        Picasso.with(context).load(contacts.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString  = charSequence.toString();
                if(charString.isEmpty()){
                    contactListFiltered = contactList;
                }else{
                    List<Contacts> filteredList = new ArrayList<>();
                    for(Contacts contact:contactList){
                        if(contact.getName().toLowerCase().contains(charString)){
                            filteredList.add(contact);
                        }
                    }
                    contactListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values =contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                contactListFiltered = (ArrayList<Contacts>) filterResults.values;
                notifyDataSetChanged();
            }


        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView number;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.phone);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}
