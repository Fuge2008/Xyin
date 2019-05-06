package com.fuge.xyin.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.video.downloader.GlideApp;
import com.fly.video.downloader.util.model.Video;
import com.fuge.xyin.R;
import com.fuge.xyin.WebViewActivity;
import com.fuge.xyin.base.BaseActivity;
import com.fuge.xyin.model.TabEntity;
import com.fuge.xyin.model.VideoBean;
import com.fuge.xyin.view.GoodView;
import com.fuge.xyin.view.MarqueeTextView;
import com.fuge.xyin.view.MusicalNoteLayout;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private BaseActivity mContext;
    private List<VideoBean> mDatas;
    private GoodView mGoodView;

    //为RecyclerView的Item添加监听
    public interface OnItemClickListener {
        void onItemClick(int position, String type, View view, View view1, View view2);
    }

    public VideoAdapter.OnItemClickListener mOnItemClickListerer;

    public void setOnItemClickListerer(VideoAdapter.OnItemClickListener listerer) {
        this.mOnItemClickListerer = listerer;
    }

    public VideoAdapter(BaseActivity context, List<VideoBean> datas) {
        mContext = context;
        mDatas = datas;
        mGoodView = new GoodView(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_play_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.jzVideo.setUp(String.valueOf(mDatas.get(position).getVideourl()), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        //隐藏全屏按钮、返回按钮
        holder.jzVideo.fullscreenButton.setVisibility(View.GONE);
        holder.jzVideo.backButton.setVisibility(View.GONE);
        //返回
        holder.ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "back", view, view, view);
            }
        });
        //评论
        holder.iv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListerer.onItemClick(position, "commit", view, view, view);
            }
        });
        holder.iv_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView) v).setImageResource(R.mipmap.au6);
                mGoodView.setText(" ");
                mGoodView.show(v);
            }
        });
        holder.mUerHeadCiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("title","fuge").putExtra("url","http://fuzhenwen.top:8000/"));
            }
        });
        holder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        holder.marquee1.startScroll();

        GlideApp.with(mContext)
                .load(mDatas.get(position).getVideopic())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.icon_fu)
                .skipMemoryCache(true)
                .circleCrop()
                .into(holder.mUerHeadCiv);
        if (mDatas.get(position).getVideoname() != null)
        holder.tv_name.setText(mDatas.get(position).getVideoname());
        if (mDatas.get(position).getVideocontent() != null)
        holder.tv_context.setText(mDatas.get(position).getVideocontent());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public JZVideoPlayerStandard jzVideo;
        LinearLayout ll_back;
        ImageView iv_commit;
        ImageView iv_share;
        ImageView iv_heart;
        TextView tv_name;
        TextView tv_context;
        MusicalNoteLayout musicalNoteLayout;
        MarqueeTextView marquee1;
        CircleImageView mUerHeadCiv;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_back = itemView.findViewById(R.id.ll_back);
            iv_commit = itemView.findViewById(R.id.iv_commit);
            iv_share = itemView.findViewById(R.id.iv_share);
            jzVideo = itemView.findViewById(R.id.jzVideo);
            marquee1 = itemView.findViewById(R.id.marquee1);
            musicalNoteLayout = itemView.findViewById(R.id.music_note_layout);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            mUerHeadCiv = itemView.findViewById(R.id.headCiv);
            tv_name =itemView.findViewById(R.id.tv_name);
            tv_context =itemView.findViewById(R.id.tv_context);
//
        }
    }

    // 一键分享
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        // 关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("传递正知，记录美好1！欢迎访问：http://www.hao-ji.cn/");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://www.hao-ji.cn/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("传递正知，记录美好2！");
        // 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://www.hao-ji.cn/uploads/kind/image/20170215/20170215071149_50979.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.hao-ji.cn/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("传递正知，记录美好3！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("传递正知，记录美好4！");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.hao-ji.cn/");

        // 启动分享GUI
        oks.show(mContext);
    }
}
