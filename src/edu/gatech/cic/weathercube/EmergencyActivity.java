package edu.gatech.cic.weathercube;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EmergencyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_emergency, menu);
        return true;
    }
}
