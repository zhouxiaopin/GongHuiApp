package cn.gzticc.gh;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.gzticc.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import cn.gzticc.customui.verticaltablayout.VerticalTabLayout;
import cn.gzticc.customui.verticaltablayout.adapter.TabAdapter;
import cn.gzticc.customui.verticaltablayout.widget.Badge;
import cn.gzticc.customui.verticaltablayout.widget.TabView;
import cn.gzticc.gh.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    private boolean isDrawer=false;
    //    @Bind(R.id.slideViewPage)
    ViewPager slideViewPage;
    VerticalTabLayout slideTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("共会");

/*        TranslucentBarManager translucentBarManager = new TranslucentBarManager(this);
        translucentBarManager.translucent(this, android.R.color.holo_purple);*/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(toggle);
        //同步
        toggle.syncState();


        navigationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(isDrawer){
                    return coordinatorLayout.dispatchTouchEvent(motionEvent);
                }else{
                    return false;
                }
            }
        });
        View headerLayout = navigationView.getHeaderView(0);
        slideViewPage = (ViewPager) headerLayout.findViewById(R.id.slideViewPage);
        slideTabLayout = (VerticalTabLayout) headerLayout.findViewById(R.id.slideTabLayout);

        List<String> list = new ArrayList<>();
        for (int i=0;i<4;i++){
            list.add(String.format(Locale.CHINA,"第%02d页",i));
        }
//        slideViewPage.setAdapter(new MyAdapter(getSupportFragmentManager(),list));
        slideViewPage.setAdapter(new MyPagerAdapter());
        slideTabLayout.setupWithViewPager(slideViewPage);

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                isDrawer=true;
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置  根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                coordinatorLayout.layout(navigationView.getRight(), 0, navigationView.getRight() + display.getWidth(), display.getHeight());
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                /*TextView textView = (TextView) drawerView.findViewById(R.id.tvSearch);
                Log.e("onDrawerOpened", textView.getText().toString());
                Log.e("onDrawerOpened", drawerView.getHeight()+"");*/
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawer=false;
            }
            @Override
            public void onDrawerStateChanged(int newState) {}
        });

/*        slideViewPage.setAdapter(null);
        slideTabLayout.setupWithViewPager(slideViewPage);*/

    }

    @Override
    protected void setStatusBar() {
//       int  mStatusBarColor = getResources().getColor(R.color.colorPrimary);
        int  mStatusBarColor = getResources().getColor(R.color.white);
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, mStatusBarColor, 30);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private class MyPagerAdapter extends PagerAdapter implements TabAdapter {
        List<String> titles;

        public MyPagerAdapter() {
            titles = new ArrayList<>();
            Collections.addAll(titles, "8小时外", "8小时内", "城市达人", "我的订阅");
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public TabView.TabBadge getBadge(int position) {
            if (position == 5) return new TabView.TabBadge.Builder().setBadgeNumber(666)
                    .setExactMode(true)
                    .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                        @Override
                        public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        }
                    }).build();
            return null;
        }

        @Override
        public TabView.TabIcon getIcon(int position) {
            return null;
        }

        @Override
        public TabView.TabTitle getTitle(int position) {

            return new TabView.TabTitle.Builder()
                    .setContent(titles.get(position))
                    .setTextColor(Color.BLUE, Color.BLACK)
                    .build();
        }

        @Override
        public int getBackground(int position) {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(MainActivity.this);
            tv.setTextColor(Color.BLUE);
            tv.setGravity(Gravity.CENTER);
            tv.setText(titles.get(position));
            tv.setTextSize(18);
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
