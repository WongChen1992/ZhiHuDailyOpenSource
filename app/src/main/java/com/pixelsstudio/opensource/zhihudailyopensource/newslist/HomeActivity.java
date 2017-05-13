package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseActivity;
import com.pixelsstudio.opensource.zhihudailyopensource.helpandfeedback.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WongChen on 2017/1/15.
 */

public class HomeActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.colorTabNormalColor), getResources().getColor(R.color.colorTabSelectedColor));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorTabSelectedColor));
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        NewsListFragment newsListFragment = new NewsListFragment();
        StarredFragment starredFragment = new StarredFragment();
        CollectionFragment collectionFragment = new CollectionFragment();
        fragments.add(newsListFragment);
        fragments.add(starredFragment);
        fragments.add(collectionFragment);

        tabTitles.add(getResources().getString(R.string.tab_news));
        tabTitles.add(getResources().getString(R.string.tab_starred));
        tabTitles.add(getResources().getString(R.string.tab_collection));

        HomeActivity.TabViewPagerAdapter adapter = new HomeActivity.TabViewPagerAdapter(getSupportFragmentManager(), tabTitles, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mViewPager.getChildCount());
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragments.get(position).onResume();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        new NewsListPresenter(newsListFragment);
        new StarredPresenter(starredFragment);
        new CollectionPresenter(collectionFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent();
                intent.setClass(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.download:
                download();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class TabViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> views;
        private List<String> titles;

        public TabViewPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> views) {
            super(fm);
            this.views = views;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private void download(){

    }
}
