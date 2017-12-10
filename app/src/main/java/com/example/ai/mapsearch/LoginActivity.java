package com.example.ai.mapsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ai.mapsearch.API.ApiClient;
import com.example.ai.mapsearch.API.RetrofitMaps;
import com.example.ai.mapsearch.Data.PrefManager;
import com.example.ai.mapsearch.Model.ResponseUser;
import com.example.ai.mapsearch.Utils.Constant;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister;
    private RetrofitMaps retrofitMaps;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(buttonOperation);
        btnRegister.setOnClickListener(buttonOperation);

        retrofitMaps = ApiClient.createClient().create(RetrofitMaps.class);
        prefManager = new PrefManager(getApplicationContext());

        if (prefManager.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, DestinationActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private View.OnClickListener buttonOperation = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnLogin:
                    getLogin();
                    break;
                case R.id.btnRegister:

                    break;
            }
        }
    };

    private void getLogin(){

        Call<ResponseUser> call = retrofitMaps.getLogin(etUsername.getText().toString(), etPassword.getText().toString());
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Response<ResponseUser> response, Retrofit retrofit) {
                Log.d(Constant.TAG, "Login " + response.raw());
//                List<User> userList = response.body().getData();
                if(response.body().getCode().equals("200")){
                    prefManager.setLogin(true);
                    prefManager.setUser(response.body().getData().get(0).getId(), response.body().getData().get(0).getUsername(), response.body().getData().get(0).getName());
                    Log.d(Constant.TAG, "id " + response.body().getData().get(0).getId());
                    Intent intent = new Intent(LoginActivity.this, DestinationActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(Constant.TAG, "login error " + t.toString());
            }
        });
    }
}
