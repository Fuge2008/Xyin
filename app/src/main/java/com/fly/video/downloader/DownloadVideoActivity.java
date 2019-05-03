package com.fly.video.downloader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.video.downloader.core.Validator;
import com.fly.video.downloader.core.app.BaseActivity;
import com.fly.video.downloader.layout.fragment.HistoryFragment;
import com.fly.video.downloader.layout.fragment.VideoFragment;
import com.fly.video.downloader.layout.fragment.VideoSearchFragment;
import com.fly.video.downloader.util.content.Recv;
import com.fly.video.downloader.util.model.Video;
import com.fuge.xyin.R;
import com.fuge.xyin.utils.ToastUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DownloadVideoActivity extends BaseActivity {
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.mSearchVideoList)
    LinearLayout mSearchVideoList;
    @BindView(R.id.mVideoHistoryCb)
    CheckBox mVideoHistoryCb;
    @BindView(R.id.mTipTv)
    TextView mTipTv;


    private Unbinder unbinder;
    protected VideoFragment videoFragment;
    protected HistoryFragment historyFragment;
    protected VideoSearchFragment searchFragment = null;

    private Date backPressAt = null;
    private boolean fromSend = false;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if (id == R.id.navigation_history) showFragment(historyFragment);
            else showFragment(videoFragment);
            ft.commit();

            return true;
        }
    };

    private FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_download_video);
        unbinder = ButterKnife.bind(this);

        // 设置Toolbar
        setSupportActionBar(toolbar);
        //底部状态栏
 /*       bottomNavigationView.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark)));
        bottomNavigationView.setItemBackgroundResource(R.drawable.transparent);*/
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);


        videoFragment = VideoFragment.newInstance();
        historyFragment = HistoryFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.full_pager, videoFragment).add(R.id.view_pager, historyFragment).hide(historyFragment).show(videoFragment).commit();

        fromSend = this.getIntent() != null && Intent.ACTION_SEND.equals(this.getIntent().getAction());

        mVideoHistoryCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if (isChecked) {
                    showFragment(historyFragment);
                    mVideoHistoryCb.setBackgroundResource(R.mipmap.b_4);
                    mTipTv.setText("去搜视频");
                } else {
                    showFragment(videoFragment);
                    mVideoHistoryCb.setBackgroundResource(R.mipmap.bag);
                    mTipTv.setText("搜索记录");
                }
                ft.commit();
            }
        });
        Log.e("打印数据3：","666");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

//    @Override
//    public void onBackPressed() {
//        FragmentManager fm = getSupportFragmentManager();
//
//        // 最后一次 并且大于2秒
//        if (fm.getBackStackEntryCount() == 0 && !fromSend) {
//            if (backPressAt == null || new Date().getTime() - backPressAt.getTime() > 2000) {
//                Toast.makeText(this, R.string.one_more_exit, Toast.LENGTH_SHORT).show();
//                backPressAt = new Date();
//                return;
//            } else {
//                com.fly.video.downloader.core.app.Process.background(this);
//                //super.onBackPressed();
//                return;
//            }
//        }
//
//        if (fm.getBackStackEntryCount() > 0) {
//            FragmentManager.BackStackEntry back = fm.getBackStackEntryAt(fm.getBackStackEntryCount() -1);
//            switch (back.getName())
//            {
//                case "video":
//                    showFragment(videoFragment);
//                    break;
//            }
//
//            fm.popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        fromSend = false;

        showFragment(videoFragment);
        Recv recv = new Recv(intent);
        if (recv.isActionSend() && videoFragment.isAdded()) {
            fromSend = true;
            videoFragment.Analyze(recv.getContent());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //finish();
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void setMainProgress(int progress) {
        if (progress >= 100) progress = 0;
        progressBar.setProgress(progress);
    }

    public void showVideoSearchFragment() {
        if (null == searchFragment)
            searchFragment = VideoSearchFragment.newInstance();

        if (!searchFragment.isAdded())
            getSupportFragmentManager().beginTransaction().add(R.id.no_navigation_pager, searchFragment).commit();

        showFragment(searchFragment);
        getSupportFragmentManager().beginTransaction().addToBackStack("video").commit();
    }

    public void onVideoChange(String str) {
        //Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        showFragment(videoFragment);
        videoFragment.Analyze(str);
    }

    public void onVideoChange(Video video) {
        onVideoChange(video, false);
    }

    public void onVideoChange(Video video, boolean fromHistory) {
        showFragment(videoFragment);
        videoFragment.Analyze(video, fromHistory);
    }

    public void onHistoryAppend(Video video) {
        historyFragment.perpendHistory(video);
    }


    @OnClick({R.id.mBackLl, R.id.mSearchLl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBackLl:
                finish();
                break;
            case R.id.mSearchLl:
                showPhotoDialog();
                break;
        }
    }

    private void showPhotoDialog() {
        final AlertDialog dlg = new AlertDialog.Builder(
                DownloadVideoActivity.this).create();
        dlg.show();
        Window window = dlg.getWindow();
        window.setContentView(R.layout.dialog_social_delete);
        TextView tvCancel = window.findViewById(R.id.tvCancel);
        TextView tvOk =window.findViewById(R.id.tvOk);
        final EditText tvContent =  window.findViewById(R.id.tvContent);
        ImageView ivClose = window.findViewById(R.id.ivClose);

        tvOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //关闭对话框
                String str =  tvContent.getText().toString();
                if (str.isEmpty() || !Validator.containsUrl(str))
                {
                    Toast.makeText(DownloadVideoActivity.this, R.string.noURL, Toast.LENGTH_SHORT).show();
                } else {
                    DownloadVideoActivity.this.onVideoChange(str);
                    tvContent.setText("");
                    dlg.cancel();
                }

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PackageManager packageManager = DownloadVideoActivity.this.getPackageManager();
                    //Intent intent = new Intent();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.ss.android.ugc.aweme");
                    DownloadVideoActivity.this.startActivity(intent);
                    com.fly.video.downloader.core.app.Process.background((Activity) DownloadVideoActivity.this);
                    ToastUtils.showShortToast(DownloadVideoActivity.this,"前往抖音");
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    ToastUtils.showShortToast(DownloadVideoActivity.this,"该功能待小主开发");
                }
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.cancel();
            }
        });

    }
}
