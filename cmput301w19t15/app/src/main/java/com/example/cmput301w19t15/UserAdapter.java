package com.example.cmput301w19t15;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private ArrayList<User> mUserList;
    private OnItemClickListener mListener;



    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public UserAdapter(Context context, ArrayList<User> userList) {
        mContext = context;
        mUserList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((mContext)).inflate(R.layout.user_item, parent, false);
        Log.e("TAG","Message");
        return new UserViewHolder(v);
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User currentUser = mUserList.get(position);

        String userName = currentUser.getName();
        String userEmail = currentUser.getEmail();
        String userPhone = currentUser.getPhone();
        String userID = currentUser.getUserID();
        //Float userRating = currentUser.getRating();

        holder.mTextViewName.setText(userName);
        holder.mTextViewEmail.setText(userEmail);
        holder.mTextViewID.setText(userID);
        //holder.mTextViewRating.setText(userRating.toString());
    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName, mTextViewEmail, mTextViewID, mTextViewRating;

        public UserViewHolder (View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.textViewUserName);
            mTextViewEmail = itemView.findViewById(R.id.textViewUserEmail);
            mTextViewID = itemView.findViewById(R.id.textViewUserID);

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
