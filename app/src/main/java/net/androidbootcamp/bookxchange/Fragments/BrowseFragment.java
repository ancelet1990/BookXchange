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
import net.androidbootcamp.bookxchange.util.DividerItemDecoration;
import net.androidbootcamp.bookxchange.util.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class BrowseFragment extends Fragment {

    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<Book> mBook;
    private BrowseAdapter browseAdapter;
    private FirebaseUser fuser;
    private String uid;
    private static final int VERTICAL_ITEM_SPACE = 48;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        //initialize and set-up recycler views
        recyclerView = view.findViewById(R.id.buyRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //add ItemDecoration - divider lines between items in the recycler view
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.drawable_divider));

        //initialize ArrayList of type Book
        mBook = new ArrayList<Book>();

        //get current user and their user id
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        uid = fuser.getUid();

        //retrieve books from firebase reasltime database
        reference = FirebaseDatabase.getInstance().getReference().child("books");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    //if book has not been marked as sold AND the book was not posted by the current user, add book to ArrayList
                    Book b = dataSnapshot1.getValue(Book.class);
                    if (!b.getBookIsSold() && !uid.equals(b.getUid())) {
                        mBook.add(b);
                    }
                }
                //send context and arraylist of books to browse adapter
                browseAdapter = new BrowseAdapter(getContext(), mBook);
                recyclerView.setAdapter(browseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
