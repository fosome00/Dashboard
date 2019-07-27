package com.example.dashboard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dashboard.R;

import java.util.ArrayList;

public class CityListAdapter extends BaseAdapter {


    ArrayList<String> cityList;

    public CityListAdapter(ArrayList<String> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
    }

    private Context context;

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class viewHolder {

        TextView cityName;
        viewHolder(View itemView) {
            cityName = itemView.findViewById(R.id.cityName);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row  = view;
        viewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.city_view, viewGroup, false);
            holder = new viewHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (viewHolder) row.getTag();
        }

        holder.cityName.setText(cityList.get(i));

        return row;
    }
}
