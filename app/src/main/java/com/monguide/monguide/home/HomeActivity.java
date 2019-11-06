package com.monguide.monguide.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.monguide.monguide.R;
import com.monguide.monguide.home.feed.FeedFragment;
import com.monguide.monguide.home.notifications.NotificationsFragment;

public class HomeActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;

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

        mFeedFragment = new FeedFragment();
        mNotificationFragment = new NotificationsFragment();

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_home_bottomnavigationview);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Load feed fragment as default fragment
        mBottomNavigationView.setSelectedItemId(R.id.activity_home_bottomnavigationview_menuitem_home);
        loadFragment(mFeedFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_fragmentcontainer , fragment);
        fragmentTransaction.commit();
    }

}
