package com.oit.test.getdata;

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

/**
 * Created by OPTLPT049 on 9/6/2017.
 */

public class CustomAdepter extends BaseAdapter  {
    private Context mContext;
    private ArrayList<Model> adpterlst;


    public CustomAdepter(Context context, ArrayList<Model> list) {
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

        Model listItems = adpterlst.get(i);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.listview, null);
        }
        String imageString = listItems.getPhoto();
        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        ImageView ivAvatar = convertView.findViewById(R.id.imv);
        ivAvatar.setImageBitmap(decodedImage);

        TextView obj = convertView.findViewById(R.id.text1);
        obj.setText(listItems.getFirstName());
        Log.d("abc", "" + listItems.getFirstName());

        TextView obj1 = convertView.findViewById(R.id.text2);
        obj1.setText(listItems.getLastName());

        TextView obj2 = convertView.findViewById(R.id.text3);
        obj2.setText(listItems.getGender());

        TextView obj3 = convertView.findViewById(R.id.text4);
        obj3.setText(listItems.getDob());

        TextView obj4 = convertView.findViewById(R.id.text5);
        obj4.setText(listItems.getDept());

        return convertView;

    }
}
