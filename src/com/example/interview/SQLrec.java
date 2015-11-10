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
public class SQLrec {  
    
  //���ݿ�洢·��  
  String filePath2 = "data/data/com.example.interview/rec.db";  
  String pathStr2 = "data/data/com.example.interview";  
    
  SQLiteDatabase database;   
  public  SQLiteDatabase openDatabase(Context context){  
    System.out.println("filePath:"+filePath2);  
    File jhPath=new File(filePath2);  
      //�鿴���ݿ��ļ��Ƿ����  
      if(jhPath.exists()){  
        Log.i("rec", "�������ݿ�");
        //������ֱ�ӷ��ش򿪵����ݿ�  
        return SQLiteDatabase.openOrCreateDatabase(jhPath, null);  
      }else{  
        //�������ȴ����ļ���  
        File path=new File(pathStr2);  
        Log.i("rec", "pathStr="+path);
        if (path.mkdir()){  
          Log.i("rec", "�����ɹ�"); 
        }else{  
          Log.i("rec", "����ʧ��");
        };  
        try {  
          //�õ���Դ  
          AssetManager am= context.getAssets();  
          //�õ����ݿ��������  
          InputStream is=am.open("rec.db");  
          Log.i("rec", is+"");
          //�������д��SDcard����	
          FileOutputStream fos=new FileOutputStream(jhPath);  
          Log.i("rec", "fos="+fos);
          Log.i("rec", "jhPath="+jhPath);
          //����byte����  ����1KBдһ��  
          byte[] buffer=new byte[1024];  
          int count = 0;  
          while((count = is.read(buffer))>0){  
            Log.i("rec", "�õ�");
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
        //���û��������ݿ�  �����Ѿ�����д��SD�����ˣ�Ȼ����ִ��һ��������� �Ϳ��Է������ݿ���  
        return openDatabase(context);  
      }  
  }  
}
