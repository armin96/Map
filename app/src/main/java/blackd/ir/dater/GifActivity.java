package blackd.ir.dater;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class GifActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        GifImageView gifImageView = findViewById(R.id.gfView);
        try {
            GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.b );
            gifImageView.setImageResource(R.drawable.b);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
