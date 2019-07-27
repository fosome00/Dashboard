package com.example.dashboard.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dashboard.Model.DataModel;
import com.example.dashboard.R;
import com.example.dashboard.constant.DataConst;

import static com.example.dashboard.constant.DataConst.*;

public class InputDialog extends DialogFragment {

    EditText name,pincode,address;
    Spinner type,size;
    Button submit;
    String cityName, typeValue, sizeValue;
    TextView city;
    DialogListener listener;
    Context context;

    public InputDialog(String cityName, Context context) {
        this.cityName = cityName;
        this.context = context;
        typeValue = DataConst.type[0];
        sizeValue = DataConst.size[0];
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.d("ColumnDialog Interface", "onAttach: "+e.getMessage() + " *** "+context);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.input_dialog,container,false);
        name = view.findViewById(R.id.outletName);
        city = view.findViewById(R.id.cityName);
        city.setText(cityName);
        pincode = view.findViewById(R.id.pincode);
        address = view.findViewById(R.id.address);
        type = view.findViewById(R.id.outletType);
        size = view.findViewById(R.id.outletSize);
        submit = view.findViewById(R.id.submit);

        ArrayAdapter sizeAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, DataConst.size);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        size.setAdapter(sizeAdapter);

        final ArrayAdapter typeAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item, DataConst.type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        view.findViewById(R.id.dialog_button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().hide();
            }
        });

        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sizeValue = DataConst.size[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeValue = DataConst.type[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataModel newOutlet = new DataModel(name.getText().toString(),typeValue,sizeValue,cityName,address.getText().toString(),pincode.getText().toString());
                Log.d("newData", "onClick: "+newOutlet);
                listener.data(newOutlet);
                getDialog().dismiss();
            }
        });
        return view;
    }

    public interface DialogListener {
        void data(DataModel data);
    }
}
