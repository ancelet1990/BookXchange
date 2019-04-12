package net.androidbootcamp.bookxchange.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.androidbootcamp.bookxchange.model.Book;
import net.androidbootcamp.bookxchange.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class BuyingAdapter extends RecyclerView.Adapter<BuyingAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Book> aBook;
    Bitmap bitmap;

    private AsyncTask mMyTask;
    public BuyingAdapter(Context context, ArrayList<Book> aBook)
    {
        this.context = context;
        this.aBook = aBook;
    }
//just to commint and send
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);

        return new BuyingAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        final Book book = aBook.get(position);

        holder.isbn.setText("ISBN: " + book.getIsbn());
        holder.title.setText("Title: " + book.getTitle());
        holder.author.setText("Author: " + book.getAuthor());
        holder.condition.setText("Condition: " + book.getCondition());
        holder.price.setText("Price: $" + book.getPrice());
        Picasso.with(context).load(book.getPhotoURL()).fit().into(holder.bPic);

    }

    @Override
    public int getItemCount()
    {
        return aBook.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView isbn, title, author, condition, price, user;
        public ImageView bPic;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            isbn = itemView.findViewById(R.id.txtISBN);
            title = itemView.findViewById(R.id.txtTitle);
            author = itemView.findViewById(R.id.txtAuthor);
            price = itemView.findViewById(R.id.txtPrice);
            condition = itemView.findViewById(R.id.txtCondition);
            bPic = itemView.findViewById(R.id.imgBookPhoto);
            user = itemView.findViewById(R.id.txtUser);

        }
    }
}
