package com.example.androidsql;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> bookId, bookTitle, bookAuthor, bookPages;
    int position;
    Activity activity;
    Animation translateAnim;

    public BookAdapter(Activity activity, Context context, ArrayList<String> bookId, ArrayList<String> bookTitle, ArrayList<String> bookAuthor, ArrayList<String> bookPages) {
        this.activity = activity;
        this.context = context;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPages = bookPages;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.book_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, int position) {
        this.position = position;
        holder.bookIdTxt.setText(String.valueOf(bookId.get(position)));
        holder.bookTitleTxt.setText(String.valueOf(bookTitle.get(position)));
        holder.bookAuthorTxt.setText(String.valueOf(bookAuthor.get(position)));
        holder.bookPagesTxt.setText(String.valueOf(bookPages.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(bookId.get(position)));
                intent.putExtra("title", String.valueOf(bookTitle.get(position)));
                intent.putExtra("author", String.valueOf(bookAuthor.get(position)));
                intent.putExtra("pages", String.valueOf(bookPages.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.bookId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bookIdTxt, bookTitleTxt, bookAuthorTxt, bookPagesTxt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bookIdTxt = itemView.findViewById(R.id.bookIdTxt);
            this.bookTitleTxt = itemView.findViewById(R.id.bookTitleTxt);
            this.bookAuthorTxt = itemView.findViewById(R.id.bookAuthorTxt);
            this.bookPagesTxt = itemView.findViewById(R.id.bookPagesTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            // Animate Recycler view
            translateAnim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translateAnim);
        }
    }
}
