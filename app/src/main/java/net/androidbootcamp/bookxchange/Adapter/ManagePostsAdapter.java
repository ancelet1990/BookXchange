package net.androidbootcamp.bookxchange.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import net.androidbootcamp.bookxchange.R;
import net.androidbootcamp.bookxchange.model.Book;

import java.util.ArrayList;

public class ManagePostsAdapter extends RecyclerView.Adapter<ManagePostsAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Book> aBook;
    private DatabaseReference database;


    public ManagePostsAdapter(Context context, ArrayList<Book> aBook)
    {
        this.context = context;
        this.aBook = aBook;
    }
    //just to commint and send
    @NonNull
    @Override
    public ManagePostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_posts_card_view, parent, false);



        return new ManagePostsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ManagePostsAdapter.ViewHolder holder, int position)
    {
        final Book book = aBook.get(position);

        holder.isbn.setText("ISBN: " + book.getIsbn());
        holder.title.setText("Title: " + book.getTitle());
        holder.author.setText("Author: " + book.getAuthor());
        holder.condition.setText("Condition: " + book.getCondition());
        holder.price.setText("Price: $" + book.getPrice());
        Picasso.with(context).load(book.getPhotoURL()).fit().into(holder.bPic);

        database = FirebaseDatabase.getInstance().getReference();


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setBookIsSold(true);
                database.child("Books").child(book.getBookID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("bookIsSold").setValue(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
        public TextView isbn, title, author, condition, price, user, label1, label2;
        public ImageView bPic;
        public Button btn;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            label1 = itemView.findViewById(R.id.txtLabel);
            label2 = itemView.findViewById(R.id.txtLabel2);
            isbn = itemView.findViewById(R.id.txtISBN);
            title = itemView.findViewById(R.id.txtTitle);
            author = itemView.findViewById(R.id.txtAuthor);
            price = itemView.findViewById(R.id.txtPrice);
            condition = itemView.findViewById(R.id.txtCondition);
            bPic = itemView.findViewById(R.id.imgBookPhoto);
            btn = itemView.findViewById(R.id.btnSold);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("Set as sold: ", "button clicked");
                }
            });
        }
    }
}
