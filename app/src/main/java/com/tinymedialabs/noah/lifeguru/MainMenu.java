package com.tinymedialabs.noah.lifeguru;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainMenu extends ActionBarActivity {

    private ListView GetMainMenuListView;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_menu);

        this.GetMainMenuListView = (ListView) this.findViewById(R.id.listView);

        new GetMainMenuListTask().execute(new ApiMainMenu());

        this.GetMainMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    // Get image ID
                    JSONObject imageClicked = jsonArray.getJSONObject(position);

                    // Send Image ID
                    Intent SubTopicMenu = new Intent(getApplicationContext(),SubMenu.class);
                    SubTopicMenu.putExtra("Subject", imageClicked.getString("Subject"));

                    Bundle bndlanimation =
                            ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1, R.anim.animation2).toBundle();

                    startActivity(SubTopicMenu, bndlanimation);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });


    }

    public void setListAdapter(JSONArray jsonArray)
    {
        this.jsonArray = jsonArray;
        this.GetMainMenuListView.setAdapter(new GetMainMenuListListViewAdapter(jsonArray,this));
    }


    private class GetMainMenuListTask extends AsyncTask<ApiMainMenu, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiMainMenu... params) {

            // it is executed on Background thread

            return params[0].GetMainMenuList();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);


        }
    }
}
