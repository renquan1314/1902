package com.example.ren.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {


    private View view;
    private ImageView mPageImage;
    /**
     * 1
     */
    private TextView mPageTv;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mPageImage = (ImageView) view.findViewById(R.id.page_image);
        mPageTv = (TextView) view.findViewById(R.id.page_tv);
        Bundle arguments = getArguments();
        String img = arguments.getString("img");
        String desc = arguments.getString("desc");
        Glide.with(getActivity()).load(img).into(mPageImage);

        mPageTv.setText(desc);
    }
}
