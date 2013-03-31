package edu.gatech.cic.weathercube;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeatherBugAPI api = new WeatherBugAPI();
        api.getForecastByZipCode("30318");
        Forecast forecast = api.forecast;
        
        //use forecast to display values on screen
        
        Button alerts = (Button) findViewById(R.id.alerts);
        alerts.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), AlertsActivity.class);
				startActivity(intent);
			}
        
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
