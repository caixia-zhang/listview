package com.example.listview;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.listview.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;

public class ItemaddActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnadd;
    private EditText editThing;
    private EditText editDate;
    private UserInfo userInfo;
    public SQLiteDatabase msqLiteDatabase;
    private View.OnClickListener onDelItem;
    PhoneBookAdapter mphoneBookAdapter;
    Calendar calendar=Calendar.getInstance();
    public DatabaseHelper mdatabaseHelper;
    public EditText editdel_date;
    public Button takephoto_btd;
    public ImageView photo;
    public Uri imageUri;
    public String  photoSavePath;;
    private String photoSaveName;
    public Bitmap mbitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_add_activity);
        btnadd=findViewById(R.id.btd1_add);
        editThing=findViewById(R.id.edit_text1_add);
        editDate=findViewById(R.id.edit_text2_add);
        editdel_date = findViewById(R.id.edit_textdel_add);
        takephoto_btd = findViewById(R.id.takephoto);
        photo = findViewById(R.id.photo);
        takephoto_btd.setOnClickListener(this);
        editdel_date.setOnClickListener(this);
        editDate.setOnClickListener(this);
        btnadd.setOnClickListener(this);
        editDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDatePickDialog(editDate);
            }
        });
        editdel_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showDatePickDialog(editdel_date);
            }
        });
        //解决android.os.FileUriExposedException:
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        initData();
    }
    private void initData() {
        File file = new File(Environment.getExternalStorageDirectory(), "heheda");
        if (!file.exists()) file.mkdirs();
        photoSavePath = Environment.getExternalStorageDirectory() + "/heheda/";
    }
    public void onClick(View v){
        mdatabaseHelper = new DatabaseHelper(this);
        msqLiteDatabase = mdatabaseHelper.getWritableDatabase();
        switch (v.getId()){

            case R.id.edit_textdel_add:
                showDatePickDialog(editdel_date);
                break;
            case R.id.edit_text2_add:
                showDatePickDialog(editDate);
                break;
            case R.id.btd1_add:
                String datetext=editDate.getText().toString();
                String thingtext=editThing.getText().toString();
                String datadeltext=editdel_date.getText().toString();
              Bitmap bitmap=rotateImage(mbitmap, 90);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
                byte[] datas=baos.toByteArray();
                if(!datetext.equals("")&&!thingtext.equals(""))
                {
                    userInfo=new UserInfo(thingtext,datetext,datadeltext,datas);
                }
                ContentValues contentValues=new ContentValues();
                contentValues.put(DatabaseHelper.TEXTNAME,userInfo.getmUserName());
                contentValues.put(DatabaseHelper.DATE,userInfo.getmDate());
                contentValues.put(DatabaseHelper.PROGRESS,userInfo.getProgress());
                contentValues.put(DatabaseHelper.DELDATE,userInfo.getmdata_del());
                contentValues.put(DatabaseHelper.PHOTO,userInfo.getMbitmap());
                msqLiteDatabase.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
                Intent intent=new Intent();
                intent.putExtra("todo",userInfo);
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
            case R.id.takephoto:
                if  (ContextCompat.checkSelfPermission(ItemaddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {
                    //有权限，直接拍照
                    takePhoto();
                }
                break;
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                takePhoto();
            } else {
                Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void showDatePickDialog(EditText editDate)//时间选择器
    {

        new DatePickerDialog(ItemaddActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                editDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void takePhoto() {
        photoSaveName = System.currentTimeMillis() + ".png";
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = Uri.fromFile(new File(photoSavePath, photoSaveName));
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
            if(resultCode== Activity.RESULT_OK)
            {    Log.e("Camera", "onActivityResult: 拍照成功");
                String path = photoSavePath + photoSaveName;
                File file = new File(path);
                if (file.exists()) {
                    Log.e("Camera", "onActivityResu" +
                            "lt: file文件存在" );
                    mbitmap = BitmapFactory.decodeFile(path);


                    /*Matrix matrix = new Matrix();
                    matrix.setScale(0.5f, 0.5f);
                    Bitmap bm = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);*/
                    photo.setImageBitmap( rotateImage(mbitmap,90));
                }
            }

    }
    public Bitmap rotateImage(Bitmap bitmap, float degree)
    {    //create new matrix  解决横屏问题
         Matrix matrix = new Matrix();
         matrix.postRotate(degree);
         Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
         return bmp;
    }

}
/*
*/
