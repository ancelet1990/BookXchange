package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.androidbootcamp.bookxchange.Fragments.ChatsFragment;
import net.androidbootcamp.bookxchange.Fragments.UsersFragment;
import net.androidbootcamp.bookxchange.model.Message;
import net.androidbootcamp.bookxchange.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
{
    CircleImageView profile_image;
    TextView username;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        Toolbar toolbar = findViewById(R.id.toolbar);
        //        setSupportActionBar(toolbar);
        //        getSupportActionBar().setTitle("");

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference =
                FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getFirstName() + " " + user.getLastName());
                if (user.getImageURL().equals("default"))
                {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else
                {
                    //Changed to second line when adding txt seen info
                    //                    Glide.with(MainActivity.this).load(user.getImageURL()).into(profile_image);
                    Glide.with(getApplicationContext()).load(user.getImageURL())
                         .into(profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        //        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        //new code
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                ViewPagerAdapter viewPagerAdapter =
                        new ViewPagerAdapter(getSupportFragmentManager());
                int unread = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Message chat = snapshot.getValue(Message.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && ! chat.isIsseen())
                    {
                        unread++;
                    }
                }

                if (unread == 0)
                {
                    viewPagerAdapter.addFragment(new ChatsFragment(), "Chats");
                } else
                {
                    viewPagerAdapter.addFragment(new ChatsFragment(), "(" + unread + ") Chats");
                }

                //                viewPagerAdapter.addFragment(new ChatsFragment(), "Chats");
                viewPagerAdapter.addFragment(new UsersFragment(), "Users");
                //                viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");

                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

//        viewPagerAdapter.addFragment(new ChatsFragment(), "Chats");
//        viewPagerAdapter.addFragment(new UsersFragment(), "Users");
//        viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
//
//        viewPager.setAdapter(viewPagerAdapter);
//
//        tabLayout.setupWithViewPager(viewPager);
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

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm)
        {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position)
        {
            return fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }

        //ctrl + o

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return titles.get(position);
        }
    }

    private void status(String status)
    {
        reference =
                FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    protected void onResume()
    {
        super.onResume();
        status("online ");
    }

    protected void onPause()
    {
        super.onPause();
        status("offline");
    }
}