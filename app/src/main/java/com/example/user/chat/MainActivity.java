package com.example.user.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.chat.Forgot.ForgotBean;
import com.example.user.chat.SignUp.SignBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText old , newpass , confirm;

    Button resetpassword;

    ProgressBar bar;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        old = findViewById(R.id.oldpassword);
        back = findViewById(R.id.back);
        newpass = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        resetpassword = findViewById(R.id.resetpassword);
        bar = findViewById(R.id.progress);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String o = old.getText().toString();
                String newpa = newpass.getText().toString();
                String c = confirm.getText().toString();

                if (o.length()>0) {


                    if (newpa.length()>0){

                        if (Objects.equals(c, newpa)){


                            bar.setVisibility(View.VISIBLE);

                            Bean b = (Bean)getApplicationContext();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseurl)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            AllApi cr = retrofit.create(AllApi.class);

                            Call<ForgotBean> call = cr.forgot(b.user_id , o , newpa , c);

                            call.enqueue(new Callback<ForgotBean>() {
                                @Override
                                public void onResponse(Call<ForgotBean> call, Response<ForgotBean> response) {

                                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.GONE);
                                    finish();



                                }

                                @Override
                                public void onFailure(Call<ForgotBean> call, Throwable t) {

                                    bar.setVisibility(View.GONE);


                                }
                            });



                        }else
                        {
                            Toast.makeText(MainActivity.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                        }


                    }else {

                        Toast.makeText(MainActivity.this, "Invalid NewPassword", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Invalid Old Password", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
