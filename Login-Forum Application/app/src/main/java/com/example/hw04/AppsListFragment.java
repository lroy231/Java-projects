package com.example.hw04;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;



import java.util.ArrayList;


public class AppsListFragment extends Fragment {

    ListView listView;
    ArrayList<DataServices.App> categories_Apps = new ArrayList<>();
    AppAdapter adapter;
    private static final String ARG_PARAM_M_TOKEN = "ARG_PARAM_M_TOKEN";
    private static final String ARG_PARAM_APP_TOKEN = "ARG_PARAM_APP_TOKEN";

    private String mToken;
    private String mAppToken;

    AppsListListener aListener;

    interface AppsListListener{
        void ObjToDetails(DataServices.App obj);
    }

    public AppsListFragment() {
        // Required empty public constructor
    }


    public static AppsListFragment newInstance(String mToken, String mAppToken) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_M_TOKEN, mToken);
        args.putString(ARG_PARAM_APP_TOKEN, mAppToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_PARAM_M_TOKEN);
            mAppToken = getArguments().getString(ARG_PARAM_APP_TOKEN);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);

        listView = view.findViewById(R.id.listView);


        new AppsListTask().execute(mToken, mAppToken);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        aListener = (AppsListListener) context;
    }

    class AppsListTask extends AsyncTask<String, Void, ArrayList<DataServices.App>> {
        ArrayList<DataServices.App> result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<DataServices.App> data) {
            categories_Apps.clear();
            categories_Apps.addAll(data);
            Log.d("TAG", "onPostExecute: " + data);
            adapter = new AppAdapter(getActivity(), R.layout.app_layout, categories_Apps);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("Tag", "onItemClick: position" + position);
                    DataServices.App selected_App = data.get(position);
                    aListener.ObjToDetails(selected_App);
                }
            });
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<DataServices.App> doInBackground(String... strings) {
            try {
               result = DataServices.getAppsByCategory(strings[0], strings[1]);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}