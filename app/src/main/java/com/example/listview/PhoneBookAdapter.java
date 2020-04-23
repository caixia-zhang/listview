package com.example.listview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.listview.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PhoneBookAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<UserInfo> mUserInfos=new ArrayList<>();
    public PhoneBookAdapter(Context context,List<UserInfo> userInfos)
    {
        mContext=context;
        mUserInfos=userInfos;
        mLayoutInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        //有多少条数据
        return mUserInfos.size();
    }
    @Override
    public Object getItem(int position) {
        //返回某一条数据对象
        return mUserInfos.get(position);
    }
    @Override
    public long getItemId(int position) {
        //
        return position;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //返回一个视图

        ViewHolder viewHolder;
        if(convertView==null)
        {
            convertView =mLayoutInflater.inflate(R.layout.item_phone_book_friend,null);
            viewHolder=new ViewHolder();
            //获取控件
            viewHolder.nameTextView=(TextView)convertView.findViewById(R.id.name_text_view);
            viewHolder.dateTextView=(TextView)convertView.findViewById(R.id.date_text_view);
            viewHolder.btddel=(Button)convertView.findViewById(R.id.del_date_things);
            viewHolder. progressBar=convertView.findViewById(R.id.progressBar);
            viewHolder. progressBar_text=convertView.findViewById(R.id.progressBar_text);
            viewHolder.date_delTextView=convertView.findViewById(R.id.deldate_text_view);
            viewHolder.imageView=convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);
        }else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //和数据绑定
        int date=Integer.parseInt(mUserInfos.get(position).getmDate().replaceAll("/",""));
        int deldate=Integer.parseInt(mUserInfos.get(position).getmdata_del().replaceAll("/",""));
        if(date>deldate)
            viewHolder.nameTextView.setTextColor(Color.rgb(255, 0, 0));
        viewHolder.dateTextView.setText("起止日期"+mUserInfos.get(position).getmDate());
        viewHolder.nameTextView.setText("事项："+mUserInfos.get(position).getmUserName());
        viewHolder.progressBar.setProgress(mUserInfos.get(position).getProgress());
        viewHolder.btddel.setTag(position);
       viewHolder.date_delTextView.setText("截止日期"+mUserInfos.get(position).getmdata_del());
        byte[] b=mUserInfos.get(position).getMbitmap();//byte转换为bitmap，
        /*//将byte[]类型转换成Bitmap类型
            byte[] b = getIntent().getByteArrayExtra("bitmap");
                  Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

               //Bitmap转换成byte[]
                  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                  byte[] datas = baos.toByteArray();
*/
       viewHolder.imageView.setImageBitmap( BitmapFactory.decodeByteArray(b,0,b.length));
        notifyDataSetChanged();
        viewHolder.btddel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mUserInfos.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
    class ViewHolder
    {
        TextView nameTextView;
        TextView dateTextView;
       Button btddel;
       ProgressBar progressBar;
       TextView  progressBar_text;
       TextView  date_delTextView;
       ImageView imageView;

    }
    //刷新数据

    public void refreshData(List<UserInfo> userInfos)
    {
        mUserInfos=userInfos;
        notifyDataSetChanged();
    }



}
