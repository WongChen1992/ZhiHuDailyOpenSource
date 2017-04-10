package com.pixelsstudio.opensource.zhihudailyopensource.newsdetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
    }

    @Override
    public void showNewsDetails(NewsDetails data) {
        iv_poster.setImageURI(Uri.parse(data.getImage()));
        String htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\""+data.getCss().get(0)+"\" />" + data.getBody();
        wevView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
    }

    @Override
    public void setPresenter(NewsDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
