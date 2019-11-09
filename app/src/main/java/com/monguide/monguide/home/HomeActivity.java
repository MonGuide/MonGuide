package com.monguide.monguide.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.monguide.monguide.R;
import com.monguide.monguide.home.feed.FeedFragment;
import com.monguide.monguide.home.notifications.NotificationsFragment;
import com.monguide.monguide.loginandsignup.LoginActivity;
import com.monguide.monguide.question.AddQuestionActivity;

public class HomeActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;

    private FloatingActionButton mAddQuestionFloatingActionButton;

    private Fragment mFeedFragment;
    private Fragment mNotificationFragment;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.activity_home_bottomnavigationview_menuitem_home :
                    loadFragment(mFeedFragment);
                    break;
                case R.id.activity_home_bottomnavigationview_menuitem_notifications:
                    loadFragment(mNotificationFragment);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.activity_home_toolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // OPEN USER PROFILE HERE
                        FirebaseAuth.getInstance().signOut();
                        startLoginActivity();
                        return true;
                    }
                });

        mAddQuestionFloatingActionButton = findViewById(R.id.activity_home_addquestionfloatingactionbutton);
        mAddQuestionFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddQuestionActivity();
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                FirebaseAuth.getInstance().signOut();
                startLoginActivity();
                return false;
            }
        });
        mFeedFragment = new FeedFragment();
        mNotificationFragment = new NotificationsFragment();

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_home_bottomnavigationview);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Load feed fragment as default fragment
        mBottomNavigationView.setSelectedItemId(R.id.activity_home_bottomnavigationview_menuitem_home);
        loadFragment(mFeedFragment);
    }

    private void startAddQuestionActivity() {
        startActivity(new Intent(HomeActivity.this, AddQuestionActivity.class));
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_fragmentcontainer , fragment);
        fragmentTransaction.commit();
    }

}
