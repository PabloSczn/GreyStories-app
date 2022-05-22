package com.napier.greystoriesgame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Class created for the About screen in the application
 * @author Pablo Sanchez
 * Last date of modification: 26/03/2022
 */
public class AboutActivity extends AppCompatActivity
{
    private TextView aboutText;
    private ImageView linkedin, github;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_screen);

        //Make Gmail clickable
        aboutText = findViewById(R.id.about_text);
        aboutText.setMovementMethod(LinkMovementMethod.getInstance());

        //Handle Linkedin button
        linkedin = findViewById(R.id.linkedin);
        linkedin.setOnClickListener(view -> {
            String url = "https://www.linkedin.com/in/pablosanchezn/";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        //Handle GithHub button
        github = findViewById(R.id.github);
        github.setOnClickListener(view -> {
            String url = "https://github.com/PabloSczn";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

    }
}
