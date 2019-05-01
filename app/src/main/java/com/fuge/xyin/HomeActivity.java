package com.fuge.xyin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.fuge.xyin.R;
import com.fuge.xyin.adapter.HsScrollViewPagerAdapter;
import com.fuge.xyin.base.BaseActivity;
import com.fuge.xyin.fragment.DyAttentionFragment;
import com.fuge.xyin.fragment.DyFollowFragment;
import com.fuge.xyin.fragment.DyHomeFragment;
import com.fuge.xyin.fragment.DyMessageFragment;
import com.fuge.xyin.fragment.DyUserFragment;
import com.fuge.xyin.model.TabEntity;
import com.fuge.xyin.view.DragPointView;
import com.fuge.xyin.view.NoScrollViewPager;
import com.fuge.xyin.view.tab.SlidingTabLayout;
import com.fuge.xyin.view.tab.listener.OnTabSelectListener;
import com.tiktokdemo.lky.tiktokdemo.Constant;
import com.tiktokdemo.lky.tiktokdemo.record.RecordVideoActivity;
import com.tiktokdemo.lky.tiktokdemo.record.bean.MusicBean;
import com.tiktokdemo.lky.tiktokdemo.utils.AppUtil;
import com.tiktokdemo.lky.tiktokdemo.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements DragPointView.OnDragListencer, ViewPager.OnPageChangeListener {


    @BindView(R.id.mSlidingTab)
    SlidingTabLayout mSlidingTab;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.iv_doview)
    ImageView ivDoview;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<TabEntity> mTitles = new ArrayList<>();
    private ScaleAnimation anim;

    private final String LOCAL_MUSIC_NAME = "RISE2.mp3";
    private String mLocalMusicPath = Constant.PIC_FILE + File.separator + LOCAL_MUSIC_NAME;

    @Override
    public void initMainView() {
        setContentView(R.layout.activity_dy_home);
        ButterKnife.bind(this);

    }

    @Override
    public void initUi() {
        anim = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.bottom_icon);
        initTab();
        setLisener();
        requestPermissions();
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onDragOut() {

    }

    private void setLisener() {

        mSlidingTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mSlidingTab.getTitleView(position).startAnimation(anim);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

    }

    private void initTab() {
        List<String> strs = new ArrayList<>();
        //添加热门
        TabEntity item1 = new TabEntity();
        item1.setId("1");
        item1.setName("首页");
        mTitles.add(item1);
        //关注
        TabEntity item2 = new TabEntity();
        item2.setId("2");
        item2.setName("关注");
        mTitles.add(item2);
        //附近
        TabEntity item3 = new TabEntity();
        item3.setId("3");
        item3.setName(".");
        mTitles.add(item3);
        TabEntity item4 = new TabEntity();
        item4.setId("4");
        item4.setName("消息");
        mTitles.add(item4);
        TabEntity item5 = new TabEntity();
        item5.setId("5");
        item5.setName("我");
        mTitles.add(item5);


        //mTitles.addAll(HnHomeBrandBiz.mCateData);

        for (int i = 0; i < mTitles.size(); i++) {
            strs.add(mTitles.get(i).getName());
            switch (mTitles.get(i).getId()) {
                case "1"://我的
                    mFragments.add(DyHomeFragment.getInstance(true));
                    break;
                case "2":
                    mFragments.add(DyFollowFragment.getInstance(true));

                    break;
                case "3":
                    mFragments.add(DyAttentionFragment.getInstance(true));
                    break;
                case "4":
                    mFragments.add(DyMessageFragment.getInstance(true));
                    break;
                case "5":
                    mFragments.add(DyUserFragment.getInstance(true));
                    break;
                default:
                    break;
            }
        }

        HsScrollViewPagerAdapter adapter = new HsScrollViewPagerAdapter(getSupportFragmentManager(), mFragments, strs);
        if (mFragments.size() < 1) return;
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(adapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
                    mSlidingTab.setBackgroundColor(getResources().getColor(R.color.mian_tab_theme_color));
                } else{
                    mSlidingTab.setBackgroundColor(getResources().getColor(R.color.pure_black));
                }
                mSlidingTab.getTitleView(position).startAnimation(anim);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


//    @Override
//    public void onBackPressed() {
//        if (jzVideo != null) {
//            if (jzVideo.backPress()) {
//                return;
//            }
//        }
//        super.onBackPressed();
//    }

    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        applyPermissions(permissions);
    }


    protected PermissionTask permissionTask;
    protected OnPermissionDenyListener mOnPermissionDenyListener;
    protected OnCompleteListener onCompleteListener;
    public static final int ACTIVITY_PERMISSION_CODE = 1000;
    public static final int ACTIVITY_PERMISSIONS_CODE = 1500;

    protected void setPermissionTask(PermissionTask task) {
        this.permissionTask = task;
    }

    protected void applyPermission(String permission, PermissionTask task, OnPermissionDenyListener onPermissionDenyListener) {
        applyPermission(permission, task, onPermissionDenyListener, null);
    }

    protected void applyPermission(String permission,
                                   PermissionTask task,
                                   OnPermissionDenyListener onPermissionDenyListener
            , OnCompleteListener onCompleteListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (task != null) {
                task.operate();
            }
            return;
        }
        this.mOnPermissionDenyListener = onPermissionDenyListener;
        this.onCompleteListener = onCompleteListener;
        setPermissionTask(task);
        permission(permission);
    }

    protected void applyPermissions(String[] permissions) {
        List<String> result = new ArrayList<>();
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (!(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)) {
                    result.add(permission);
                }
            }
            if (result.size() > 0)
                ActivityCompat.requestPermissions(this, result.toArray(new String[result.size()]), ACTIVITY_PERMISSIONS_CODE);
        }
    }


    protected void applyPermission(String permission, PermissionTask task) {
        applyPermission(permission, task, null);
    }


    protected void permission(String permission) {
        if (!(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //已经禁止提示了
                if (mOnPermissionDenyListener != null) {
                    mOnPermissionDenyListener.onPermissionDeny();
                }
                Toast.makeText(AppUtil.getApplicationContext(), String.format(getString(R.string.permission_prohibit_tip), getRefuseMsg(permission)), Toast.LENGTH_SHORT).show();
                onComplete();
                return;
            }
            requestPermission(permission);
        } else {
            if (permissionTask != null) {
                permissionTask.operate();
            }
            onComplete();
        }
    }

    private void onComplete() {
        if (onCompleteListener != null) {
            onCompleteListener.onPermissionComplete();
        }
    }

    private void requestPermission(String permission) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, ACTIVITY_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACTIVITY_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权
                    if (permissionTask != null) {
                        permissionTask.operate();
                    }
                    onComplete();
                } else {
                    //用户拒绝授权
                    if (mOnPermissionDenyListener != null) {
                        mOnPermissionDenyListener.onPermissionDeny();
                    }
                    if (permissions == null || permissions.length == 0) {
                        Toast.makeText(AppUtil.getApplicationContext(), String.format(getString(R.string.permission_prohibit_tip), getString(R.string.permission_default)), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AppUtil.getApplicationContext(), String.format(getString(R.string.permission_prohibit_tip), getRefuseMsg(permissions[0])), Toast.LENGTH_SHORT).show();
                    }

                    onComplete();
                }
                break;
        }
    }


    @OnClick(R.id.iv_doview)
    public void onViewClicked() {
        mSlidingTab.setBackgroundColor(getResources().getColor(R.color.black));
        if (!FileUtils.checkFileExits(mLocalMusicPath)) {
            FileUtils.copyFileFromAssets(HomeActivity.this, LOCAL_MUSIC_NAME, Constant.PIC_FILE);
        }
        MusicBean searchMusicResultBean = new MusicBean();
        searchMusicResultBean.setMusicId(1);
        searchMusicResultBean.setUrl(mLocalMusicPath);
        searchMusicResultBean.setLocalPath(mLocalMusicPath);
        searchMusicResultBean.setName(LOCAL_MUSIC_NAME);
        Intent intent = new Intent(HomeActivity.this, RecordVideoActivity.class);
        intent.putExtra("MusicBean", searchMusicResultBean);
        startActivity(intent);
    }


    public interface PermissionTask {
        void operate();
    }

    public interface OnPermissionDenyListener {
        void onPermissionDeny();
    }

    public interface OnCompleteListener {
        void onPermissionComplete();
    }

    private String getRefuseMsg(String permission) {
        String permissionStr = getString(R.string.permission_default);
        if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionStr = getString(R.string.permission_location);
        } else if (permission.equals(Manifest.permission.CAMERA)) {
            permissionStr = getString(R.string.permission_camera);
        } else if (permission.equals(Manifest.permission.RECORD_AUDIO)) {
            permissionStr = getString(R.string.permission_record);
        } else if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionStr = getString(R.string.permission_storage);
        } else if (permission.equals(Manifest.permission.CALL_PHONE)) {
            permissionStr = getString(R.string.permission_phone);
        }
        return permissionStr;
    }

    private static boolean isExit = false;// 定义是否已退出应用

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {// 返回事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {// 双击返回键退出应用
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            // 返回键双击延迟
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }

}
