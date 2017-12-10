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
import com.example.ai.mapsearch.Model.Response;
import com.example.ai.mapsearch.Utils.Constant;

import retrofit.Callback;
import retrofit.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etName, etPassword;
    private Button btnRegister;
    private RetrofitMaps retrofitMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        retrofitMaps = ApiClient.createClient().create(RetrofitMaps.class);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser(){
        retrofit.Call<Response> call = retrofitMaps.setUser(etUsername.getText().toString(), etName.getText().toString(), etPassword.getText().toString());
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                Log.d(Constant.TAG, "Register " + response.raw());

                if(response.body().getCode().equals("200")){
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(Constant.TAG, "Register error " +t.toString());
            }
        });
    }
}
