package com.hoss.crud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BookRVAdapter extends RecyclerView.Adapter<BookRVAdapter.ViewHolder> {
private ArrayList<BookModal> bookAL;
private Context context;
int lastPos=-1;
private BookClickInterface bookClickInterface;

    public BookRVAdapter(ArrayList<BookModal> bookAL, Context context, BookClickInterface bookClickInterface) {
        this.bookAL = bookAL;
        this.context = context;
        this.bookClickInterface = bookClickInterface;
    }

    @NonNull
    @Override
    public BookRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.book_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookModal bookModal= bookAL.get(position);
        holder.title.setText(bookModal.getTitle());
     //   holder.price.setText(bookModal.getPrice());
        Picasso.get().load(bookModal.getImg()).into(holder.image);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookClickInterface.onClickBook(position);
            }
        });
    }

    public void setAnimation(View itemView,int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        }
    }

    @Override
    public int getItemCount() {
        return bookAL.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView title;
       private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.idTVTitle);
       //     price=itemView.findViewById(R.id.idTVPrice);
            image=itemView.findViewById(R.id.idImgv);
        }
    }

    public interface BookClickInterface{
        void onClickBook(int position);
    }
}
