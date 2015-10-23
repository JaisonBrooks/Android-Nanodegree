package jaisonbrooks.nanoapps;

// Author: Jaison Brooks

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupButtons();
    }

    private void setupButtons() {
        Button spotify = (Button) findViewById(R.id.btn_spotify);
        spotify.setOnClickListener(this);

        Button scores = (Button) findViewById(R.id.btn_scores);
        scores.setOnClickListener(this);

        Button library = (Button) findViewById(R.id.btn_library);
        library.setOnClickListener(this);

        Button build_it_bigger = (Button) findViewById(R.id.btn_builditbigger);
        build_it_bigger.setOnClickListener(this);

        Button xyz = (Button) findViewById(R.id.btn_xyzreader);
        xyz.setOnClickListener(this);

        Button capstone = (Button) findViewById(R.id.btn_capstone);
        capstone.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "This button will launch my " + ((Button) v).getText() + " app!", Toast.LENGTH_SHORT).show();
    }
}
