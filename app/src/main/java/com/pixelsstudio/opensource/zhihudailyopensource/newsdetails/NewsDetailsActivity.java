package com.pixelsstudio.opensource.zhihudailyopensource.newsdetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.pixelsstudio.opensource.zhihudailyopensource.R;
import com.pixelsstudio.opensource.zhihudailyopensource.base.BaseActivity;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.NewsDetails;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * Created by WongChen on 2017/2/22.
 */

public class NewsDetailsActivity extends BaseActivity implements NewsDetailsContract.View {
    private Toolbar mToolbar;
    private NewsDetailsContract.Presenter mPresenter;
    private WebView wevView;
    private SimpleDraweeView iv_poster;
    private MenuItem menuItemStarred, menuItemCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        new NewsDetailsPresenter(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wevView = (WebView) findViewById(R.id.webView);
        iv_poster = (SimpleDraweeView) findViewById(R.id.iv_poster);
        int id = getIntent().getExtras().getInt("id");
        mPresenter.getData(id);

//        ViewCompat.setTransitionName(iv_poster, "img");
//
////        Slide slide = new Slide();
////        slide.setDuration(500);
////
//        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.setDuration(500);
////
////        getWindow().setEnterTransition(slide);
//        getWindow().setSharedElementEnterTransition(changeBounds);
    }

    @Override
    public void showNewsDetails(NewsDetails data) {
        iv_poster.setImageURI(Uri.parse(data.getImage()));
        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\""+data.getCss().get(0)+"\" />" + data.getBody();
        wevView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
    }

    @Override
    public void setStarredImg(boolean isStarred) {
        if (isStarred) {
            menuItemStarred.setIcon(getDrawable(R.mipmap.ic_favorite_white_24dp));
            menuItemStarred.setChecked(true);
        } else {
            menuItemStarred.setIcon(getDrawable(R.mipmap.ic_favorite_border_white_24dp));
            menuItemStarred.setChecked(false);
        }
    }

    @Override
    public void setCollectionImg(boolean isCollection) {
        if (isCollection) {
            menuItemCollection.setIcon(getDrawable(R.mipmap.ic_star_white_24dp));
            menuItemCollection.setChecked(true);
        } else {
            menuItemCollection.setIcon(getDrawable(R.mipmap.ic_star_border_white_24dp));
            menuItemCollection.setChecked(false);
        }
    }

    @Override
    public void setStarredVisible(boolean isVisible) {
        menuItemStarred.setVisible(isVisible);
    }

    @Override
    public void setCollectionVisible(boolean isVisible) {
        menuItemCollection.setVisible(isVisible);
    }

    @Override
    public void showToast(String str) {
        Snackbar.make(getWindow().getDecorView(), str, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void setPresenter(NewsDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_menu, menu);
        menuItemStarred = menu.findItem(R.id.starred);
        menuItemCollection = menu.findItem(R.id.collection);
        mPresenter.checkCollection(mAppContext);
        mPresenter.checkStarred(mAppContext);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.starred:
                    mPresenter.starred(mAppContext,item.isChecked());
                break;
            case R.id.collection:
                    mPresenter.collection(mAppContext,item.isChecked());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
