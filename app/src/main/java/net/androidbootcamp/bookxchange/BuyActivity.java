package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.androidbootcamp.bookxchange.Adapter.BuyingAdapter;
import net.androidbootcamp.bookxchange.model.Book;
import net.androidbootcamp.bookxchange.model.User;

import java.util.ArrayList;

public class BuyActivity extends AppCompatActivity
{
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Book> list;
    BuyingAdapter buyingAdapter;
    User user;
    EditText search_books;
    Book book;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Book>();

        reference = FirebaseDatabase.getInstance().getReference().child("Books");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Book b = dataSnapshot1.getValue(Book.class);
                    if (!b.getBookIsSold()) {
                        list.add(b);
                    }
                }
                buyingAdapter = new BuyingAdapter(BuyActivity.this, list, user);
                recyclerView.setAdapter(buyingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(BuyActivity.this, "Oops.... Something is wrong", Toast.LENGTH_SHORT)
                     .show();
            }
        });

//        search_books = findViewById(R.id.search_books);
//        search_books.addTextChangedListener(new TextWatcher()
//        {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after)
//            {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
//            {
//                searchBooks(charSequence.toString().toLowerCase());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s)
//            {
//
//            }
//        });
    }

//    private void searchBooks(String s)
//    {
//        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
//        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
//                                      .startAt(s)
//                                      .endAt(s + "\uf8ff");
//
//        query.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                list.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Book book = snapshot.getValue(Book.class);
//
//                    assert book != null;
//                    assert fuser != null;
//                    if (!user.getId().equals(fuser.getUid())) {
//                        list.add(book);
//                    }
//                }
//
//                buyingAdapter = new BuyingAdapter(new BuyActivity().this, list, user);
//                recyclerView.setAdapter(buyingAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
//    }
//
//    private void readBooks()
//    {
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//
//        reference.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (search_books.getText().toString().equals(""))
//                {
//                    list.clear();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
//                    {
//                        Book book = snapshot.getValue(Book.class);
//
//                        assert book != null;
//                        assert firebaseUser != null;
//                        if (!book.getBookID().equals(book.getBookID()))
//                        {
//                            list.add(book);
//                        }
//                    }
//
//                    buyingAdapter = new UserAdapter(getContext(), list, false);
//                    recyclerView.setAdapter(buyingAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
//    }

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
                Intent intent2 = new Intent(this, BuyActivity.class);
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
