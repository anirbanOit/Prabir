package com.oit.test.jasonparse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by OPTLPT049 on 9/1/2017.
 */

public class CustomAdepter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, String>> adpterlst;

    public CustomAdepter(Context context, ArrayList<HashMap<String, String>> list) {
        mContext = context;
        adpterlst = list;

    }

    @Override
    public int getCount() {
        return adpterlst.size();
    }

    @Override
    public Object getItem(int i) {
        return adpterlst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        HashMap<String,String> stringStringHashMap = adpterlst.get(i);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        String imageString=stringStringHashMap.get("photo");
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


        ImageView ivAvatar = convertView.findViewById(R.id.imv);
        ivAvatar.setImageBitmap(decodedImage);

        TextView obj=convertView.findViewById(R.id.name);
        obj.setText(stringStringHashMap.get("firstName"));
        Log.d("abc",""+stringStringHashMap.get("firstName"));

        TextView obj1 =convertView.findViewById(R.id.email);
        obj1.setText(stringStringHashMap.get("lastName"));

        TextView obj2 =convertView.findViewById(R.id.mobile);
        obj1.setText(stringStringHashMap.get("gender"));

        TextView obj3 =convertView.findViewById(R.id.dob);
        obj1.setText(stringStringHashMap.get("dob"));

        return convertView;
    }
}
