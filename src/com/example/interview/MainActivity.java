package com.example.interview;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

import com.example.interview.CustomDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity extends Activity {
	private CustomDialog dialog;
	private  FeedbackAgent agent;
	// 首先在您的Activity中添加如下成员变量

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//		mPushAgent.onAppStart();
//		mPushAgent.enable();
        onResume();
        onPause();
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
    	agent = new FeedbackAgent(this);
        agent.sync();
    	
    }
    @Override
    public void onResume() {
    	super.onResume();
    	MobclickAgent.onResume(this);
    	}
    @Override
    	public void onPause() {
    	super.onPause();
    	MobclickAgent.onPause(this);
    	}
    public void clickHandler1(View view){
    	Intent intent = new Intent(this, javaActivity.class);
    	String mText = "JAVA开发";
		intent.putExtra("Text", mText);
    	startActivity(intent);
    	//setContentView(R.layout.java);
    }
    public void clickHandler2(View view){
    	Intent intent = new Intent(this, javaActivity.class);
    	String mText = "C/C++开发";
		intent.putExtra("Text", mText);
    	startActivity(intent);
    }
    public void clickHandler3(View view){
    	Intent intent = new Intent(this, javaActivity.class);
    	String mText = "前端开发";
		intent.putExtra("Text", mText);
    	startActivity(intent);
    }
    public void clickHandler4(View view){
    	Intent intent = new Intent(this, javaActivity.class);
    	String mText = "通信网络";
		intent.putExtra("Text", mText);
    	startActivity(intent);
    }
    public void clickHandler5(View view){
    	Intent intent = new Intent(this, javaActivity.class);
    	String mText = "算法与逻辑";
		intent.putExtra("Text", mText);
    	startActivity(intent);
    }
    public void clickHandler6(View view){
    	Intent intent = new Intent(this, javaActivity.class);
    	String mText = "数据结构与操作系统";
		intent.putExtra("Text", mText);
    	startActivity(intent);
    }
    public void clickHandler7(View view){
    	Intent intent = new Intent(this, collectActivity.class);
    	String mText = "Collect";
		intent.putExtra("Text", mText);
    	startActivity(intent);
    }
    public void download(View view){
//    	Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://netogether.sinaapp.com/"));
//    	//it.setClassName("com.Android.browser", "com.android.browser.BrowserActivity");
//    	startActivity(it);
    	//Toast.makeText(this, "您当前的是最新版本~", Toast.LENGTH_LONG).show();
    	UmengUpdateAgent.update(this);
    }
    public void setting(View view){
    CustomDialog.Builder customBuilder = new
            CustomDialog.Builder(MainActivity.this);
       /* customBuilder.setTitle("标题")
            .setMessage("提示内容")
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	
                }
            })
            .setPositiveButton("确定", 
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });*/
	    customBuilder.setTitle("添加题库")
	    .setMessage("如果您有题目要提交，请点击确定发送邮件")
	    .setPositiveButton("确定", 
	            new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
	            startSendEmailIntent(); 
	        }
	    });
        dialog = customBuilder.create();
        dialog.show();
    }
    private void startSendEmailIntent(){  
        Intent data=new Intent(Intent.ACTION_SENDTO);  
        data.setData(Uri.parse("mailto:it_interview@163.com"));  
        data.putExtra(Intent.EXTRA_SUBJECT, "IT面试宝典题库提交");  
        data.putExtra(Intent.EXTRA_TEXT, "请输入您要提交的问题，感谢您的支持。");  
        startActivity(data);  
    }  
    public void feedback(View view){
    	agent.startFeedbackActivity();

    }
}
