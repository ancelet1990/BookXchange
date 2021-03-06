package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.androidbootcamp.bookxchange.Adapter.ManagePostsAdapter;
import net.androidbootcamp.bookxchange.Adapter.ManageSoldPostsAdapter;
import net.androidbootcamp.bookxchange.model.Book;
import net.androidbootcamp.bookxchange.util.DividerItemDecoration;
import net.androidbootcamp.bookxchange.util.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class ManagePostsActivity extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView1, recyclerView2;
    ArrayList<Book> list, list2;
    ManagePostsAdapter postsAdapter;
    ManageSoldPostsAdapter soldPostsAdapter;
    FirebaseUser fuser;
    String uid;
    private static final int VERTICAL_ITEM_SPACE = 48;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        recyclerView1 = findViewById(R.id.recyclerActive);
        recyclerView2 = findViewById(R.id.recyclerSold);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        //add ItemDecoration
        recyclerView1.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView1.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.drawable_divider));
        recyclerView2.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView2.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.drawable_divider));

        list = new ArrayList<Book>();
        list2 = new ArrayList<Book>();

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        uid = fuser.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("books");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                list2.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Book b = dataSnapshot1.getValue(Book.class);
                    if (uid.equals(b.getUid()) && !b.getBookIsSold()) {
                        list.add(b);
                    }
                    if (uid.equals(b.getUid()) && b.getBookIsSold()) {
                        list2.add(b);
                    }
                }
                postsAdapter = new ManagePostsAdapter(ManagePostsActivity.this, list);
                recyclerView1.setAdapter(postsAdapter);
                soldPostsAdapter = new ManageSoldPostsAdapter(ManagePostsActivity.this, list2);
                recyclerView2.setAdapter(soldPostsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManagePostsActivity.this, "Oops.... Something is wrong", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
