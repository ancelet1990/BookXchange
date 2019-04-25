package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import net.androidbootcamp.bookxchange.Adapter.BrowseAdapter;
import net.androidbootcamp.bookxchange.model.Book;

import java.util.ArrayList;

public class BookDetailActivity extends AppCompatActivity {
    private TextView isbn, title, author, condition, price, edition, tags;
    private String bookID;
    private ImageView bookImage;
    private Button btnMessage;
    private Book book;

    public BookDetailActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        book = new Book();
        title = findViewById(R.id.tvTitleDetail);
        isbn = findViewById(R.id.tvISBNDetail);
        author = findViewById(R.id.tvAuthorDetail);
        condition = findViewById(R.id.tvConditionDetail);
        price = findViewById(R.id.tvPriceDetail);
        bookImage = findViewById(R.id.ivBookDetail);
        btnMessage = findViewById(R.id.btnMessageDetail);
        edition = findViewById(R.id.tvEditionDetail);
        tags = findViewById(R.id.tvTagsDetail);


        //Intent intent = getIntent();
       // book_ID = intent.getStringExtra("bookid");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bookID = bundle.getString("bookid");
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child("books")
                .orderByKey()
                .equalTo(bookID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot singleSnapshot = dataSnapshot.getChildren().iterator().next();
                if(singleSnapshot != null){
                    book = singleSnapshot.getValue(Book.class);
                    Log.d("XXX ", "onDataChange: found the book: " + book.getTitle());

                    title.setText(book.getTitle());
                    isbn.setText("ISBN: " + book.getIsbn());
                    author.setText(book.getAuthor());
                    condition.setText("Condition: " + "\t" + book.getCondition());
                    price.setText("Asking Price: $"+ book.getPrice());
                    tags.setText("Tags: " + book.getTags());
                    Picasso.with(getApplicationContext()).load(book.getPhotoURL()).fit().into(bookImage);

                    String end = "";
                    int no = Integer.parseInt(book.getEdition());

                    if (no == 1) { end = "st"; }
                    if (no == 2) { end = "nd"; }
                    if  (no == 3) { end = "rd"; }
                    if (no > 3) { end = "th"; }
                    else { end = "";}

                    edition.setText(book.getEdition() + end + " Edition");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        reference = FirebaseDatabase.getInstance().getReference().child("books").child(book_ID);
//        reference.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Book b = dataSnapshot1.getValue(Book.class);
//                    if (b.getBookID() == book_ID) {
//                        book = b;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetailActivity.this, MessageActivity.class);
                intent.putExtra("userid", book.getUid());
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.account:
                Intent intent = new Intent(this, AccountActivity.class);
                this.startActivity(intent);
                break;
            case R.id.sell_books:
                Intent intent1 = new Intent(this, SellActivity.class);
                this.startActivity(intent1);
                break;
            case R.id.buy_books:
                Intent intent2 = new Intent(this, MainBuyActivity.class);
                this.startActivity(intent2);
                break;
            case R.id.messages:
                Intent intent3 = new Intent(this, MainActivity.class);
                this.startActivity(intent3);
                break;
            case R.id.posts:
                Intent intent4 = new Intent(this, ManagePostsActivity.class);
                this.startActivity(intent4);
                break;
            case R.id.help:
                Intent intent5 = new Intent(this, HelpActivity.class);
                this.startActivity(intent5);
                break;
            case R.id.about:
                Intent intent6 = new Intent(this, AboutActivity.class);
                this.startActivity(intent6);
                break;
        }
        return true;
    }
}
