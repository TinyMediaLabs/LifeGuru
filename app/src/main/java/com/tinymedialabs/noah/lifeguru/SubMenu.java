package com.tinymedialabs.noah.lifeguru;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import android.support.v7.app.ActionBarActivity;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SubMenu extends ActionBarActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.animation4, R.anim.animation3);
    }

    private TextView SubTopic;

    private String PassedSubject;
    private JSONArray jsonArray;

    private ListView GetSubMenuListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sub_menu);

        this.SubTopic = (TextView) this.findViewById(R.id.SubTopic);

        this.GetSubMenuListView = (ListView) this.findViewById(R.id.listView2);

        //Get set SubTopic
        PassedSubject = getIntent().getStringExtra("Subject");
        SubTopic.setText(PassedSubject);
        PassedSubject = PassedSubject.toLowerCase();

        new GetSubMenuListTask().execute(new ApiSubMenu());

        this.GetSubMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    // Get image ID
                    JSONObject SubTopicClicked = jsonArray.getJSONObject(position);

                    // Send Image ID
                    Intent ReadingMenu = new Intent(getApplicationContext(),ReadingMenu.class);
                    ReadingMenu.putExtra("SubTopic", SubTopicClicked.getString("SubTopic"));
                    ReadingMenu.putExtra("Text", SubTopicClicked.getString("Text"));
                    ReadingMenu.putExtra("Image", SubTopicClicked.getString("Image"));

                    Bundle bndlanimation =
                            ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1, R.anim.animation2).toBundle();

                    startActivity(ReadingMenu, bndlanimation);
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setListAdapter2(JSONArray jsonArray)
    {
        this.jsonArray = jsonArray;
        this.GetSubMenuListView.setAdapter(new GetSubMenuListListViewAdapter(jsonArray,this));
    }

    private class GetSubMenuListTask extends AsyncTask<ApiSubMenu, Long, JSONArray> {
        @Override
        protected JSONArray doInBackground(ApiSubMenu... params) {

            // it is executed on Background thread

            return params[0].GetSubMenuList(PassedSubject);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter2(jsonArray);

        }
    }

}
