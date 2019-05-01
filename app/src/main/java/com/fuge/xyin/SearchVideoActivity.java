package com.fuge.xyin;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fly.video.downloader.util.model.Video;
import com.fuge.xyin.R;
import com.fly.video.downloader.layout.fragment.HistoryFragment;

public class SearchVideoActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    protected HistoryFragment historyFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        historyFragment = HistoryFragment.newInstance();
        fragmentTransaction.add(R.id.main_fragment, historyFragment);
        fragmentTransaction.show(historyFragment).commit();
    }
    public void onHistoryAppend(Video video)
    {
        historyFragment.perpendHistory(video);
    }
}
