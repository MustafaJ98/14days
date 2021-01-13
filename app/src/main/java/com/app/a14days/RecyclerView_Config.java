package com.app.a14days;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.app.a14days.Timer_14days.calculateRemainingTime;

public class RecyclerView_Config {
    private Context myContext;
    private ContactAdapter mContactAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Contact> contacts, List<String> keys){
        myContext = context;
        mContactAdapter = new ContactAdapter(contacts, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mContactAdapter);
    }

    class ContactItemView extends RecyclerView.ViewHolder {
        private TextView myContactName;
        private TextView myContactDate;
        private TextView myRemainingDays;

        private String key;

        public ContactItemView(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(myContext).
                    inflate(R.layout.contact_list_item, parent, false));
            myContactName = (TextView) itemView.findViewById(R.id.ContactName);
            myContactDate = (TextView) itemView.findViewById(R.id.ContactDate);
            myRemainingDays = (TextView) itemView.findViewById(R.id.RemainingDays);
        }

        public void bind(Contact contact, String key){
            myContactName.setText(contact.getContactName());
            myContactDate.setText(contact.getContactDate());
            String remainingDays = calculateRemainingTime(contact.getContactDate());
            myRemainingDays.setText(remainingDays);
            this.key = key;
        }

        private String calculateRemainingDays() {
            return "14";
        }
    }
    class ContactAdapter extends RecyclerView.Adapter<ContactItemView>{
        private List<Contact> contacts;
        private List<String> mKeys;

        public ContactAdapter(List<Contact> contacts, List<String> mKeys) {
            this.contacts = contacts;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public ContactItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContactItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactItemView holder, int position) {
        holder.bind(contacts.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }
    }
}
