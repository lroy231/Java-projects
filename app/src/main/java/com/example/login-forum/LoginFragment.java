package com.example.hw04;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class LoginFragment extends Fragment {

    public EditText editEmail;
    public EditText editPassword;
    public Button login_Button;
    public TextView create_New;

    public LoginListener listener;



    public interface LoginListener{
        void goToAppCategory(String token);
        void goToRegisterAccount();
    }

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editEmail = view.findViewById(R.id.register_Email);
        editPassword = view.findViewById(R.id.register_Password);
        login_Button = view.findViewById(R.id.edit_Submit);
        create_New = view.findViewById(R.id.create_NewAcc);


        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                if(editEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Email field is missing", Toast.LENGTH_SHORT).show();
                } else if (editPassword.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Password field is missing", Toast.LENGTH_SHORT).show();
                } else{
                    new MySyncTask().execute(email, password);
                }

                }

            });


        create_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.goToRegisterAccount();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof LoginListener){
            listener = (LoginListener) context;
        } else{
            throw new RuntimeException(context.toString()
                    + " must implement login listener");
        }
    }

    class MySyncTask extends AsyncTask<String, Void, String>{
        String result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String token) {

            listener.goToAppCategory(token);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                result = DataServices.login(strings[0], strings[1]);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return result;
        }
    }



}