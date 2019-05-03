package com.fly.video.downloader.layout.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import com.fuge.xyin.R;
import com.fuge.xyin.SearchVideoActivity;
import com.fuge.xyin.utils.ToastUtils;
import com.fly.video.downloader.DownloadVideoActivity;
import com.fly.video.downloader.core.Validator;
import com.fly.video.downloader.layout.listener.VideoFragmentListener;
import com.fly.video.downloader.util.content.Recv;
import com.fly.video.downloader.util.content.analyzer.AnalyzerTask;
import com.fly.video.downloader.util.model.Video;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    protected VideoFragmentListener mFragmentListener;

    public VideoFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();

        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (mFragmentListener != null)
        {
            if (hidden)  mFragmentListener.pause();
            else mFragmentListener.resume();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.video, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.video_menu_search == item.getItemId()){
            ((DownloadVideoActivity)getActivity()).showVideoSearchFragment();
            showPhotoDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_video, container, false);
        mFragmentListener.onCreateView(view);

        Recv recv = new Recv(this.getActivity().getIntent());
        if (recv.isActionSend() && isAdded())
            Analyze(recv.getContent());
        showPhotoDialog();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentListener.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mFragmentListener = new VideoFragmentListener(this, context);

        setMenuVisibility(true);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        setMenuVisibility(false);
        mFragmentListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();

        mFragmentListener.pause();
    }

    @Override
    public void onResume() {
        super.onResume();

        mFragmentListener.resume();
    }

    public void Analyze(String text)
    {
        mFragmentListener.reset();

        Toast.makeText(getActivity(), R.string.start_analyzing, Toast.LENGTH_SHORT).show();

        AnalyzerTask analyzerTask = new AnalyzerTask(getActivity(), mFragmentListener);
        analyzerTask.execute(text);
    }

    public void Analyze(Video video)
    {
        Analyze(video, false);
    }

    public void Analyze(Video video, boolean fromHistory)
    {
        mFragmentListener.onAnalyzed(video, fromHistory);
    }

    private void showPhotoDialog() {
        final AlertDialog dlg = new AlertDialog.Builder(
                getActivity()).create();
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
                    Toast.makeText(getActivity(), R.string.noURL, Toast.LENGTH_SHORT).show();
                } else {
                    ((DownloadVideoActivity)getActivity()).onVideoChange(str);
                    tvContent.setText("");
                    dlg.cancel();
                }

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PackageManager packageManager = getActivity().getPackageManager();
                    //Intent intent = new Intent();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.ss.android.ugc.aweme");
                    getActivity().startActivity(intent);
                    com.fly.video.downloader.core.app.Process.background((Activity) getActivity());
                    ToastUtils.showShortToast(getActivity(),"前往抖音");
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    ToastUtils.showShortToast(getActivity(),"该功能待小主开发");
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
