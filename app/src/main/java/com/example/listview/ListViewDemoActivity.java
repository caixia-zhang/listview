package com.example.listview;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listview.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListViewDemoActivity extends AppCompatActivity {

    public final int REQUEST_CODE_B = 1;
    public final int REQUEST_CODE_C = 2;
    private int mposition;
    private Button button;
    private Button btnadd;
    private EditText editThing;
    private EditText editDate;
    private ListView mphoneBookListView;
    private List<UserInfo> muserInfos;
    private View.OnClickListener onDelItem;
    PhoneBookAdapter mphoneBookAdapter;
    Calendar calendar=Calendar.getInstance();
    public SQLiteDatabase msqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);

        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        msqLiteDatabase = databaseHelper.getWritableDatabase();

        button=findViewById(R.id.btd1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ListViewDemoActivity.this,ItemaddActivity.class),REQUEST_CODE_B);
            }
        });

       mphoneBookListView = (ListView)findViewById(R.id.list_view);
        muserInfos = new ArrayList<>();
       // muserInfos.add(new UserInfo("XIOA","21"));

         btnadd=findViewById(R.id.btd1);
        editThing=findViewById(R.id.edit_text1);
         editDate=findViewById(R.id.edit_text2);
         mphoneBookAdapter=new PhoneBookAdapter(ListViewDemoActivity.this, muserInfos);
        mphoneBookListView.setAdapter(mphoneBookAdapter);
        mphoneBookAdapter.refreshData(muserInfos);

       // Cursor cursor=msqLiteDatabase.query(DatabaseHelper.TABLE_NAME,null,null,null,null,null,null);
        Cursor cursor=msqLiteDatabase.rawQuery("select * from "+DatabaseHelper.TABLE_NAME,null);
       if(cursor.moveToFirst())
        {
           do
                {
                String thingtext=cursor.getString((cursor.getColumnIndexOrThrow(DatabaseHelper.TEXTNAME)));
                String datetext=cursor.getString((cursor.getColumnIndexOrThrow(DatabaseHelper.DATE)));
                int progress=cursor.getInt((cursor.getColumnIndexOrThrow(DatabaseHelper.PROGRESS)));
                    String deldatetext=cursor.getString((cursor.getColumnIndexOrThrow(DatabaseHelper.DELDATE)));
                    byte[] bytes=cursor.getBlob(cursor.getColumnIndexOrThrow(DatabaseHelper.PHOTO));
                   // Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                muserInfos.add(new UserInfo(thingtext,datetext,progress,deldatetext,bytes));
                Log.i(MainActivity.class.getSimpleName(),":"+thingtext+".");
            }while (cursor.moveToNext());
        }

        mphoneBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mposition=position;
                startActivityForResult(new Intent(ListViewDemoActivity.this,progerssbar_setActivity.class),REQUEST_CODE_C);
            }
        });
        mphoneBookAdapter.refreshData(muserInfos);
     /* btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editDate.getText().toString().equals("")&&!editThing.getText().equals(""))
                {
               muserInfos.add(new UserInfo(editDate.getText().toString(),editThing.getText().toString()));
                    mphoneBookAdapter.refreshData(muserInfos);
                }
            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog();
            }
        });
        editDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDatePickDialog();
            }
        });*/
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(requestCode==REQUEST_CODE_B&&resultCode==Activity.RESULT_OK){

            muserInfos.add((UserInfo) data.getSerializableExtra("todo"));
            mphoneBookAdapter.refreshData(muserInfos);

        }
        if(requestCode==REQUEST_CODE_C&&resultCode==Activity.RESULT_OK)
        {
            muserInfos.get(mposition).setProgress(Integer.parseInt(data.getStringExtra("progress")));
            ContentValues contentValues=new ContentValues();
            contentValues.put(DatabaseHelper.PROGRESS,Integer.parseInt(data.getStringExtra("progress")));
            String whereClause="textname=?";
            String[] wherewhat={muserInfos.get(mposition).getmUserName()};
            msqLiteDatabase.update(DatabaseHelper.TABLE_NAME,contentValues,whereClause,wherewhat);
            mphoneBookAdapter.refreshData(muserInfos);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
  /*  private void showDatePickDialog()//时间选择器
    {

        new DatePickerDialog(ListViewDemoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                editDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }*/
}
