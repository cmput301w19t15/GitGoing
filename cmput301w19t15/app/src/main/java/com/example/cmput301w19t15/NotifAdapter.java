package com.example.cmput301w19t15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

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
    public void onBindViewHolder (NotifAdapter.NotifViewHolder holder, int position) {
        Notification currentNotif = mNotifList.get(position);

        String notifType = "";
        if (currentNotif.getType().equals("requested")) {
            notifType = "Request on your book";
        }
        else if (currentNotif.getType().equals("accepted")) {
            notifType = "Your request has been accepted";
        }
        String fromUser = FirebaseDatabase.getInstance().getReference().child("users")
                .child(currentNotif.getNotifyFromID()).child("email").toString();
        String bookTitle = FirebaseDatabase.getInstance().getReference().child("books")
                .child(currentNotif.getBookID()).child("title").toString();

        holder.mTextViewType.setText(notifType);
        holder.mTextViewUser.setText(fromUser);
        holder.mTextViewBook.setText(bookTitle);
    }

    @Override
    public int getItemCount() {
        return mNotifList.size();
    }
    public class NotifViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewType, mTextViewUser, mTextViewBook;

        public NotifViewHolder (View itemView) {
            super(itemView);
            mTextViewType = itemView.findViewById(R.id.NotifType);
            mTextViewUser = itemView.findViewById(R.id.NotifUserEmail);
            mTextViewBook = itemView.findViewById(R.id.NotifBookTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
