 /*
 * Version: 1.0
 *
 * Copyright 2019 TEAM GITGOING
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.example.cmput301w19t15;

 import android.support.v7.widget.RecyclerView;

 /**
  * Adapter for User class
  * @author Eisha
  * @version 1.0
  *
  */

 import android.content.Context;
 import android.support.v7.widget.RecyclerView;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.TextView;

 import java.util.ArrayList;

  /**
   * The type User adapter.
   * @reuse:  https://github.com/anjeshshrestha/uAlberta5Star
   */
 public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private Context mContext;
    private ArrayList<User> mUserList;
    private OnItemClickListener mListener;


     /**
      * The interface On item click listener.
      */
     public interface OnItemClickListener {
         /**
          * On item click.
          *
          * @param position the position
          */
         void onItemClick(int position);
    }

     /**
      * Sets on item click listener.
      *
      * @param listener the listener
      */
     public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


     /**
      * Instantiates a new User adapter.
      *
      * @param context  the context
      * @param userList the user list
      */
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
        //String userPhone = currentUser.getPhone();
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

     /**
      * The type User view holder.
      */
     public class UserViewHolder extends RecyclerView.ViewHolder {
         /**
          * The M text view name.
          */
         public TextView mTextViewName, /**
          * The M text view email.
          */
         mTextViewEmail, /**
          * The M text view id.
          */
         mTextViewID, /**
          * The M text view rating.
          */
         mTextViewRating;

         /**
          * Instantiates a new User view holder.
          *
          * @param itemView the item view
          */
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
