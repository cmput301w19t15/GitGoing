package com.example.cmput301w19t15;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {
    private Context mContext;
    private ArrayList<Notification> mNotifList;
    private NotifAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        /**
         * returns position of item click
         * @param position the position
         */
        void onItemClick(int position);
    }
    /**
     * Sets on item click listener
     * @param  listener the listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * Instantiates a new Notification adapter
     * @param context the context used to call this adapter
     * @param notifList the notification list that will be displayed
     */
    public NotifAdapter(Context context, ArrayList<Notification> notifList) {
        mContext = context;
        mNotifList = notifList;
    }

    @Override
    public NotifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new NotifViewHolder(v);
    }

    @Override
    public void onBindViewHolder (final NotifAdapter.NotifViewHolder holder, int position) {
        Notification currentNotif = mNotifList.get(position);

        String notifType = "";
        if (currentNotif.getType().equals("requested")) {
            notifType = "Request on your book";
        }
        else if (currentNotif.getType().equals("accepted")) {
            notifType = "Your request has been accepted";
        }
        holder.mTextViewType.setText(notifType);



        //get user email from ID
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(currentNotif.getNotifyFromID()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            String name = dataSnapshot.getValue().toString();
                            holder.mTextViewUser.setText(name);
                            //Log.e("TAG", "" + name[0]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });


        //get book title email from ID
        FirebaseDatabase.getInstance().getReference().child("books")
                .child(currentNotif.getBookID()).child("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            String title = dataSnapshot.getValue().toString();
                            holder.mTextViewBook.setText(title);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", " it's null.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("onCancelled", " cancelled");
            }
        });
        String bookTitle = FirebaseDatabase.getInstance().getReference().child("books")
                .child(currentNotif.getBookID()).child("title").toString();

    }

    @Override
    public int getItemCount() {
        return mNotifList.size();
    }

    public class NotifViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewType, mTextViewUser, mTextViewBook, mTextViewRead;

        public NotifViewHolder (View itemView) {
            super(itemView);
            mTextViewType = itemView.findViewById(R.id.NotifType);
            mTextViewUser = itemView.findViewById(R.id.NotifUserEmail);
            mTextViewBook = itemView.findViewById(R.id.NotifBookTitle);
            mTextViewRead = itemView.findViewById(R.id.NotifUnread);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                        for (Notification notif : mNotifList) {
                            if(notif.getRead()) {
                                mTextViewRead.setVisibility(View.INVISIBLE);
                            }
                            else {
                                //mTextViewRead.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });
        }
    }
}
