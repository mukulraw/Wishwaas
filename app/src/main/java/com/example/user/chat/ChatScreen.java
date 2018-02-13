package com.example.user.chat;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.chat.Forgot.ForgotBean;
import com.example.user.chat.SendMsg.Sendbean;
import com.example.user.chat.SignUp.SignBean;
import com.example.user.chat.UserMsg.Datum;
import com.example.user.chat.UserMsg.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ChatScreen extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView grid;
    LinearLayoutManager manager;

    ProgressBar bar;

    GridAdapter adapter;

    EditText type;
    Button send;
    int count = 0;

    List<Datum> list;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bar = (ProgressBar) findViewById(R.id.progress);
        type = (EditText) findViewById(R.id.type);
        send = (Button) findViewById(R.id.send);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setTitleTextColor(Color.BLACK);

        toolbar.setNavigationIcon(R.drawable.arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Bean b = (Bean) getApplicationContext();
        toolbar.setTitle(b.usernmae);

        grid = findViewById(R.id.grid);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL , false);

        list = new ArrayList<>();

        adapter = new GridAdapter(this, list);
        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);

        LoadComments();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String t = type.getText().toString();

                if (t.length() > 0)
                {
                    bar.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApi cr = retrofit.create(AllApi.class);

                    Call<Sendbean> call = cr.send(b.user_id, t);

                    call.enqueue(new Callback<Sendbean>() {
                        @Override
                        public void onResponse(Call<Sendbean> call, Response<Sendbean> response) {

                            type.setText("");

                            Toast.makeText(ChatScreen.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            bar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<Sendbean> call, Throwable t) {

                            bar.setVisibility(View.GONE);


                        }
                    });
                }




            }
        });







    }


    public void scheduleComments()
    {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApi cr = retrofit.create(AllApi.class);

                Call<UserBean> call = cr.list(b.user_id);

                call.enqueue(new Callback<UserBean>() {
                    @Override
                    public void onResponse(Call<UserBean> call, Response<UserBean> response) {

                        try {
                            adapter.setgrid(response.body().getData());
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        if (response.body().getData().size() > count) {
                            grid.smoothScrollToPosition(adapter.getItemCount() - 1);
                            count = response.body().getData().size();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserBean> call, Throwable t) {

                        Log.d("mukul" , t.toString());




                    }
                });


            }
        }, 0 , 1000);










    }


    private void LoadComments() {





                        bar.setVisibility(View.VISIBLE);


                Bean b = (Bean) getApplicationContext();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(b.baseurl)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                AllApi cr = retrofit.create(AllApi.class);

                Call<UserBean> call = cr.list(b.user_id);

                call.enqueue(new Callback<UserBean>() {
                    @Override
                    public void onResponse(Call<UserBean> call, Response<UserBean> response) {

                        adapter.setgrid(response.body().getData());

                                bar.setVisibility(View.GONE);

                                scheduleComments();

                    }

                    @Override
                    public void onFailure(Call<UserBean> call, Throwable t) {

                        Log.d("mukul" , t.toString());


                                bar.setVisibility(View.GONE);



                    }
                });




    }


    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {

        Context context;

        List<Datum> list = new ArrayList<>();

        public GridAdapter(Context context, List<Datum> list) {

            this.context = context;
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.grid_list_model, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            Datum item = list.get(position);


            if (Objects.equals(item.getSendBy(), "user"))
            {
                holder.container.setGravity(GravityCompat.END);
                holder.bubble.setBackground(context.getResources().getDrawable(R.drawable.bubble_me));
            }
            else
            {
                holder.container.setGravity(GravityCompat.START);
                holder.bubble.setBackground(context.getResources().getDrawable(R.drawable.bubble_admin));
            }


            holder.name.setText(item.getUsername());
            holder.msg.setText(item.getMessage());
            holder.date.setText(item.getCreaatedDate());



        }

        public void setgrid(List<Datum> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name, date, msg;
            LinearLayout container , bubble;

            public MyViewHolder(View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.name);
                date = itemView.findViewById(R.id.date);
                msg = itemView.findViewById(R.id.message);

                container = (LinearLayout)itemView.findViewById(R.id.container);
                bubble = (LinearLayout)itemView.findViewById(R.id.bubble);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
