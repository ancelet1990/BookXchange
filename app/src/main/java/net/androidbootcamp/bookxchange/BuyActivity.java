package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.androidbootcamp.bookxchange.Adapter.BuyingAdapter;

import java.util.ArrayList;

public class BuyActivity extends AppCompatActivity
{
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Book> list;
    BuyingAdapter buyingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Book>();

        reference = FirebaseDatabase.getInstance().getReference().child("books");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Book b = dataSnapshot.getValue(Book.class);
                    list.add(b);
                }
                buyingAdapter = new BuyingAdapter(BuyActivity.this, list);
                recyclerView.setAdapter(buyingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(BuyActivity.this, "Oops.... Something is wrong", Toast.LENGTH_SHORT)
                     .show();
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
                Intent intent2 = new Intent(this, BuyActivity.class);
                this.startActivity(intent2);
                break;
            case R.id.messages:
                Intent intent3 = new Intent(this, MessageActivity.class);
                this.startActivity(intent3);
                break;
            case R.id.posts:
                Intent intent4 = new Intent(this, PostActivity.class);
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
