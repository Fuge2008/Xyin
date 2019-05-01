package com.fuge.xyin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/** 闪屏界面 */
public class SplashActivity extends Activity {
	protected static final String TAG = SplashActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//setContentView(R.layout.activity_splash);
		if (!isTaskRoot()) {
			finish();
			return;
		}
		handler.sendEmptyMessageDelayed(0,3000);
	}


	Handler handler=new Handler(Looper.getMainLooper()){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 0:
					startActivity(new Intent(SplashActivity.this,HomeActivity.class));
					finish();
					break;
			}
		}
	};



}
