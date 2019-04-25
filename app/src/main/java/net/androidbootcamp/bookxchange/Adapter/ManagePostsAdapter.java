package net.androidbootcamp.bookxchange.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import net.androidbootcamp.bookxchange.R;
import net.androidbootcamp.bookxchange.model.Book;

import java.util.ArrayList;

public class ManagePostsAdapter extends RecyclerView.Adapter<ManagePostsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> aBook;
    private DatabaseReference database;


    public ManagePostsAdapter(Context context, ArrayList<Book> aBook) {
        this.context = context;
        this.aBook = aBook;
    }

    //just to commint and send
    @NonNull
    @Override
    public ManagePostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.manage_posts_card_view, parent, false);


        return new ManagePostsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ManagePostsAdapter.ViewHolder holder, final int position) {
        final Book book = aBook.get(position);
        final ManagePostsAdapter.ViewHolder holder1 = holder;
        final int pos = position;

        if (book.getBookIsSold()) {
            holder1.btn.setVisibility(View.GONE);
        } else {
            holder1.btn.setVisibility(View.VISIBLE);
        }

        holder1.isbn.setText("ISBN: " + book.getIsbn());
        holder1.title.setText(book.getTitle());
        holder1.author.setText("Author: " + book.getAuthor());
        holder1.condition.setText("Condition: " + book.getCondition());
        holder1.price.setText("Price: $" + book.getPrice());
        Picasso.with(context).load(book.getPhotoURL()).fit().into(holder1.bPic);


        database = FirebaseDatabase.getInstance().getReference();


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aBook.remove(book);
                holder1.btn.setVisibility(View.GONE);
                holder1.sold.setVisibility(View.VISIBLE);
                database.child("books").child(book.getBookID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("bookIsSold").setValue(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                book.setBookIsSold(true);
                holder1.itemView.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return aBook.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView isbn, title, author, condition, price, user, label1, label2, sold;
        public ImageView bPic;
        public Button btn;

        public ViewHolder(@NonNull View itemView) {
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
            sold = itemView.findViewById(R.id.txtSOLD);

        }
    }
}
