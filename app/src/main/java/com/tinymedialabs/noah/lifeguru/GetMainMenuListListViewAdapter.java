package com.tinymedialabs.noah.lifeguru;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetMainMenuListListViewAdapter extends BaseAdapter {

    private JSONArray dataArray;
    private Activity activity;

    private static final String baseUrlForImage = "http://78.62.18.43:80/Images/";

    private static LayoutInflater inflater = null;

    public GetMainMenuListListViewAdapter(JSONArray jsonArray, Activity a)
    {
        this.dataArray = jsonArray;
        this.activity = a;

        inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return this.dataArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Set up convert view if it is null
        final ListCell cell;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.main_menu_listview_cell, null);
            cell = new ListCell();
            cell.subject = (TextView) convertView.findViewById(R.id.subject_name);

            cell.image = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(cell);
        }
        else{
            cell = (ListCell) convertView.getTag();
        }

        // Change the data of the cell

        try {
            JSONObject jsonObject = this.dataArray.getJSONObject(position);

            String nameOfImage = jsonObject.getString("Image");

            cell.subject.setText(jsonObject.getString("Subject"));

            String urlForImageInServer = baseUrlForImage + nameOfImage;

            new AsyncTask<String, Void, Bitmap>()
            {

                @Override
                protected Bitmap doInBackground(String... params) {
                    String url = params[0];
                    Bitmap icon = null;

                    try {
                        InputStream in = new java.net.URL(url).openStream();
                        icon = BitmapFactory.decodeStream(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return icon;
                }

                protected void onPostExecute(Bitmap result)
                {
                    cell.image.setImageBitmap(result);
                }
            }.execute(urlForImageInServer);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;

    }

    private class ListCell
    {
        private TextView subject;
        private ImageView image;
    }

}
