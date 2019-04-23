package net.androidbootcamp.bookxchange.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.androidbootcamp.bookxchange.Adapter.BrowseAdapter;

import net.androidbootcamp.bookxchange.R;
import net.androidbootcamp.bookxchange.model.Book;
import net.androidbootcamp.bookxchange.model.User;

import java.util.ArrayList;

public class BrowseFragment extends Fragment {

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Book> mBook;
    BrowseAdapter browseAdapter;
    FirebaseUser fuser;
    String uid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        recyclerView = view.findViewById(R.id.buyRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        mBook = new ArrayList<Book>();

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        uid = fuser.getUid();

        reference = FirebaseDatabase.getInstance().getReference().child("books");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Book b = dataSnapshot1.getValue(Book.class);
                    if (!b.getBookIsSold() && !uid.equals(b.getUid())) {
                        mBook.add(b);
                    }
                }
                browseAdapter = new BrowseAdapter(getContext(), mBook);
                recyclerView.setAdapter(browseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        return view;
    }
}
