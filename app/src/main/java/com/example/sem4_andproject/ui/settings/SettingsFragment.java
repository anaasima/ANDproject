package com.example.sem4_andproject.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;


import com.example.sem4_andproject.R;

public class SettingsFragment extends Fragment {
    Spinner dateSpinner;
    SharedPreferences sharedPreferences;
    String selectedItem;

    CheckBox duskButton;
    CheckBox dawnButton;

    ImageView duskImage;
    ImageView dawnImage;
    String selectedTheme;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setDateSpinner(view);
        setElements(view);
        setSharedPreferences(view);
        return view;
    }
    public void setElements(View view) {
        duskButton = view.findViewById(R.id.dusk);
        dawnButton = view.findViewById(R.id.dawn);

        duskImage = view.findViewById(R.id.duskImage);
        dawnImage = view.findViewById(R.id.dawnImage);

        duskButton.isChecked();
        duskImage.setVisibility(View.VISIBLE);

        duskButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dawnButton.setChecked(false);
                    dawnImage.setVisibility(View.GONE);
                    duskImage.setVisibility(View.VISIBLE);

                    selectedTheme = "dusk";
                }
                if (!isChecked)
                {
                    dawnButton.setChecked(true);
                    duskImage.setVisibility(View.GONE);
                    dawnImage.setVisibility(View.VISIBLE);

                    selectedTheme = "dawn";
                }
            }
        });
        dawnButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    duskButton.setChecked(false);
                    duskImage.setVisibility(View.GONE);
                    dawnImage.setVisibility(View.VISIBLE);

                    selectedTheme = "dawn";
                }
                if (!isChecked)
                {
                    duskButton.setChecked(true);
                    dawnImage.setVisibility(View.GONE);
                    duskImage.setVisibility(View.VISIBLE);

                    selectedTheme = "dusk";
                }
            }
        });
    }
    public void setDateSpinner(View view) {
        dateSpinner = view.findViewById(R.id.spinnerDateFormat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.strings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);

       dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedItem = parent.getItemAtPosition(position).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }
    public void setSharedPreferences(View view) {
        sharedPreferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dateFormat", selectedItem);
        editor.putString("themeFormat", selectedTheme);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
    public void getData()
    {
        String selectedFormat = sharedPreferences.getString("dateFormat", "dd/mm/yyyy");
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter) dateSpinner.getAdapter();
        int position = adapter.getPosition(selectedFormat);
        dateSpinner.setSelection(position);

        String themeFormat = sharedPreferences.getString("themeFormat", "dusk");
        if (themeFormat.equals("dusk"))
        {
            dawnButton.setChecked(false);
            duskButton.setChecked(true);
            dawnImage.setVisibility(View.GONE);
            duskImage.setVisibility(View.VISIBLE);
        }
        else
        {
            duskButton.setChecked(false);
            dawnButton.setChecked(true);
            duskImage.setVisibility(View.GONE);
            dawnImage.setVisibility(View.VISIBLE);

        }

    }
}