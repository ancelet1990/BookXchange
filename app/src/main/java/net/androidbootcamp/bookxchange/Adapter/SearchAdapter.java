package net.androidbootcamp.bookxchange.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.androidbootcamp.bookxchange.BookDetailActivity;
import net.androidbootcamp.bookxchange.MessageActivity;
import net.androidbootcamp.bookxchange.R;
import net.androidbootcamp.bookxchange.model.Book;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static final String TAG = "SearchAdapter";
    private Context mContext;
    private ArrayList<Book> mBooks;

    public SearchAdapter(Context context, ArrayList<Book> aBook) {
        this.mContext = context;
        this.mBooks = aBook;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);

        closeKeyboard(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = mBooks.get(position);

        holder.isbn.setText(book.getIsbn());
        holder.title.setText(book.getTitle());
        holder.author.setText(book.getAuthor());
        holder.condition.setText("Condition: " + book.getCondition());
        holder.price.setText("$" + book.getPrice());
        Picasso.with(mContext).load(book.getPhotoURL()).fit().into(holder.bPic);
        holder.btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", book.getUid());
                mContext.startActivity(intent);
            }
        });
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mContext, BookDetailActivity.class);
                intent1.putExtra("bookid", book.getBookID());
                mContext.startActivity(intent1);
            }
        });
    }

    private void closeKeyboard(View view) {

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView isbn, title, author, condition, price;
        public ImageView bPic;
        public Button btnMessage, btnDetails;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            isbn = itemView.findViewById(R.id.txtISBN);
            title = itemView.findViewById(R.id.txtTitle);
            author = itemView.findViewById(R.id.txtAuthor);
            price = itemView.findViewById(R.id.txtPrice);
            condition = itemView.findViewById(R.id.txtCondition);
            bPic = itemView.findViewById(R.id.imgBookPhoto);
            btnMessage = itemView.findViewById(R.id.btnMessage);
            btnDetails = itemView.findViewById(R.id.btnDetails);

        }
    }
}
