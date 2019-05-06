package com.fly.video.downloader.util.content.analyzer.app;

import android.os.Parcel;
import android.os.Parcelable;



import java.io.Serializable;

public class VideoModel implements Parcelable, Serializable {

     private String videotitle;  //videoCover
     private String videoname;//Nickname
     private String videocontent;//videoContent
     private String videourl;//vidoeURl
     private String userid;//id
     private String videopic;//head

    public VideoModel(Parcel in) {
        videotitle = in.readString();
        videoname = in.readString();
        videocontent = in.readString();
        videourl = in.readString();
        userid = in.readString();
        videopic = in.readString();
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    public VideoModel() {
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getVideocontent() {
        return videocontent;
    }

    public void setVideocontent(String videocontent) {
        this.videocontent = videocontent;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVideopic() {
        return videopic;
    }

    public void setVideopic(String videopic) {
        this.videopic = videopic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videotitle);
        dest.writeString(videoname);
        dest.writeString(videocontent);
        dest.writeString(videourl);
        dest.writeString(userid);
        dest.writeString(videopic);
    }
}
