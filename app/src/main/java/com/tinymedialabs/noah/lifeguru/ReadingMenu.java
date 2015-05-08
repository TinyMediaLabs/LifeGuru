package com.tinymedialabs.noah.lifeguru;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import android.support.v7.app.ActionBarActivity;

public class ReadingMenu extends ActionBarActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.animation4, R.anim.animation3);
    }

    private TextView ReadingTopic;
    private ImageView ImageForReading;
    private TextView ReadableText;

    private String imageName;

    private static final String baseUrlForImage3 = "http://192.168.1.100:8080/Images/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reading_menu);

        ReadingTopic = (TextView) this.findViewById(R.id.ReadingTopic);
        ReadableText = (TextView) this.findViewById(R.id.ReadableText);
        ImageForReading = (ImageView) this.findViewById(R.id.imageView3);

        ReadingTopic.setText(getIntent().getStringExtra("SubTopic"));
        ReadableText.setText(getIntent().getStringExtra("Text"));

        imageName = getIntent().getStringExtra("Image");

        String urlForImageInServer = baseUrlForImage3 + imageName;

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
                ImageForReading.setImageBitmap(result);
            }
        }.execute(urlForImageInServer);
    }

}
