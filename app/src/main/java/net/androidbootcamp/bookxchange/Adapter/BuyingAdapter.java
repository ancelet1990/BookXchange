package net.androidbootcamp.bookxchange.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.androidbootcamp.bookxchange.MessageActivity;
import net.androidbootcamp.bookxchange.R;
import net.androidbootcamp.bookxchange.model.Book;
import net.androidbootcamp.bookxchange.model.User;

import java.util.ArrayList;

public class BuyingAdapter extends RecyclerView.Adapter<BuyingAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Book> aBook;
    private User user;

    public BuyingAdapter(Context context, ArrayList<Book> aBook, User user)
    {
        this.context = context;
        this.aBook = aBook;
        this.user = user;
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
        holder.user.setText(book.getUid());
        Picasso.with(context).load(book.getPhotoURL()).fit().into(holder.bPic);
        holder.btnMessage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", book.getUid());
                context.startActivity(intent);
            }
        });

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
        Button btnMessage;

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
            btnMessage = itemView.findViewById(R.id.btnMessage);

        }
    }
}
