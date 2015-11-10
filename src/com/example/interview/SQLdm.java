package com.example.interview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * ��������ʵ�ִ�assetsĿ¼��ȡ���ݿ��ļ�Ȼ��д��SDcard��,�����SDcard�д��ڣ��ʹ����ݿ⣬�����ھʹ�assetsĿ¼�¸��ƹ�ȥ
 * @author Trenson
 *
 */
public class SQLdm {  
    
  //���ݿ�洢·��  
  String filePath = "data/data/com.example.interview/test.db";  
  String pathStr = "data/data/com.example.interview";  
  boolean flag=false;  
  SQLiteDatabase database;   
  public  SQLiteDatabase openDatabase(Context context){  
    System.out.println("filePath:"+filePath);  
    File jhPath=new File(filePath);  
      //�鿴���ݿ��ļ��Ƿ����  
      if(jhPath.exists()&&flag==true){  
        Log.i("test", "�������ݿ�");
        //������ֱ�ӷ��ش򿪵����ݿ�  
        return SQLiteDatabase.openOrCreateDatabase(jhPath, null);  
      }else{  
        //�������ȴ����ļ���  
        File path=new File(pathStr);  
        Log.i("test", "pathStr="+path);
        if (path.mkdir()){  
          Log.i("test", "�����ɹ�"); 
        }else{  
          Log.i("test", "����ʧ��");
        };  
        try {  
          //�õ���Դ  
          AssetManager am= context.getAssets();  
          //�õ����ݿ��������  
          InputStream is=am.open("test.db");  
          Log.i("test", is+"");
          //�������д��SDcard����	
          FileOutputStream fos=new FileOutputStream(jhPath);  
          Log.i("test", "fos="+fos);
          Log.i("test", "jhPath="+jhPath);
          //����byte����  ����1KBдһ��  
          byte[] buffer=new byte[1024];  
          int count = 0;  
          while((count = is.read(buffer))>0){  
            Log.i("test", "�õ�");
            fos.write(buffer,0,count);  
          }  
          //���رվͿ�����  
          fos.flush();  
          fos.close();  
          is.close();  
        } catch (IOException e) {  
          // TODO Auto-generated catch block  
          e.printStackTrace();  
          return null;
        }  
        flag=true;
        //���û��������ݿ�  �����Ѿ�����д��SD�����ˣ�Ȼ����ִ��һ��������� �Ϳ��Է������ݿ���  
        return openDatabase(context);  
      }  
  }  
}
