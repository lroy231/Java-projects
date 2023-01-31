package com.example.hw04;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;




import java.util.ArrayList;

public class AppCategoryFragment extends Fragment {
    TextView welcome;
    ListView listView;
    ArrayList<String> categories = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private static final String ARG_PARAM_TOKEN = "ARG_PARAM_TOKEN";

    private String mToken;
    String category;

    public AppCategoryFragment() {
        // Required empty public constructor
    }

    public static AppCategoryFragment newInstance(String token) {
        AppCategoryFragment fragment = new AppCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mToken = getArguments().getString(ARG_PARAM_TOKEN);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_category, container, false);
        welcome = view.findViewById(R.id.welcome);
        listView = view.findViewById(R.id.list_View);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, categories);
        listView.setAdapter(adapter);

        new GetNameTask().execute(mToken);
        new AppCategoryTask().execute(mToken);



        view.findViewById(R.id.logout_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.doLogout();
            }
        });


        return view;
    }

    AppCategoriesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AppCategoriesListener) context;
    }


    interface AppCategoriesListener{
        void toAppsListFragment(String mToken, String appToken);
        void doLogout();
    }
    class GetNameTask extends AsyncTask<String, Void, DataServices.Account>{
        DataServices.Account result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(DataServices.Account account) {
                welcome.setText(account.getName());
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected DataServices.Account doInBackground(String... strings) {
            try {
               result = DataServices.getAccount(strings[0]);
                Log.d("TAG", "doInBackground: " + strings[0]);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class AppCategoryTask extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> result;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            Log.d("TAG", "onPostExecute: " + strings);
                categories.clear();
                categories.addAll(strings);
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                        android.R.id.text1, categories);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selected_Category = categories.get(position);
                        mListener.toAppsListFragment(mToken, selected_Category);
                    }
                });
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                result = DataServices.getAppCategories(strings[0]);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}