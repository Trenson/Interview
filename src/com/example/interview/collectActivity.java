package com.example.interview;


import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class collectActivity  extends Activity  implements OnClickListener{
	String mText="";
	int count=0;
	int l7=1;
	int num=0;
    private GestureDetector gestureDetector;  
    private TextView back;
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.collect);
	        Intent intent = getIntent(); 
	        mText = intent.getStringExtra("Text"); 
	        back = (TextView) findViewById(R.id.mimedetail_back);
			back.setOnClickListener(this);
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
	        onResume();
	        onPause();
	        question();
	        PushAgent.getInstance(this).onAppStart();
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
	 @Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.mimedetail_back:
				finish();
				break;

			default:
				break;
			}
		}
	 public String readFile(String path){
		 String res=""; 
	 		try{ 
	 		   InputStream in = getResources().getAssets().open(path);
	 		   int length = in.available();         
	 		byte [] buffer = new byte[length];        
	 		in.read(buffer);            
	 		res = EncodingUtils.getString(buffer, "UTF-8");     
	 		}catch(Exception e){ 
	 		      e.printStackTrace();         
	 		   }
	 		return res;
	 }
	 public void question(){
	 		TextView tt = (TextView) findViewById(R.id.textView1);
	 	 if(mText.equals("Collect")){
	 			SQLrec s = new SQLrec();
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext()); 
	 			Cursor cursor = db.rawQuery("select No from tbl_qa",null);
	 			if(cursor.moveToLast())
	 				count = cursor.getInt(0);
	 			cursor = db.rawQuery("select * from tbl_qa where question is not ?",new String[]{"null"}); 
	 			String name = null;
	 		   if(cursor.moveToFirst()){  
	 			   		num++;
		 		      name = num+"、"+cursor.getString(cursor.getColumnIndex("question"))+'\n';
		 		      l7=cursor.getInt(cursor.getColumnIndex("No"));
		 		    }
	 		   else
	 			   name="暂无收藏问题。";
	 		   	SpannableString  msp = new SpannableString(name);
	 		  	msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	 		  	tt.setText(msp);
	 		    cursor.close();  
	 		}
		 	TextView tv = (TextView) findViewById(R.id.textView2);
	    	tv.setText("");	
	 	}
	    public void show(View view){
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	if(mText.equals("Collect")&&num>0){
	 			String name = null;
	 			SQLrec s = new SQLrec();
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext()); 
	 			Cursor cursor = db.rawQuery("select * from tbl_qa where No="+l7,null); 
	 		    if(cursor.moveToFirst()){  
	 		      name = cursor.getString(cursor.getColumnIndex("answer"));
	 		    }  
	 		   String text=tv.getText().toString();
	    		if(text==""){
	    			SpannableString  msp = new SpannableString("解答："+'\n'+name);
		 		  	msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		 		  	tv.setText(msp);
	    		}
	    		else
	    			tv.setText("");
	 		    cursor.close();  
	    	}
	    }
	    public void next(View view){
	    	TextView tt = (TextView) findViewById(R.id.textView1);
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	if(l7==count){
	 	 		   Toast.makeText(this, "这是最后一条", Toast.LENGTH_LONG).show();
	    		}
	    		if(mText.equals("Collect")&&l7<count){
	    		l7=l7+1;num++;
	    		SQLrec s = new SQLrec();
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext()); 
	 		    Cursor cursor = db.rawQuery("select * from tbl_qa where No="+l7,null); 
	    		String name = null;
	    		while(!cursor.moveToFirst()){
	    			l7++;
	    			cursor = db.rawQuery("select * from tbl_qa where No="+l7,null);
	    		}
	    		cursor.moveToFirst();
 			name = num+"、"+cursor.getString(cursor.getColumnIndex("question"))+'\n';
 			SpannableString  msp = new SpannableString(name);
 			msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
 			msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);	
 			tv.setText("");
	    	tt.setText(msp);
	    	}
	    }
	    public void last(View view){
	    	TextView tt = (TextView) findViewById(R.id.textView1);
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	if(num==1){
	 	 		   Toast.makeText(this, "这是第一条", Toast.LENGTH_LONG).show();
	    		}
	    	if(mText.equals("Collect")&&num>1){
	    		l7=l7-1;num--;
	    		SQLrec s = new SQLrec();
	 			SQLiteDatabase db =s.openDatabase(getApplicationContext()); 
	 		    Cursor cursor = db.rawQuery("select * from tbl_qa where No="+l7,null); 
	    		String name = null;
	    		while(!cursor.moveToFirst()){
	    			l7--;
	    			cursor = db.rawQuery("select * from tbl_qa where No="+l7,null);
	    		}
	    		cursor.moveToFirst();
 			name = num+"、"+cursor.getString(cursor.getColumnIndex("question"))+'\n';
 			SpannableString  msp = new SpannableString(name);
 			msp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,name.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
 			msp.setSpan(new RelativeSizeSpan(1.2f), 0,name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);	
 			tv.setText("");
	    	tt.setText(msp);
	    	}
	    }
	    public void clear(View view){
			Toast.makeText(this, "删除成功！", Toast.LENGTH_LONG).show();
	    	TextView tt = (TextView) findViewById(R.id.textView1);
	    	TextView tv = (TextView) findViewById(R.id.textView2);
	    	SQLrec s = new SQLrec();
			SQLiteDatabase db =s.openDatabase(getApplicationContext());
			db.delete("tbl_qa", "No=?", new String[]{l7+""});
			Cursor cursor = db.rawQuery("select No from tbl_qa",null);
			if(cursor.moveToLast()){
			count = cursor.getInt(0);
			if(l7>count){
				last(view);

			}
			else{
				num--;
				next(view);
			}
			}
			else{
				num--;
				tv.setText("");
	    		tt.setText("暂无收藏问题。");
			}
			db.close();
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
}
