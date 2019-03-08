package com.example.cmput301w19t15;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.helper.Layer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context mContext;
    private ArrayList<Book> mBookList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public void BookAdapter(Context context, ArrayList<Book> bookList) {
        mContext = context;
        mBookList = bookList;
    }

    @Override
    public BookViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from((mContext)).inflate(R.layout.activity_book_info, parent, false);
        return new BookViewHolder(v);
    }


    @Override
    public void onBindViewHolder(BookViewHolder holder, int postion) {
        Book currentBook = mBookList.get((postion));

        String bookTitle = currentBook.getTitle();
        String bookauthor = currentBook.getAuthor();
        String bookISBN = currentBook.getISBN();
        String bookPhoto = currentBook.getPhoto();
        String ownerEmail = currentBook.getOwnerEmail();
        String ownerID = currentBook.getOwnerID();
        String bookID = currentBook.getBookID();
        Date borrowDate = currentBook.getDate();
        String bookStatus = currentBook.getStatus();
        String borrowerID = currentBook.getBorrowerID();

        holder.mTextViewTitle.setText(bookTitle);
        holder.mTextViewAuthor.setText(bookauthor);
        holder.mTextViewISBN.setText(bookISBN);
        Bitmap bookImage = ConvertPhoto.convert(bookPhoto);
        holder.mImageView.setImageBitmap(bookImage);
        //holder.mTextViewPhoto.setText(bookPhoto);
        holder.mTextViewOwnerEmail.setText(ownerEmail);
        holder.mTextViewOwnerID.setText(ownerID);
        holder.mTextViewBookID.setText(bookID);
        holder.mTextViewStatus.setText(bookStatus);
        holder.mTextViewBorrowerID.setText(borrowerID);
    }


    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewTitle, mTextViewAuthor, mTextViewISBN, mTextViewPhoto, mTextViewOwnerEmail,
                mTextViewOwnerID, mTextViewBookID, mTextViewDate, mTextViewStatus, mTextViewBorrowerID;

        public BookViewHolder (View bookView) {
            super(bookView);
            mImageView = bookView.findViewById(R.id.imageView);
            //load url image to Imageview
            //load image from fireabse to ImageView
            mTextViewTitle = bookView.findViewById(R.id.textViewBookTitle);
            mTextViewAuthor = bookView.findViewById(R.id.textViewAuthor);
            mTextViewISBN = bookView.findViewById(R.id.textViewISBN);
            mTextViewOwnerEmail = bookView.findViewById(R.id.textViewOwnerEmail);
            //mTextViewDate = bookView.findViewById(R.id.);
            mTextViewStatus = bookView.findViewById(R.id.textViewStatus);

            bookView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (mListener != null){
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
