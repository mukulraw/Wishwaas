package com.chat.wishwaas.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    EditText name, pass, mobile, confirm;

    Button register;

    TextView login;

    ProgressBar bar;

    //SharedPreferences pref;
    //SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //pref = getSharedPreferences("pref" , Context.MODE_PRIVATE);
       // edit = pref.edit();

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


                Intent i = new Intent(Register.this, Home.class);
                startActivity(i);
                finish();

            }
        });

       /* register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String u = name.getText().toString();
                final String p = pass.getText().toString();
                final String m = mobile.getText().toString();
                String c = confirm.getText().toString();

                if (u.length() > 0) {

                    if (Utils.isValidMobile(m)){

                        if (p.length()>0){

                            if (Objects.equals(c, p))
                            {
                                bar.setVisibility(View.VISIBLE);

                                final Bean b = (Bean)getApplicationContext();

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


                                        if (Objects.equals(response.body().getStatus(), "1"))

                                        {

                                            b.user_id = response.body().getData().getUserId();
                                            b.usernmae = response.body().getData().getName();

                                            edit.putString("mobile" , m);
                                            edit.putString("password" , p);
                                            edit.apply();

                                            Intent i = new Intent(Register.this, Home.class);
                                            startActivity(i);
                                            finish();

                                            Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        }else {

                                            Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }


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
        });*/


    }
}
