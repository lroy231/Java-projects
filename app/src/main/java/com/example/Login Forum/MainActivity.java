package com.example.hw04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
//HOMEWORK 4
// HW04
// LEROY PHOMMA AND BINLY KEONAKHONE
public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, AppCategoryFragment.AppCategoriesListener, RegisterFragment.RegisterListener, AppsListFragment.AppsListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Login");

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment())
                .commit();

    }
    String mToken;
    String mAppToken;
    String category;
    DataServices.App object;

    @Override
    public void goToAppCategory(String token) {
        this.mToken = token;
        setTitle("App Categories");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, AppCategoryFragment.newInstance(mToken))
                .commit();
    }

    @Override
    public void goToRegisterAccount() {
        setTitle("Register Account");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, new RegisterFragment())
                .commit();

    }


    @Override
    public void toAppsListFragment(String mToken, String appNameToken) {
        this.mToken = mToken;
        this.mAppToken = appNameToken;
        setTitle(appNameToken);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, AppsListFragment.newInstance(mToken, mAppToken))
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void doLogout() {
        setTitle("Login");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }


    @Override
    public void RegisterAppCategory(String token) {
        this.mToken = token;
        setTitle("App Categories");
        getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerView, AppCategoryFragment.newInstance(mToken))
                    .commit();

        }


    @Override
    public void cancel_Register() {
        setTitle("Login");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();

    }

    @Override
    public void ObjToDetails(DataServices.App obj) {
        this.object = obj;
        setTitle("App Details");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, AppDetailsFragment.newInstance(object))
                .addToBackStack(null)
                .commit();
    }
}