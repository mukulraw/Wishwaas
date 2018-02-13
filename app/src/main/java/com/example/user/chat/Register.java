package com.example.user.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.chat.SignUp.SignBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Register extends AppCompatActivity {

    EditText name, pass, mobile, confirm;

    Button register;

    TextView login;

    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);
        confirm = findViewById(R.id.confirm);
        register = findViewById(R.id.register);
        bar = findViewById(R.id.progress);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String u = name.getText().toString();
                String p = pass.getText().toString();
                String m = mobile.getText().toString();
                String c = confirm.getText().toString();

                if (u.length() > 0) {

                    if (Utils.isValidMobile(m)){

                        if (p.length()>0){

                            if (Objects.equals(c, p))
                            {
                                bar.setVisibility(View.VISIBLE);

                                Bean b = (Bean)getApplicationContext();

                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(b.baseurl)
                                        .addConverterFactory(ScalarsConverterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                AllApi cr = retrofit.create(AllApi.class);

                                Call<SignBean> call = cr.call(u, m, p);
                                call.enqueue(new Callback<SignBean>() {
                                    @Override
                                    public void onResponse(Call<SignBean> call, Response<SignBean> response) {


                                        Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(Register.this, Login.class);
                                        startActivity(i);

                                        bar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<SignBean> call, Throwable t) {

                                        bar.setVisibility(View.GONE);


                                    }
                                });



                            }else {
                                Toast.makeText(Register.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                            }


                        }else {
                            Toast.makeText(Register.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }

                    }else
                    {
                        Toast.makeText(Register.this, "Invalid Mobile Number ", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(Register.this, "Invalid UserName", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }
}
