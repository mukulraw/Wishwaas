package com.chat.wishwaas.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    Timer timer;

    ProgressBar bar;

    SharedPreferences pref;

    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref  = getSharedPreferences("pref" , Context.MODE_PRIVATE);
        edit = pref.edit();

        bar = findViewById(R.id.progress);

        timer = new Timer();

       /* final String m = pref.getString("mobile" , "");
        final String p = pref.getString("password" , "");


        if (m.length()>0 && p.length()>0){

            bar.setVisibility(View.VISIBLE);

            final Bean b = (Bean)getApplicationContext();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseurl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AllApi cr = retrofit.create(AllApi.class);


            Log.d("m" , m);
            Log.d("p" , p);
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

                        Intent i = new Intent(Splash.this, Home.class);
                        startActivity(i);
                        finish();


                        Log.d("jksgjkfd" , "fjdklg");

                        Toast.makeText(Splash.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(Splash.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    bar.setVisibility(View.GONE);


                }

                @Override
                public void onFailure(Call<MoblileBean> call, Throwable t) {

                    Log.d("fklsdjfs" , t.toString());

                    bar.setVisibility(View.GONE);


                }
            });





        }
        else {
*/            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    Intent i = new Intent(Splash.this , Login .class);
                    startActivity(i);
                    finish();


                }
            },1500);



        }




    }

