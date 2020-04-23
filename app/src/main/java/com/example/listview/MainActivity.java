package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private TextView textView;
    private Button btnadd;
    private EditText editThing;
    private EditText editDate;

    private List<UserInfo> muserInfos;
    private View.OnClickListener onDelItem;
    PhoneBookAdapter mphoneBookAdapter;
    Calendar calendar=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       textView.findViewById(R.id.text);
       textView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent();
               intent.putExtra("todo","12");
               intent.putExtra("todo1","34");
               setResult(Activity.RESULT_OK,intent);
               finish();
           }
       });
    }


}
