package com.example.cansu.gezgor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TilesFormatter extends BaseAdapter {

    private Context mContext;
    LayoutInflater inflater;

    Constants places = new Constants();
    Typeface robotobold;

    String colors[] = {"#ff0065", "#00e5ff", "#00ff9b", "#e4ff00", "#00ff1b", "#67005c", "#670029", "#00673f", "#ff1b00", "#00a6e6", "#3f51b5", "#9c27b0", "#673ab7"};
    public TilesFormatter(Context c) {
        mContext = c;
        robotobold = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Bold.ttf");
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return places.places_list.length;
    }

    @Override
    public Object getItem(int position) {
        return places.places_list[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String place_id = places.places_list[position];
        String icon_id = places.places.get(place_id);

        convertView = inflater.inflate(R.layout.grid_item, parent, false);
        if(convertView == null){

        }else {
            try {
                ImageView place_img = (ImageView) convertView.findViewById(R.id.place_img);
                TextView place_text = (TextView) convertView.findViewById(R.id.place_text);
                place_text.setTypeface(robotobold);
                place_img.setBackgroundColor(Color.parseColor(colors[position % 13]));
                if (icon_id != null) {
                    Drawable drawable = mContext.getResources().getDrawable(getDrawable(mContext, icon_id));
                    place_img.setImageDrawable(drawable);
                }

                if(place_id == "grocery_or_supermarket"){
                    place_id = "supermarket";
                }
                place_text.setText(place_id.toUpperCase().replace("_", " "));
            } catch (Exception e) {
                Log.e("Places", place_id);
            }
        }
        return convertView;
    }

    public static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}

