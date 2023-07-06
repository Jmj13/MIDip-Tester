package com.jagrutijadhav.ajp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;

public class DatabaseAccess{
    private static CodeView codeText;
    private static TextView queno,quetext;
    private static RadioButton option1,option2,option3,option4;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    private static TextToSpeech textToSpeech;
    public String jagu;
    Cursor c=null;

    private DatabaseAccess(Context context){
        this.openHelper=new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context,TextToSpeech speech,TextView que_text, CodeView que_code, TextView quetionno , RadioButton opt1, RadioButton opt2, RadioButton opt3, RadioButton opt4){
        quetext=que_text;
        codeText=que_code;
        option1=opt1;
        option2=opt2;
        option3=opt3;
        option4=opt4;
        textToSpeech=speech;
        //  scoretext=s;
        queno=quetionno;
        if(instance==null){
            instance =new DatabaseAccess(context);
        }
        return instance;
    }

    public static DatabaseAccess getInstance1(Context context){
        if(instance==null){
            instance =new DatabaseAccess(context);
        }
        return instance;
    }


    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public String getAddress(String subject,int i,int chapter_no,int testlevel){

        if(subject.equals("EST")) {
            c = db.rawQuery("select * from est where ChapterNo=" + chapter_no + " AND TestNo=" + testlevel + " AND QueNo=" + i, new String[]{});
        }else if(subject.equals("MAN")){
            c=db.rawQuery("select * from man where ChapterNo="+chapter_no+" AND TestNo="+testlevel+" AND QueNo="+i,new String[]{});
        }
        else{
            c=db.rawQuery("select * from testlevel where ChapterNo="+chapter_no+" AND TestNo="+testlevel+" AND QueNo="+i,new String[]{});
        }
        StringBuffer buffer=new StringBuffer();

        if(c.moveToNext()){
            Log.e("testleve", testlevel+"chapter_no "+chapter_no+"i="+i);
            //Log.e("subject= ", subject+"");
            String address=c.getString(8);
            jagu=(c.getString(3)+"");
            if(testlevel==5&&subject.equals("AJP")){
                quetext.setVisibility(View.INVISIBLE);
                codeText.setTheme(CodeViewTheme.ANDROIDSTUDIO).getCodeBackgroundColor();
                codeText.showCode(jagu);
            }
            else{
                codeText.setVisibility(View.INVISIBLE);
                quetext.setText(jagu);

            }
            queno.setText(c.getInt(2)+"");
            option1.setText(c.getString(4)+"");
            option2.setText(c.getString(5)+"");
            option3.setText(c.getString(6)+"");
            option4.setText(c.getString(7)+"");
            buffer.append(""+address);

        }
        return buffer.toString();

    }
    public void  voicechek(int speed){
        if(speed==1) {
            int j = textToSpeech.speak(jagu + "\noption: 1. " + c.getString(4) + "\n\noption: 2. " + c.getString(5) + "\n\n option: 3. " + c.getString(6)
                    + "\n\noption: 4. " + c.getString(7), TextToSpeech.QUEUE_FLUSH, null);
        }

    }
}
