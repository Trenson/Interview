package com.example.interview;


import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class javaActivity  extends Activity implements OnClickListener{
	String mText="";
	String name="";
	String s_que="";
	String s_ans="";
	String all_content="";
	String all_title="IT求职宝典";		
	int len1=1,len2=1,len3=1,len4=1,len5=1,len6=1;
	int l1=1,l2=1,l3=1,l4=1,l5=1,l6=1;
	int ini1=1,ini2=1,ini3=1,ini4=1,ini5=1,ini6=1;
	int cnt=0;
	SQLdm s = new SQLdm();
	SQLrec s2 = new SQLrec();
    private GestureDetector gestureDetector;  
    private TextView back,share;
    private TextView edit_title;
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	final WeiXinShareContent weixinContent = new WeiXinShareContent();
	final CircleShareContent circleMedia = new CircleShareContent();
	final QZoneShareContent qzone = new QZoneShareContent(); 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.java);
	        back = (TextView) findViewById(R.id.mimedetail_back);
			back.setOnClickListener(this);
			share = (TextView) findViewById(R.id.share);
			share.setOnClickListener(this);
	        Intent intent = getIntent(); 
	        mText = intent.getStringExtra("Text"); 
	        edit_title = (TextView) findViewById(R.id.top1);
//	        all_content=all_content+"今天又在“IT求职宝典”上做了"+cnt+"道"+mText+"的题目，感觉自己棒棒哒！";
			edit_title.setText(mText);
	        gestureDetector = new BuileGestureExt(this,new BuileGestureExt.OnGestureResult() {
	            @Override
	            public void onGestureResult(int direction) {
	            	switch(direction){
	                case BuileGestureExt.GESTURE_UP:
	                 break;
	                case BuileGestureExt.GESTURE_RIGHT:
	                	last(null);
	                 break;
	                case BuileGestureExt.GESTURE_DOWN:
	                 break;
	                case BuileGestureExt.GESTURE_LEFT:
	                	next(null);
	                 break;
	                }
	            }
	        }
	        ).Buile();
	        question();
	        onResume();
	        onPause();
	        PushAgent.getInstance(this).onAppStart();
	        shareall(0);
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
	    public void shareall(int cc){
	    	all_content="我今天又在“IT求职宝典”上做了"+cc+"道"+mText+"的题目，感觉自己棒棒哒！";
	    	 // 设置分享内容
	    	mController.setShareContent(all_content);
	    	// 设置分享图片, 参数2为图片的url地址
//	    	mController.setShareMedia(new UMImage(this, 
//	    	                                      "http://pp.myapp.com/ma_icon/0/icon_11373129_19649318_1418625104/96"));
//	    	mController.setShareMedia(new UMImage(this, 
//                    "http://itinterviewhelper.sinaapp.com/"));
	    	// 设置分享图片，参数2为本地图片的资源引用
	    	mController.setShareMedia(new UMImage(this, R.drawable.ic_launcher));
	    	// 设置分享图片，参数2为本地图片的路径(绝对路径)
	    	//mController.setShareMedia(new UMImage(getActivity(), 
//	    	                                BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

	    	// 设置分享音乐
	    	//UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
	    	//uMusic.setAuthor("GuGu");
	    	//uMusic.setTitle("天籁之音");
	    	// 设置音乐缩略图
	    	//uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	    	//mController.setShareMedia(uMusic);

	    	// 设置分享视频
	    	//UMVideo umVideo = new UMVideo(
//	    	          "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
	    	// 设置视频缩略图
	    	//umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	    	//umVideo.setTitle("友盟社会化分享!");
	    	//mController.setShareMedia(umVideo);
	    	QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1103589216",
	                "r7H1tW5LiuMAKlcm");
	qZoneSsoHandler.addToSocialSDK();
			UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1103589216",
		            "r7H1tW5LiuMAKlcm");
		qqSsoHandler.addToSocialSDK();  
		qqSsoHandler.setTargetUrl("http://itinterviewhelper.sinaapp.com/");
		String appID = "wxf02bbbeffec66d98";
		String appSecret = "3a6e345d9b22f45d499f4d0eca840452";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(this,appID,appSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this,appID,appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		
		//设置微信好友分享内容
		
		//设置分享文字
		weixinContent.setShareContent(all_content);
		//设置title
//		weixinContent.setTitle(all_title);
		//设置分享内容跳转URL
		weixinContent.setShareImage(new UMImage(this, "http://pp.myapp.com/ma_icon/0/icon_11373129_19649318_1418625104/128"));
		weixinContent.setTargetUrl("http://itinterviewhelper.sinaapp.com/");
		mController.setShareMedia(weixinContent);
		//设置微信朋友圈分享内容

		circleMedia.setShareContent(all_content);
		//设置朋友圈title
		circleMedia.setTitle(all_content);
		circleMedia.setShareImage(new UMImage(this, "http://pp.myapp.com/ma_icon/0/icon_11373129_19649318_1418625104/128"));
		circleMedia.setTargetUrl("http://itinterviewhelper.sinaapp.com/");
		mController.setShareMedia(circleMedia);
		//设置QQ空间分享内容
 
		qzone.setShareContent(all_content);
        qzone.setTitle(all_title);
        qzone.setShareImage(new UMImage(this, "http://pp.myapp.com/ma_icon/0/icon_11373129_19649318_1418625104/128"));
        qzone.setTargetUrl("http://itinterviewhelper.sinaapp.com/");
        mController.setShareMedia(qzone);
	    }
	 	public void question(){
	 		TextView tt = (TextView) findViewById(R.id.textView1);
	 		if(mText.equals("JAVA开发")){	
	 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
	 			Cursor cursor = db2.rawQuery("select col1 from tbl_record",null);
				if(cursor.moveToFirst())
					l1 = cursor.getInt(0);
				ini1=l1;
				SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			cursor = db.rawQuery("select * from tbl_java where No="+l1,null);
	 			if(cursor.moveToFirst())
					name = l1+"、"+cursor.getString(cursor.getColumnIndex("question"));
	 			SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
	 		  	tt.setText(msp);
	 			cursor = db.rawQuery("select count(*) from tbl_java",null);
				if(cursor.moveToFirst())
					len1 = cursor.getInt(0);
	 		}
	 		else if(mText.equals("C/C++开发")){
	 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
	 			Cursor cursor = db2.rawQuery("select col2 from tbl_record",null);
				if(cursor.moveToFirst())
					l2 = cursor.getInt(0);
				ini2=l2;
				SQLiteDatabase db =s.openDatabase(getApplicationContext());
				cursor = db.rawQuery("select * from tbl_c where No="+l2,null);
	 			if(cursor.moveToFirst())
					name = l2+"、"+cursor.getString(cursor.getColumnIndex("question"));
	 			SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
	 		  	tt.setText(msp);
	 			cursor = db.rawQuery("select count(*) from tbl_c",null);
				if(cursor.moveToFirst())
					len2 = cursor.getInt(0);
	 		}
	 		else if(mText.equals("前端开发")){
	 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
	 			Cursor cursor = db2.rawQuery("select col3 from tbl_record",null);
				if(cursor.moveToFirst())
					l3 = cursor.getInt(0);
				ini3=l3;
				SQLiteDatabase db =s.openDatabase(getApplicationContext());
				cursor = db.rawQuery("select * from tbl_js where No="+l3,null);
	 			if(cursor.moveToFirst())
					name = l3+"、"+cursor.getString(cursor.getColumnIndex("question"));
	 			SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
	 		  	tt.setText(msp);
	 			cursor = db.rawQuery("select count(*) from tbl_js",null);
				if(cursor.moveToFirst())
					len3 = cursor.getInt(0);
	 		}
	 		else if(mText.equals("通信网络")){
	 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
	 			Cursor cursor = db2.rawQuery("select col4 from tbl_record",null);
				if(cursor.moveToFirst())
					l4= cursor.getInt(0);
				ini4=l4;
				SQLiteDatabase db =s.openDatabase(getApplicationContext());
				cursor = db.rawQuery("select * from tbl_tel where No="+l4,null);
	 			if(cursor.moveToFirst())
					name = l4+"、"+cursor.getString(cursor.getColumnIndex("question"));
	 			SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
	 		  	tt.setText(msp);
	 			cursor = db.rawQuery("select count(*) from tbl_tel",null);
				if(cursor.moveToFirst())
					len4 = cursor.getInt(0);
	 		}
	 		else if(mText.equals("算法与逻辑")){
	 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
	 			Cursor cursor = db2.rawQuery("select col5 from tbl_record",null);
				if(cursor.moveToFirst())
					l5 = cursor.getInt(0);
				ini5=l5;
				SQLiteDatabase db =s.openDatabase(getApplicationContext());
				cursor = db.rawQuery("select * from tbl_pm where No="+l5,null);
	 			if(cursor.moveToFirst())
					name = l5+"、"+cursor.getString(cursor.getColumnIndex("question"));
	 			SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
	 		  	tt.setText(msp);
	 			cursor = db.rawQuery("select count(*) from tbl_pm",null);
				if(cursor.moveToFirst())
					len5 = cursor.getInt(0);
	 		}
	 		else if(mText.equals("数据结构与操作系统")){
	 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
	 			Cursor cursor = db2.rawQuery("select col6 from tbl_record",null);
				if(cursor.moveToFirst())
					l6 = cursor.getInt(0);
				ini6=l6;
				SQLiteDatabase db =s.openDatabase(getApplicationContext());
				cursor = db.rawQuery("select * from tbl_test where No="+l6,null);
	 			if(cursor.moveToFirst())
					name = l6+"、"+cursor.getString(cursor.getColumnIndex("question"));
	 			SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
	 		  	tt.setText(msp);
	 			cursor = db.rawQuery("select count(*) from tbl_test",null);
				if(cursor.moveToFirst())
					len6 = cursor.getInt(0);
	 		}	 	
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	tv.setText("");
	 	}
	    public void show(View view){
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	if(mText.equals("JAVA开发")){
	    		String text=tv.getText().toString();
	    		if(text==""){
	    			SQLiteDatabase db =s.openDatabase(getApplicationContext());
		 			Cursor cursor = db.rawQuery("select * from tbl_java where No="+l1,null);
		 			if(cursor.moveToFirst())
						name = cursor.getString(cursor.getColumnIndex("answer"));
		 			SpannableString  msp = new SpannableString("解答："+'\n'+name);
		 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		 		  	tv.setText(msp);
		 			s_que=cursor.getString(cursor.getColumnIndex("question"));
		 			s_ans=name; 	
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
		 			ContentValues values = new ContentValues();
		    		values.put("col1", l1);
		    		db2.update("tbl_record", values, "r_id=?", new String[]{"1"});
		    		cnt=l1-ini1;
	    		}		
	    		else
	    			tv.setText("");
	    	}
	    	else if(mText.equals("C/C++开发")){
	    		String text=tv.getText().toString();
	    		if(text==""){
		 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
		 			Cursor cursor = db.rawQuery("select * from tbl_c where No="+l2,null);
		 			if(cursor.moveToFirst())
						name = cursor.getString(cursor.getColumnIndex("answer"));
		 			SpannableString  msp = new SpannableString("解答："+'\n'+name);
		 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		 		  	tv.setText(msp);
		 			s_que=cursor.getString(cursor.getColumnIndex("question"));
		 			s_ans=name;
		 			db.close();
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
		 			ContentValues values = new ContentValues();
		    		values.put("col2", l2);
		    		db2.update("tbl_record", values, "r_id=?", new String[]{"1"});
		    		cnt=l2-ini2;
	    		}		
	    		else
	    			tv.setText("");
	    	}
	    	else if(mText.equals("前端开发")){
	    		String text=tv.getText().toString();
	    		if(text==""){
		 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
		 			Cursor cursor = db.rawQuery("select * from tbl_js where No="+l3,null);
		 			if(cursor.moveToFirst())
						name = cursor.getString(cursor.getColumnIndex("answer"));
		 			SpannableString  msp = new SpannableString("解答："+'\n'+name);
		 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		 		  	tv.setText(msp);
		 			s_que=cursor.getString(cursor.getColumnIndex("question"));
		 			s_ans=name;
		 			db.close();
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
		 			ContentValues values = new ContentValues();
		    		values.put("col3", l3);
		    		db2.update("tbl_record", values, "r_id=?", new String[]{"1"});
		    		cnt=l3-ini3;
	    		}		
	    		else
	    			tv.setText("");
	    	}
	    	else if(mText.equals("通信网络")){
	    		String text=tv.getText().toString();
	    		if(text==""){
		 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
		 			Cursor cursor = db.rawQuery("select * from tbl_tel where No="+l4,null);
		 			if(cursor.moveToFirst())
						name = cursor.getString(cursor.getColumnIndex("answer"));
		 			SpannableString  msp = new SpannableString("解答："+'\n'+name);
		 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		 		  	tv.setText(msp);
		 			s_que=cursor.getString(cursor.getColumnIndex("question"));
		 			s_ans=name;
		 			db.close();
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
		 			ContentValues values = new ContentValues();
		    		values.put("col4", l4);
		    		db2.update("tbl_record", values, "r_id=?", new String[]{"1"});
		    		cnt=l4-ini4;
	    		}		
	    		else
	    			tv.setText("");
	    	}
	    	else if(mText.equals("算法与逻辑")){
	    		String text=tv.getText().toString();
	    		if(text==""){
		 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
		 			Cursor cursor = db.rawQuery("select * from tbl_pm where No="+l5,null);
		 			if(cursor.moveToFirst())
						name = cursor.getString(cursor.getColumnIndex("answer"));
		 			SpannableString  msp = new SpannableString("解答："+'\n'+name);
		 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		 		  	tv.setText(msp);
		 			s_que=cursor.getString(cursor.getColumnIndex("question"));
		 			s_ans=name;
		 			db.close();
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
		 			ContentValues values = new ContentValues();
		    		values.put("col5", l5);
		    		db2.update("tbl_record", values, "r_id=?", new String[]{"1"});
		    		cnt=l5-ini5;
	    		}		
	    		else
	    			tv.setText("");
	    	}
	    	else if(mText.equals("数据结构与操作系统")){
	    		String text=tv.getText().toString();
	    		if(text==""){
		 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
		 			Cursor cursor = db.rawQuery("select * from tbl_test where No="+l6,null);
		 			if(cursor.moveToFirst())
						name = cursor.getString(cursor.getColumnIndex("answer"));
		 			SpannableString  msp = new SpannableString("解答："+'\n'+name);
		 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		 		  	tv.setText(msp);
		 			s_que=cursor.getString(cursor.getColumnIndex("question"));
		 			s_ans=name;
		 			db.close();
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext());
		 			ContentValues values = new ContentValues();
		    		values.put("col6", l6);
		    		db2.update("tbl_record", values, "r_id=?", new String[]{"1"});
		    		cnt=l6-ini6;
	    		}		
	    		else
	    			tv.setText("");
	    	}
	    }
	    public void next(View view){
	    	TextView tt = (TextView) findViewById(R.id.textView1);
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	if(mText.equals("JAVA开发")){
	    		if(l1==len1)
		 	 		Toast.makeText(this, "这是最后一条", Toast.LENGTH_LONG).show();
	    		else if(l1<len1){
	    		l1=l1+1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_java where No="+l1,null);
	 			if(cursor.moveToFirst())
					name = l1+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		tv.setText("");
	    		}
	    	}
	    	else if(mText.equals("C/C++开发")){
	    		if(l2==len2)
		 	 		Toast.makeText(this, "这是最后一条", Toast.LENGTH_LONG).show();
	    		else if(l2<len2){
	    		l2=l2+1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_c where No="+l2,null);
	 			if(cursor.moveToFirst())
					name = l2+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("前端开发")){
	    		if(l3==len3)
		 	 		Toast.makeText(this, "这是最后一条", Toast.LENGTH_LONG).show();
	    		else if(l3<len3){
	    		l3=l3+1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_js where No="+l3,null);
	 			if(cursor.moveToFirst())
					name = l3+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("通信网络")){
	    		if(l4==len4)
		 	 		Toast.makeText(this, "这是最后一条", Toast.LENGTH_LONG).show();
	    		else if(l4<len4){
	    		l4=l4+1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_tel where No="+l4,null);
	 			if(cursor.moveToFirst())
					name = l4+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("算法与逻辑")){
	    		if(l5==len5)
		 	 		Toast.makeText(this, "这是最后一条", Toast.LENGTH_LONG).show();
	    		else if(l5<len5){
	    		l5=l5+1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_pm where No="+l5,null);
	 			if(cursor.moveToFirst())
					name = l5+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("数据结构与操作系统")){
	    		if(l6==len6)
		 	 		Toast.makeText(this, "这是最后一条", Toast.LENGTH_LONG).show();
	    		else if(l6<len6){
	    		l6=l6+1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_test where No="+l6,null);
	 			if(cursor.moveToFirst())
					name = l6+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    }
	    public void last(View view){
	    	TextView tt = (TextView) findViewById(R.id.textView1);
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	if(mText.equals("JAVA开发")){
	    		if(l1==1)
		 	 		Toast.makeText(this, "这是第一条", Toast.LENGTH_LONG).show();
	    		else if(l1>1){
	    		l1=l1-1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_java where No="+l1,null);
	 			if(cursor.moveToFirst())
					name = l1+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		tv.setText("");
	    		}
	    	}
	    	else if(mText.equals("C/C++开发")){
	    		if(l2==1)
		 	 		Toast.makeText(this, "这是第一条", Toast.LENGTH_LONG).show();
	    		else if(l2>1){
	    		l2=l2-1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_c where No="+l2,null);
	 			if(cursor.moveToFirst())
					name = l2+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("前端开发")){
	    		if(l3==1)
		 	 		Toast.makeText(this, "这是第一条", Toast.LENGTH_LONG).show();
	    		else if(l3>1){
	    		l3=l3-1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_js where No="+l3,null);
	 			if(cursor.moveToFirst())
					name = l3+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("通信网络")){
	    		if(l4==1)
		 	 		Toast.makeText(this, "这是第一条", Toast.LENGTH_LONG).show();
	    		else if(l4>1){
	    		l4=l4-1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_tel where No="+l4,null);
	 			if(cursor.moveToFirst())
					name = l4+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("算法与逻辑")){
	    		if(l5==1)
		 	 		Toast.makeText(this, "这是第一条", Toast.LENGTH_LONG).show();
	    		else if(l5>1){
	    		l5=l5-1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_pm where No="+l5,null);
	 			if(cursor.moveToFirst())
					name = l5+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    	else if(mText.equals("数据结构与操作系统")){
	    		if(l6==1)
		 	 		Toast.makeText(this, "这是第一条", Toast.LENGTH_LONG).show();
	    		else if(l6>1){
	    		l6=l6-1;
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext());
	 			Cursor cursor = db.rawQuery("select * from tbl_test where No="+l6,null);
	 			if(cursor.moveToFirst())
					name = l6+"、"+cursor.getString(cursor.getColumnIndex("question"));
	    		tv.setText("");
	    		SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	    		}
	    	}
	    }
	    public void collect(View view){
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	String text=tv.getText().toString();    	
	    	if(mText.equals("JAVA开发")){
	    		if(text!=""){
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext()); 
		 			Cursor cursor = db2.rawQuery("select * from tbl_qa where question=?",new String[]{s_que});
		 			if(cursor.moveToFirst()){ 
		 				Toast.makeText(this, "你已经收藏过了！", Toast.LENGTH_LONG).show(); 
		 			}
		 			else{
				 		Toast.makeText(this, "收藏成功！", Toast.LENGTH_LONG).show(); 
		 				ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
						cv.put("question",s_que);
						cv.put("answer",s_ans); 
			 		    db2.insert("tbl_qa",null,cv); 
		 			}
	    		}
	    		else
	    			Toast.makeText(this, "请先显示答案再收藏~", Toast.LENGTH_LONG).show();
	    	}
	    	else if(mText.equals("C/C++开发")){
	    		if(text!=""){
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext()); 
		 			Cursor cursor = db2.rawQuery("select * from tbl_qa where question=?",new String[]{s_que});
		 			if(cursor.moveToFirst()){ 
		 				Toast.makeText(this, "你已经收藏过了！", Toast.LENGTH_LONG).show(); 
		 			}
		 			else{
		 				Toast.makeText(this, "收藏成功！", Toast.LENGTH_LONG).show();
		 				ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
		 				cv.put("question",s_que);
						cv.put("answer",s_ans); 
			 		    db2.insert("tbl_qa",null,cv); 
		 			}
	    		}
	    		else
	    			Toast.makeText(this, "请先显示答案再收藏~", Toast.LENGTH_LONG).show();
	    	}
	    	else if(mText.equals("前端开发")){
	    		if(text!=""){
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext()); 
		 			Cursor cursor = db2.rawQuery("select * from tbl_qa where question=?",new String[]{s_que});
		 			if(cursor.moveToFirst()){ 
		 				Toast.makeText(this, "你已经收藏过了！", Toast.LENGTH_LONG).show(); 
		 			}
		 			else{
		 				Toast.makeText(this, "收藏成功！", Toast.LENGTH_LONG).show();
		 				ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
		 				cv.put("question",s_que);
						cv.put("answer",s_ans); 
			 		    db2.insert("tbl_qa",null,cv); 
		 			}
	    		}
	    		else
	    			Toast.makeText(this, "请先显示答案再收藏~", Toast.LENGTH_LONG).show();
	    	}
	    	else if(mText.equals("通信网络")){
	    		if(text!=""){
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext()); 
		 			Cursor cursor = db2.rawQuery("select * from tbl_qa where question=?",new String[]{s_que});
		 			if(cursor.moveToFirst()){ 
		 				Toast.makeText(this, "你已经收藏过了！", Toast.LENGTH_LONG).show(); 
		 			}
		 			else{
		 				Toast.makeText(this, "收藏成功！", Toast.LENGTH_LONG).show();
		 				ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
		 				cv.put("question",s_que);
						cv.put("answer",s_ans); 
			 		    db2.insert("tbl_qa",null,cv); 
		 			}
	    		}
	    		else
	    			Toast.makeText(this, "请先显示答案再收藏~", Toast.LENGTH_LONG).show();
	    	}
	    	else if(mText.equals("算法与逻辑")){
	    		if(text!=""){
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext()); 
		 			Cursor cursor = db2.rawQuery("select * from tbl_qa where question=?",new String[]{s_que});
		 			if(cursor.moveToFirst()){ 
		 				Toast.makeText(this, "你已经收藏过了！", Toast.LENGTH_LONG).show(); 
		 			}
		 			else{
		 				Toast.makeText(this, "收藏成功！", Toast.LENGTH_LONG).show();
		 				ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
		 				cv.put("question",s_que);
						cv.put("answer",s_ans); 
			 		    db2.insert("tbl_qa",null,cv); 
		 			}
	    		}
	    		else
	    			Toast.makeText(this, "请先显示答案再收藏~", Toast.LENGTH_LONG).show();
	    	}
	    	else if(mText.equals("数据结构与操作系统")){
	    		if(text!=""){
		 			SQLiteDatabase db2 =s2.openDatabase(getApplicationContext()); 
		 			Cursor cursor = db2.rawQuery("select * from tbl_qa where question=?",new String[]{s_que});
		 			if(cursor.moveToFirst()){ 
		 				Toast.makeText(this, "你已经收藏过了！", Toast.LENGTH_LONG).show(); 
		 			}
		 			else{
		 				Toast.makeText(this, "收藏成功！", Toast.LENGTH_LONG).show();
		 				ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
		 				cv.put("question",s_que);
						cv.put("answer",s_ans); 
			 		    db2.insert("tbl_qa",null,cv); 
		 			}
	    		}
	    		else
	    			Toast.makeText(this, "请先显示答案再收藏~", Toast.LENGTH_LONG).show();
	    	}
	    }
		 @Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.mimedetail_back:
					finish();
					break;
				case R.id.share:
//			    	Toast.makeText(this, "数量："+cnt+1, Toast.LENGTH_LONG).show();
		    		shareall(cnt+1);
			    	mController.openShare(this, false);
					break;
				}
			}
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        return gestureDetector.onTouchEvent(event);
	    }
	    @Override 
	    public boolean dispatchTouchEvent(MotionEvent ev) 
	    { 
	    	gestureDetector.onTouchEvent(ev);
//	    	RelativeLayout ll=(RelativeLayout)findViewById(R.id.lc);
//	    	scroll.onTouchEvent(ev); 
	    	return super.dispatchTouchEvent(ev); 
	    }
	    private void show(String value){
	        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
	    }
}
