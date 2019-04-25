package net.androidbootcamp.bookxchange.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.androidbootcamp.bookxchange.Adapter.SearchAdapter;
import net.androidbootcamp.bookxchange.R;
import net.androidbootcamp.bookxchange.model.Book;
import net.androidbootcamp.bookxchange.model.HitsList;
import net.androidbootcamp.bookxchange.model.HitsObject;
import net.androidbootcamp.bookxchange.util.DividerItemDecoration;
import net.androidbootcamp.bookxchange.util.ElasticSearchAPI;
import net.androidbootcamp.bookxchange.util.VerticalSpaceItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";
    private static final String BASE_URL = "http://35.238.42.238//elasticsearch/books/book/";
    private RecyclerView recyclerView;
    private ArrayList<Book> mBooks;
    private SearchAdapter searchAdapter;
    private static final int VERTICAL_ITEM_SPACE = 48;
    private FrameLayout mFrameLayout;
    private FirebaseUser fuser;
    private String uid;

    private EditText mSearchText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.searchRecycler);
        mSearchText = view.findViewById(R.id.search_books);
        mFrameLayout = view.findViewById(R.id.containerframe);

        //add ItemDecoration
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
        //or

        //or
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.drawable_divider));

        init();

        return view;
    }

    private void setupBooksList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(getActivity(), mBooks);
        recyclerView.setAdapter(searchAdapter);
    }

    private void init() {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        uid = fuser.getUid();

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    mBooks = new ArrayList<Book>();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ElasticSearchAPI searchAPI = retrofit.create(ElasticSearchAPI.class);

                    HashMap<String, String> headerMap = new HashMap<String, String>();
                    headerMap.put("Authorization", Credentials.basic("user", "K3ymFyi2WBKw"));

                    String searchString = "";

                    if (!mSearchText.equals("")) {
                        searchString = searchString + mSearchText.getText().toString() + "*";
                    }

                    Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchString);

                    call.enqueue(new Callback<HitsObject>() {
                        @Override
                        public void onResponse(Call<HitsObject> call, Response<HitsObject> response) {

                            HitsList hitsList = new HitsList();
                            String jsonResponse = "";
                            try {
                                Log.d(TAG, "onResponse: server response: " + response.toString());

                                if (response.isSuccessful()) {
                                    hitsList = response.body().getHits();
                                } else {
                                    jsonResponse = response.errorBody().string();
                                }

                                Log.d(TAG, "onResponse: hits: " + hitsList);

                                for (int i = 0; i < hitsList.getBookIndex().size(); i++) {
                                    Log.d(TAG, "onResponse: data: " + hitsList.getBookIndex().get(i).getBook().toString());
                                    if (!hitsList.getBookIndex().get(i).getBook().getUid().equals(uid) && !hitsList.getBookIndex().get(i).getBook().getBookIsSold()) {
                                        mBooks.add(hitsList.getBookIndex().get(i).getBook());
                                    }

                                }

                                Log.d(TAG, "onResponse: size: " + mBooks.size());

                                //setup the list of posts
                                setupBooksList();

                            } catch (NullPointerException e) {
                                Log.e(TAG, "onResponse: NullPointerException: " + e.getMessage());
                            } catch (IndexOutOfBoundsException e) {
                                Log.e(TAG, "onResponse: IndexOutOfBoundsException: " + e.getMessage());
                            } catch (IOException e) {
                                Log.e(TAG, "onResponse: IOException: " + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<HitsObject> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                            Toast.makeText(getActivity(), "search failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                return false;
            }
        });
    }

    public void viewPost(String bookID) {
        BookDetailFragment fragment = new BookDetailFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putString(getString(R.string.arg_book_id), bookID);
        fragment.setArguments(args);

        transaction.replace(R.id.containerframe, fragment, getString(R.string.fragment_view_book));
        transaction.addToBackStack(getString(R.string.fragment_view_book));
        transaction.commit();

        mFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
