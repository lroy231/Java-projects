package com.example.hw04;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class AppDetailsFragment extends Fragment {
    // INCLASS 05 ASSIGNMENT
// LEROY PHOMMA AND BINLY KEONAKHONE
    TextView appName;
    TextView artistName;
    TextView releaseDate;
    ListView listView;
    ArrayList<String> app_details = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static final String ARG_PARAM1 = "param1";

    private DataServices.App obj_Token;

    public AppDetailsFragment() {
        // Required empty public constructor
    }



    public static AppDetailsFragment newInstance(DataServices.App obj_Token) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) obj_Token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            obj_Token = (DataServices.App) getArguments().getSerializable(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_details, container, false);

        appName = view.findViewById(R.id.detail_AppName);
        artistName = view.findViewById(R.id.detail_ArtistName);
        releaseDate = view.findViewById(R.id.details_ReleaseDate);

        appName.setText(obj_Token.name);
        artistName.setText(obj_Token.artistName);
        releaseDate.setText(obj_Token.releaseDate);

        app_details.clear();
        app_details.addAll(obj_Token.genres);

        listView = view.findViewById(R.id.details_ListView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, app_details);
        listView.setAdapter(adapter);

        Log.d("tag", "empty? " + obj_Token.toString() );


        return view;
    }
}