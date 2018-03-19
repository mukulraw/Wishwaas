package com.chat.wishwaas.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText mobile, password;

    Button login;

    TextView forgot, register;

    ProgressBar bar;

    //SharedPreferences pref;
    //SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        //edit = pref.edit();
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        forgot = findViewById(R.id.forgot);
        register = findViewById(R.id.register);
        bar = findViewById(R.id.progress);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, Home.class);
                startActivity(i);
                finish();
            }
        });

       /* login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String m = mobile.getText().toString();
                final String p = password.getText().toString();

                if (Utils.isValidMobile(m)) {

                    if (p.length() > 0) {

                        bar.setVisibility(View.VISIBLE);

                        final Bean b = (Bean)getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApi cr = retrofit.create(AllApi.class);

                        Call<MoblileBean> call = cr.mobile(m, p);
                        call.enqueue(new Callback<MoblileBean>() {
                            @Override
                            public void onResponse(Call<MoblileBean> call, Response<MoblileBean> response) {


                                if (Objects.equals(response.body().getStatus(), "1"))

                                {

                                    b.user_id = response.body().getData().getUserId();
                                    b.usernmae = response.body().getData().getName();
                                    b.dob = response.body().getData().getBirthDate();
                                    b.time = response.body().getData().getBirthTime();
                                    b.pdob = response.body().getData().getBirthPlace();

                                    edit.putString("mobile" , m);
                                    edit.putString("password" , p);
                                    edit.apply();

                                    Intent i = new Intent(Login.this, Home.class);
                                    startActivity(i);
                                    finish();

                                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }else {

                                    Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                                bar.setVisibility(View.GONE);


                            }

                            @Override
                            public void onFailure(Call<MoblileBean> call, Throwable t) {


                                Log.d("fjkhdg" , t.toString());
                                bar.setVisibility(View.GONE);


                            }
                        });


                    } else {
                        Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Login.this, "Invalid Mobile number", Toast.LENGTH_SHORT).show();
                }


            }
        });
*/

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);


            }
        });
    }
}
