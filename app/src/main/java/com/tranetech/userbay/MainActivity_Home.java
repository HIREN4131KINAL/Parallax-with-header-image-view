package com.tranetech.userbay;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity_Home extends AppCompatActivity {
    private RecyclerView horizontal_recycler_view;
    private String jsonstr;
    private String DEPARTMENT;


    ArrayList<POJO_COMMENTS> homepage = new ArrayList<POJO_COMMENTS>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity__home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);

        //Start download
        new AsyncHttpTask().execute();

    }

    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                JSONParser call = new JSONParser();
                DEPARTMENT = "http://api.androidhive.info/json/movies.json";
                jsonstr = call.makeServiceCall(DEPARTMENT);


            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("Json url view", jsonstr);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Download complete. Lets update UI
            //Hide progressbar
            //mProgressBar.setVisibility(View.GONE);

            if (jsonstr != null) {
                try {
                    JSONArray response = new JSONArray(jsonstr);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject post = response.getJSONObject(i);

                        String item_name = post.getString("title").toString();
                        String imagename = post.getString("image").toString();


                        homepage.add(new POJO_COMMENTS(item_name, imagename));


                    }
                    // use this setting to improve performance if you know that changes
                    // in content do not change the layout size of the RecyclerView
                    horizontal_recycler_view.setHasFixedSize(true);
                    // use a linear layout manager
                    LinearLayoutManager horizontalLayoutManagaer
                            = new LinearLayoutManager(MainActivity_Home.this, LinearLayoutManager.HORIZONTAL, false);
                    horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
                    //set custom adapter with array list of pojo class
                    horizontal_recycler_view.setAdapter(new HorizontalAdapter(homepage));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                Toast.makeText(MainActivity_Home.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();

        }
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        ArrayList<POJO_COMMENTS> homepage = new ArrayList<POJO_COMMENTS>();

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tv_cmnt;
            public CircleImageView cr_iv_profile;

            public MyViewHolder(View view) {
                super(view);
                tv_cmnt = (TextView) view.findViewById(R.id.tv_cmnt);
                cr_iv_profile = (CircleImageView) view.findViewById(R.id.cr_iv_profile);

            }
        }


        public HorizontalAdapter(ArrayList<POJO_COMMENTS> homepage) {

            this.homepage = homepage;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cmnts_horngtl, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final POJO_COMMENTS user = homepage.get(position);

            holder.tv_cmnt.setText(user.getComments());
            Picasso.with(getApplicationContext())
                    .load(user.getCr_iv_profile())
                    .placeholder(R.drawable.placeholder)   // optional
                    .resize(400, 400)                        // optional
                    .into(holder.cr_iv_profile);

        }

        @Override
        public int getItemCount() {
            return homepage.size();
        }
    }
}
