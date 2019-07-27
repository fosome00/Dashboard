package com.example.dashboard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dashboard.Model.DataModel;
import com.example.dashboard.R;

import java.util.ArrayList;

public class OutletAdapter extends BaseAdapter {

    Context context;
    public ArrayList<DataModel> data;

    public OutletAdapter(Context context, ArrayList<DataModel> data) {
        this.context = context;
        this.data = data;
    }

    class viewHolder {
        TextView outletName, type, size, address, pincode, city;
        viewHolder(View itemView) {
            outletName = itemView.findViewById(R.id.display_outlet_name);
            type = itemView.findViewById(R.id.display_type);
            size = itemView.findViewById(R.id.display_size);
            address = itemView.findViewById(R.id.display_address);
            pincode = itemView.findViewById(R.id.display_pincode);
            city = itemView.findViewById(R.id.display_city);
        }

    }

    @Override
    public int getCount() {
        if (data == null)
        {
            data = new ArrayList<>();
        }
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row  = view;
        viewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.detail_dialog, viewGroup, false);
            holder = new viewHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (viewHolder) row.getTag();
        }

        if (data.get(i) != null && data.size() != 0 && data != null && holder != null) {
            holder.outletName.setText(data.get(i).getOutletName());
            holder.address.setText(data.get(i).getAddress());
            holder.pincode.setText(data.get(i).getPincode());
            holder.size.setText(data.get(i).getSize());
            holder.type.setText(data.get(i).getType());
            holder.city.setText(data.get(i).getCity());
        }

        return row;
    }
}
