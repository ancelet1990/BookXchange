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
import com.google.firebase.iid.FirebaseInstanceId;

import net.androidbootcamp.bookxchange.Adapter.UserAdapter;
import net.androidbootcamp.bookxchange.Notifications.Token;
import net.androidbootcamp.bookxchange.R;
import net.androidbootcamp.bookxchange.model.Chatlist;
import net.androidbootcamp.bookxchange.model.User;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends Fragment
{
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    //change this
    //    private List<String> usersList;
    private List<Chatlist> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        //new code
        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        //removed for performance
        //        reference = FirebaseDatabase.getInstance().getReference("Chats");
        //        reference.addValueEventListener(new ValueEventListener()
        //        {
        //            @Override
        //            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        //            {
        //                usersList.clear();
        //
        //                for (DataSnapshot snapshot : dataSnapshot.getChildren())
        //                {
        //                    Chat chat = snapshot.getValue(Chat.class);
        //
        //                    if (chat.getSender().equals(fuser.getUid()))
        //                    {
        //                        usersList.add(chat.getReceiver());
        //                    }
        //                    if (chat.getReceiver().equals(fuser.getUid()))
        //                    {
        //                        usersList.add(chat.getSender());
        //                    }
        //                }
        //
        //                readChats();
        //            }
        //
        //            @Override
        //            public void onCancelled(@NonNull DatabaseError databaseError)
        //            {
        //
        //            }
        //        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    //for notifications
    private void updateToken(String token)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(fuser.getUid()).setValue(token1);
    }

    private void chatList()
    {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    User user = snapshot.getValue(User.class);
                    for (Chatlist chatlist : usersList)
                    {
                        if (user.getId().equals(chatlist.getId()))
                        {
                            mUsers.add(user);
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    //removed for performance
    //    private void readChats()
    //    {
    //        mUsers = new ArrayList<>();
    //
    //        reference = FirebaseDatabase.getInstance().getReference("Users");
    //
    //        reference.addValueEventListener(new ValueEventListener()
    //        {
    //            @Override
    //            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
    //            {
    //                mUsers.clear();
    //
    //                for (DataSnapshot snapshot : dataSnapshot.getChildren())
    //                {
    //                    User user = snapshot.getValue(User.class);
    //
    //                    //display 1 user from chats
    //                    for (String id : usersList)
    //                    {
    //                        if (user.getId().equals(id))
    //                        {
    //                            if (mUsers.size() != 0)
    //                            {
    //                                for (User user1 : mUsers)
    //                                {
    //                                    if (!user.getId().equals(user1.getId()))
    //                                    {
    //                                        mUsers.add(user);
    //                                    }
    //                                }
    //                            } else
    //                            {
    //                                mUsers.add(user);
    //                            }
    //                        }
    //                    }
    //                }
    //
    //                userAdapter = new UserAdapter(getContext(), mUsers, true);
    //                recyclerView.setAdapter(userAdapter);
    //            }
    //
    //            @Override
    //            public void onCancelled(@NonNull DatabaseError databaseError)
    //            {
    //
    //            }
    //        });
    //    }
}
