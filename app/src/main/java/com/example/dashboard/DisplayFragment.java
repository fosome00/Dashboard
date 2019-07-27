package com.example.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dashboard.DialogFragment.InputDialog;
import com.example.dashboard.Model.DataModel;
import com.example.dashboard.adpater.OutletAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;


public class DisplayFragment extends Fragment implements InputDialog.DialogListener{

    GridView gv;
    OutletAdapter adapter;
    String cityName;
    TextView message;
    FloatingActionButton newOutlet;
    Gson gson = new Gson();
    private ArrayList<DataModel> cityList;
    public DisplayFragment() {
        // Required empty public constructor

    }

    public DisplayFragment(String cityName) {
        this.cityName = cityName;

        Log.d("CityName", "DisplayFragment: "+cityName);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("CityName", "onCreateView: "+cityName);
        View view =  inflater.inflate(R.layout.fragment_display, container, false);
        gv = view.findViewById(R.id.listOutlets);
        newOutlet = view.findViewById(R.id.newOutletDialog);
        message = view.findViewById(R.id.emptyListMessage);
        cityList = outletList(cityName);
        adapter = new OutletAdapter(getActivity(), outletList(cityName));
        check();
        newOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputDialog dialog = new InputDialog(cityName, getContext());
                dialog.setTargetFragment(DisplayFragment.this,2);
                dialog.show(getFragmentManager(),"open dialog");
            }
        });

        return view;
    }

    private void check() {
        if (outletList(cityName).size() > 0) {
            gv.setVisibility(View.VISIBLE);
            message.setVisibility(View.GONE);
            gv.setAdapter(adapter);
        }
        else {
            gv.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
        }
    }

    private void addNewFile(DataModel data) {


        String jsonData = gson.toJson(data);
        Log.d("File", "addNewFile: parse to json "+jsonData);
        File file = createNewFile(data.getOutletName());
        if (file !=null) {
            writeOnFile(file,jsonData);
        }
        else
            Log.d("File", "addNewFile: "+"file not created");
        if (cityList == null) {
            cityList = new ArrayList<>();
            Log.d("File", "addNewFile: "+cityList);
        }
        else {
            Log.d("File", "addNewFile: "+cityList);
        }
        cityList.add(data);

        adapter.data = cityList;
        check();
        gv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("File", "addNewFile: "+cityList);

    }

    private File createNewFile(String fileName) {
        try {
            File dir = getActivity().getFilesDir();
            if (!Arrays.asList(dir.list()).contains(cityName)) {
                File temp = new File(getActivity().getFilesDir()+File.separator+cityName);
                temp.mkdirs();
                dir = temp;
                Log.d("File", "createNewDir: "+dir);
            }
            else {
                dir = new File(getActivity().getFilesDir()+File.separator+cityName);
                Log.d("File", "createNewDir: file exist "+dir);
            }

            if(!checkFileExist(dir, fileName)) {
                File file = new File(dir,fileName+".txt");
                file.createNewFile();
                Log.d("File", "createNewFile: "+file);
                return file;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeOnFile(File file,String jsonData) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(jsonData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
            Log.d("FileDataInsert", "onCreate: Date Inserted Successfully "+file);
        }catch(FileNotFoundException ex)
        {
            Log.e("writeData", ex.getMessage(), ex);
        }catch(IOException ex)
        {
            Log.e("writeRead", ex.getMessage(), ex);
        }
    }

    private boolean checkFileExist(File file, String fileName) {
        try {
            if (Arrays.asList(file.list()).contains(fileName+".txt")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private DataModel fileContent(String fileName, File dir) {

        File file = new File(dir,fileName);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(fileReader);
        String valTempRead = null;
        DataModel model = new DataModel();
        try {
            while((valTempRead = reader.readLine()) != null) {
                Log.d("File", "fileContent: " + valTempRead);

                model = gson.fromJson(valTempRead,DataModel.class);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return model;
    }

    private ArrayList<DataModel> outletList(String cityName) {
        ArrayList<DataModel> cityListTemp = new ArrayList<>();
        try {
            File dir = new File(getActivity().getFilesDir()+File.separator+cityName);
            for (String temp:dir.list()) {
                DataModel model = fileContent(temp, dir);
                if (model != null) {
                    cityListTemp.add(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cityListTemp.remove(null);
        return cityListTemp;
    }

    @Override
    public void data(DataModel data) {
        Log.d("Listener", "data: "+data.getOutletName()+data.getType()+data.getSize()+data.getPincode()+data.getAddress());
        addNewFile(data);
    }
}
