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



public class RegisterFragment extends Fragment {

    private EditText name;
    private EditText email;
    private EditText password;
    private Button submit;
    private TextView cancel;

    private RegisterListener listener;


    public interface RegisterListener {
        void RegisterAppCategory(String token);
        void cancel_Register();
    }


    public RegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        name = view.findViewById(R.id.register_Name);
        email = view.findViewById(R.id.register_Email);
        password = view.findViewById(R.id.register_Password);

        submit = view.findViewById(R.id.submit_Button);
        cancel = view.findViewById(R.id.cancel_Button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registered_Name = name.getText().toString();
                String registered_Email = email.getText().toString();
                String registered_Password = password.getText().toString();
                if (!name.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    new register_Task().execute(registered_Name, registered_Email, registered_Password);
                }
              }
            });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancel_Register();
            }
        });

        return view;
        }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.LoginListener) {
            listener = (RegisterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement login listener");
        }
    }

    class register_Task extends AsyncTask<String, Void, String> {
        String result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String register_token) {
            listener.RegisterAppCategory(register_token);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
              result = DataServices.register(strings[0], strings[1], strings[2]);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

}