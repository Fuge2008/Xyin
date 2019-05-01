package com.fuge.xyin.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fuge.xyin.CommitAdapter;
import com.fuge.xyin.R;
import com.fuge.xyin.base.BaseFragment;
import com.fuge.xyin.interfaces.OnViewPagerListener;
import com.fuge.xyin.utils.GetScreenWinth;
import com.fuge.xyin.utils.MyVideoPlayer;
import com.fuge.xyin.utils.PagerLayoutManager;
import com.fuge.xyin.utils.SoftKeyBoardListener;
import com.fuge.xyin.utils.SoftKeyHideShow;
import com.fuge.xyin.utils.VideoAdapter;
import com.fuge.xyin.view.DYLoadingView;
import com.fuge.xyin.view.Love;
import com.fly.video.downloader.DownloadVideoActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DyHomeFragment extends BaseFragment {
    private List<String> myData;
    private RecyclerView recyclerView;
    private PagerLayoutManager mLayoutManager;
    private MyVideoPlayer jzVideo;
    private SoftKeyBoardListener softKeyBoardListener;//软键盘监听
    private CommitAdapter commitAdapter;
    private RecyclerView recyclerViewCommit;
    private RelativeLayout rl_bottom;
    private View commit;
    private TextView tv_shape, tv_shape2, tv_send, tv_context;
    private LinearLayout ll_cancel;
    private RelativeLayout rl_all;
    private EditText et_context;
    private Animation showAction;
    private VideoAdapter adapter;
    private ImageView iv_doview;
    private ImageView iv_search;
    private Love love;

    private DYLoadingView dyLoadingView;

    /**
     * 默认从第一个开始播放
     */
    private int positionClick = 0;

    /**
     * 是否可以自动滑动
     * 当现实评论列表，说明用户想评论，不可以自动滑动
     */
    private boolean isScroll = true;

    public static DyHomeFragment getInstance(boolean big) {
        DyHomeFragment frag = new DyHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("big", big);
        frag.setArguments(bundle);
        return frag;
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_dy_home;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }

    @Override
    protected void initData() {
        setView();
        setListener();
        addData();
        setAdapter();
        setSoftKeyBoardListener();

    }

    @Override
    public void onResume() {
        super.onResume();
        //home back
        if (jzVideo != null) {
            jzVideo.goOnPlayOnResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (jzVideo != null) {
            if (jzVideo.backPress()) {
                return;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // 视频回去的时候要暂停
        ((AudioManager) mActivity.getSystemService(
                Context.AUDIO_SERVICE)).requestAudioFocus(
                new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int focusChange) {
                    }
                }, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        //home back
        if (jzVideo != null) {
            jzVideo.goOnPlayOnPause();
        }
    }

    private void setListener() {


    }

    private void setView() {
        rl_bottom = mActivity.findViewById(R.id.rl_bottom);
        recyclerView = mActivity.findViewById(R.id.recyclerView);
        recyclerViewCommit = mActivity.findViewById(R.id.recyclerViewCommit);
        commit = mActivity.findViewById(R.id.commit);
        tv_shape = mActivity.findViewById(R.id.tv_shape);
        tv_shape2 = mActivity.findViewById(R.id.tv_shape2);
        tv_send = mActivity.findViewById(R.id.tv_send);
        tv_context = mActivity.findViewById(R.id.tv_context);
        ll_cancel = mActivity.findViewById(R.id.ll_cancel);
        rl_all = mActivity.findViewById(R.id.rl_all);
        et_context = mActivity.findViewById(R.id.et_context);
        iv_doview = mActivity.findViewById(R.id.iv_doview);
        dyLoadingView = mActivity.findViewById(R.id.dy_view);
        love = mActivity.findViewById(R.id.love);
        iv_search = mActivity.findViewById(R.id.iv_search);
        myData = new ArrayList<>();
        dyLoadingView.start();
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,DownloadVideoActivity.class));
            }
        });


    }

    private void addData() {
        myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0");
        myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0");
        myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0");
        myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0");
        myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0");

        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url("http://www.fuzhenwen.top/douyin/video/video.json").method("GET", null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    private void setAdapter() {
        //设置adapter
        adapter = new VideoAdapter(mActivity, myData);
        recyclerView.setAdapter(adapter);
        mLayoutManager = new PagerLayoutManager(mActivity, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                //点击进入 0
                playVideo(view, false);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                positionClick = position;
                //滑动选择 1
                playVideo(view, isBottom);
            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                //暂停上一个播放
//                releaseVideo(view);
            }
        });

        adapter.setOnItemClickListerer(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String type, View view, View view1, View view2) {
                if (type.equals("back")) {
                    //返回
                    if (jzVideo.backPress()) {
                        return;
                    }
                    getActivity().finish();
                } else if (type.equals("commit")) {
                    //评论
                    showCommitDialog();
                }
            }
        });

    }

    /**
     * 开始播放 & 监听播放完成
     */
    private void playVideo(View view, boolean isBottom) {
        if (view != null) {
            jzVideo = view.findViewById(R.id.jzVideo);
            jzVideo.startVideo();
            if (isBottom) {
                //到最后一个加载第二页
                myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0");
                myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0");
                myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0");
                myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0");
                myData.add("https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0");
                adapter.notifyDataSetChanged();
            }
            jzVideo.setFinishListerer(new MyVideoPlayer.OnItemClickListener() {
                @Override
                public void onItemClick() {
                    //播放完成自动播放下一个,用户没有看评论列表可以播放下一个
                    if (isScroll) {
                        smoothMoveToPosition(recyclerView, positionClick++);
                    }
                }
            });
        }
    }

    /**
     * 平滑的滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            //滑动指定高度
            mRecyclerView.smoothScrollBy(0, GetScreenWinth.getHeight(mActivity) -
                    (Resources.getSystem().getDimensionPixelSize(Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"))));
        } else {
            // 第三种可能:跳转位置在最后可见项之后
            mRecyclerView.smoothScrollToPosition(position);
        }
    }

    /**
     * 评论布局
     */
    public void showCommitDialog() {
        if (commitAdapter == null) {
            commitAdapter = new CommitAdapter(mActivity);
            recyclerViewCommit.setLayoutManager(new LinearLayoutManager(mActivity));
            recyclerViewCommit.setAdapter(commitAdapter);
        } else {
            commitAdapter.notifyDataSetChanged();
        }

        //为布局设置显示的动画
        showAction = AnimationUtils.loadAnimation(mActivity, R.anim.actionsheet_dialog_in);
        commit.startAnimation(showAction);

        //显示布局和阴影
        commit.setVisibility(View.VISIBLE);
        tv_shape.setVisibility(View.VISIBLE);

        //不可以滑动
        isScroll = false;

        //关闭评论
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit.setVisibility(View.GONE);
                tv_shape.setVisibility(View.GONE);
                //可以滑动
                isScroll = true;
            }
        });
        //阴影点击,隐藏评论列表和阴影
        tv_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit.setVisibility(View.GONE);
                tv_shape.setVisibility(View.GONE);
                //可以滑动
                isScroll = true;
            }
        });
        //输入评论点击
        tv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftKeyHideShow.HideShowSoftKey(mActivity);//隐藏软键盘
            }
        });
        //第二层阴影点击，隐藏输入评论框和软键盘
        tv_shape2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftKeyHideShow.HideShowSoftKey(mActivity);//隐藏软键盘
            }
        });
        //发送评论
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "评论成功", Toast.LENGTH_SHORT).show();
                SoftKeyHideShow.HideShowSoftKey(mActivity);//隐藏软键盘
            }
        });
    }

    /**
     * 软键盘监听
     */
    private void setSoftKeyBoardListener() {
        softKeyBoardListener = new SoftKeyBoardListener(mActivity);
        //软键盘状态监听
        softKeyBoardListener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //动态设置控件宽高
                ViewGroup.LayoutParams params = rl_bottom.getLayoutParams();
                rl_bottom.setPadding(0, 0, 0, height);
                rl_bottom.setLayoutParams(params);
                //当软键盘显示的时候
                rl_bottom.setVisibility(View.VISIBLE);
                tv_shape2.setVisibility(View.VISIBLE);

                et_context.setFocusable(true);
                et_context.setFocusableInTouchMode(true);
                et_context.setCursorVisible(true);
                et_context.requestFocus();
            }

            @Override
            public void keyBoardHide(int height) {
                //当软键盘隐藏的时候
                rl_bottom.setVisibility(View.GONE);
                tv_shape2.setVisibility(View.GONE);
                et_context.setFocusable(false);
                et_context.setFocusableInTouchMode(false);
                et_context.setCursorVisible(false);
            }
        });
        //设置点击事件,显示软键盘
        et_context.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View view) {
                InputMethodManager manager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        //防止EditText点击两次才获取到焦点
        et_context.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag++;
                if (flag == 2) {
                    flag = 0;//不要忘记这句话
                    //处理逻辑
                    et_context.setFocusable(true);
                    et_context.setFocusableInTouchMode(true);
                    et_context.setCursorVisible(true);
                }
                return false;
            }
        });
    }



    @OnClick({R.id.suipai, R.id.cb, R.id.iv_search, R.id.dy_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.suipai:
                break;
            case R.id.cb:
                break;
            case R.id.iv_search:
                break;
            case R.id.dy_view:
                break;
        }
    }


//    @Override
//    protected void onResume() {//友盟统计
//        super.onResume();
//        MobclickAgent.onResume(this);
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        MobclickAgent.onPause(this);
//    }
}
