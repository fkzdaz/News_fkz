
package com.example.fkz2.news_fkz.ui.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.example.fkz2.news_fkz.R;
import com.example.fkz2.news_fkz.ui.fragment.ImageFragment;
import com.example.fkz2.news_fkz.ui.fragment.JokeFragment;
import com.example.fkz2.news_fkz.ui.fragment.VoiceFragment;
import com.example.fkz2.news_fkz.utils.ApiUtil;
import com.example.fkz2.news_fkz.utils.ColorUtil;
import com.example.fkz2.news_fkz.utils.Constants;
import com.example.fkz2.news_fkz.utils.StartActivityUtil;
import com.example.fkz2.news_fkz.widget.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AHBottomNavigation.OnTabSelectedListener {

    private Context context;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.bottom_navigation_bar)
    AHBottomNavigation bottomNavigationBar;
    private Fragment[] fragments;
    private int currentPosition = 0;
    ImageView userIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        context = getApplicationContext();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        View headerView = navView.getHeaderView(0);
        userIcon = (ImageView) headerView.findViewById(R.id.user_icon);
        Glide.with(this).load(ApiUtil.USER_ICON)
                .transform(new GlideCircleTransform(context))
                .placeholder(R.color.place_holder_color)
                .error(R.color.place_holder_color)
                .into(userIcon);
        navView.setNavigationItemSelectedListener(this);
        bottomNavigationBar.setOnTabSelectedListener(this);
        bottomNavigationBar.addItem(new AHBottomNavigationItem(R.string.navigation_bottom_image_text, R.drawable.ic_book_white_24dp, R.color.colorAccent));
        bottomNavigationBar.addItem(new AHBottomNavigationItem(R.string.navigation_bottom_voice, R.drawable.ic_voice_white_24dp, R.color.colorAccent));
        bottomNavigationBar.addItem(new AHBottomNavigationItem(R.string.navigation_bottom_joke, R.drawable.ic_video_white_24dp, R.color.colorAccent));

        final ImageFragment imageFragment = new ImageFragment();
        final VoiceFragment voiceFragment = new VoiceFragment();
        final JokeFragment jokeFragment = new JokeFragment();
        fragments = new Fragment[]{imageFragment, voiceFragment, jokeFragment};

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, imageFragment)
                .add(R.id.container, voiceFragment)
                .add(R.id.container, jokeFragment)
                .show(imageFragment)
                .hide(voiceFragment)
                .hide(jokeFragment)
                .commit();

        toolbar.setTitle(context.getString(R.string.navigation_bottom_image_text));

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentPosition) {
                    case 0:
                        imageFragment.backToTop();
                        break;
                    case 1:
                        voiceFragment.backToTop();
                        break;
                    case 2:
                        jokeFragment.backToTop();
                        break;
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.nav_chat:
                bundle.putInt(Constants.EXTRA_SOURCE_ID, Constants.SOURCE_FOR_NIGHT_CHAT);
                break;
            case R.id.nav_study:
                bundle.putInt(Constants.EXTRA_SOURCE_ID, Constants.SOURCE_FOR_DRIVER_STUDY);
                break;
            case R.id.nav_game:
                bundle.putInt(Constants.EXTRA_SOURCE_ID, Constants.SOURCE_FOR_GAME);
                break;
            case R.id.nav_voice:
                bundle.putInt(Constants.EXTRA_SOURCE_ID, Constants.SOURCE_FOR_TODAY_VOICE);
                break;
            case R.id.nav_crazy:
                bundle.putInt(Constants.EXTRA_SOURCE_ID, Constants.SOURCE_FOR_FAT_CHAT);
                break;
            case R.id.nav_up:
                bundle.putInt(Constants.EXTRA_SOURCE_ID, Constants.SOURCE_FOR_UP);
                break;
            case R.id.nav_news:
                bundle.putInt(Constants.EXTRA_SOURCE_ID, Constants.SOURCE_FOR_SEVEN_NEWS);
                break;
        }
        StartActivityUtil.start(this, NightChatActivity.class, bundle);


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeColor(int colorId) {
        toolbar.setBackgroundColor(ContextCompat.getColor(context, colorId));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ColorUtil.colorBurn(ContextCompat.getColor(context, colorId)));
        }
        //Snackbar.make(drawerLayout, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


    @Override
    public void onTabSelected(int position, boolean wasSelected) {
        currentPosition = position;
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        switch (position) {
            case 0:
                toolbar.setTitle(context.getString(R.string.navigation_bottom_image_text));
                break;
            case 1:
                toolbar.setTitle(context.getString(R.string.navigation_bottom_voice));
                break;
            case 2:
                toolbar.setTitle(context.getString(R.string.navigation_bottom_joke));
                break;
            default:
                toolbar.setTitle(context.getString(R.string.navigation_bottom_image_text));
                break;
        }

        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            if (i == position) {
                if (!fragments[position].isAdded()) {
                    trx.add(R.id.container, fragments[position]);
                }
                trx.show(fragments[i]).commitAllowingStateLoss();
            } else {
                trx.hide(fragments[i]);
            }
        }
    }
}
