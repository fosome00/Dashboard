package com.example.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.dashboard.adpater.CityListAdapter;
import com.example.dashboard.constant.DataConst;

import java.util.ArrayList;

public class CityListFragment extends Fragment {

    private GridView gv;
    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        gv = view.findViewById(R.id.cityList);
        gv.setNumColumns(2);
        ArrayList<String> city = getCityList();
        CityListAdapter adapter = new CityListAdapter(city, getActivity());
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DisplayFragment(getCityList().get(i))).addToBackStack("cityFragment").commit();
            }
        });
        return view;
    }

    private ArrayList<String> getCityList() {

        return DataConst.cityList;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}

