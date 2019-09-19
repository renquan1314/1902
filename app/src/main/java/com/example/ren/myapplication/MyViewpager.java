package com.example.ren.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyViewpager extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewpager;
    private MyReFragAdapter adapter;
    /**
     * 返回
     */
    private Button mBtn;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_viewpager);
        initView();
    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mBtn = (Button) findViewById(R.id.btn);
        mTv = (TextView) findViewById(R.id.tv);
        mBtn.setOnClickListener(this);

        ArrayList<Bean.ResultsBean> list = (ArrayList<Bean.ResultsBean>) getIntent().getSerializableExtra("list");
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            BlankFragment blankFragment = new BlankFragment();
            fragments.add(blankFragment);
            String url = list.get(i).getUrl();
            String desc = list.get(i).getDesc();
            Bundle bundle = new Bundle();
            bundle.putString("desc", desc);
            bundle.putString("img", url);
            blankFragment.setArguments(bundle);
        }
        adapter = new MyReFragAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(adapter);

        showtv(1);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showtv(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void showtv(int postion){
        mTv.setText("当前图片位置："+postion);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn:
                finish();
                break;
        }
    }
}
