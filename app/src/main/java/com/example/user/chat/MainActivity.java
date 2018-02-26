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

    EditText mobile;

    Button resetpassword;

    ProgressBar bar;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobile = findViewById(R.id.oldpassword);
        back = findViewById(R.id.back);

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


                String m = mobile.getText().toString();


                if (Utils.isValidMobile(m)) {


                    bar.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApi cr = retrofit.create(AllApi.class);

                    Call<ForgotBean> call = cr.forgot(m);

                    call.enqueue(new Callback<ForgotBean>() {
                        @Override
                        public void onResponse(Call<ForgotBean> call, Response<ForgotBean> response) {

                            if (Objects.equals(response.body().getStatus(), "1")) {

                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.GONE);
                                finish();
                            } else {

                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.GONE);

                            }


                        }

                        @Override
                        public void onFailure(Call<ForgotBean> call, Throwable t) {

                            bar.setVisibility(View.GONE);


                        }
                    });


                } else {
                    Toast.makeText(MainActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
