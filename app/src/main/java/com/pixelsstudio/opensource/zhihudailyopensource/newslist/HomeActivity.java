package com.pixelsstudio.opensource.zhihudailyopensource.newslist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.api.ApiClient;
import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseActivity;
import com.pixelsstudio.opensource.zhihudailyopensource.helpandfeedback.SettingsActivity;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.NewsDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by WongChen on 2017/1/15.
 */

public class HomeActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();
    private MenuItem menuItemDownload;

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
        menuItemDownload = menu.findItem(R.id.download);
        return true;
    }

    private List<Integer> downloadId = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent();
                intent.setClass(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.download:
                for (ListNews.StoriesEntity data : ((NewsListFragment) fragments.get(0)).getDownloadData()) {
                    downloadId.add(data.getId());
                }
                menuItemDownload.setVisible(false);
                Snackbar.make(HomeActivity.this.getWindow().getDecorView(), "开始离线...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                download(downloadId.get(0));
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

    private void download(int id) {
        Call<NewsDetails> call = ApiClient.getAPI().newsDetails(id);
        call.enqueue(new Callback<NewsDetails>() {
            @Override
            public void onResponse(Call<NewsDetails> call, Response<NewsDetails> response) {

                downloadId.remove(0);
                if (downloadId.size() > 0) {
                    download(downloadId.get(0));
                }else{
                    menuItemDownload.setVisible(true);
                    Snackbar.make(HomeActivity.this.getWindow().getDecorView(), "离线完成", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<NewsDetails> call, Throwable t) {

            }
        });
    }
}
