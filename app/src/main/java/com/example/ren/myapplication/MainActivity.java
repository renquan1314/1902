package com.example.ren.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImage;
    private RecyclerView mRecycler;
    private NavigationView mNav;
    private DrawerLayout mDrawer;
    private ArrayList<Bean.ResultsBean> lists;
    private MyAdapter adapter;

    //    http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
        initdata();
        initRec();
    }

    private void initRec() {
        mRecycler.setLayoutManager(new GridLayoutManager(this,2));
        lists = new ArrayList<>();
        adapter = new MyAdapter(this, lists);
        mRecycler.setAdapter(adapter);

        adapter.setS(new MyAdapter.OnClickListener() {
            @Override
            public void getposition(int position) {
                Intent intent = new Intent(MainActivity.this,MyViewpager.class);
                intent.putExtra("list",lists);
                startActivity(intent);
            }
        });
    }

    private void initdata() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.gankurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<Bean> observable = apiService.getjson();
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Bean bean) {
                        List<Bean.ResultsBean> results = bean.getResults();
                        lists.addAll(results);
                        adapter.notifyDataSetChanged();

                        String url = results.get(1).getUrl();
                        RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher);
                        Glide.with(MainActivity.this).load(url).apply(options).into(mImage);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void init() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.app_name);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNav.setItemIconTintList(null);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImage = (ImageView) findViewById(R.id.image);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mNav = (NavigationView) findViewById(R.id.nav);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
    }
}
