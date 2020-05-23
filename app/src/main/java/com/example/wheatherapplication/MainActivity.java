package com.example.wheatherapplication;
import com.example.wheatherapplication.data.HistoryEntity;
import com.example.wheatherapplication.data.HistoryRepository;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wheatherapplication.services.WeatherService;
import com.example.wheatherapplication.models.OpenWeatherMap;

public class MainActivity extends AppCompatActivity{

    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtCelsius;
    EditText editCity;
    Button btnHistory;
    HistoryRepository db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCity = findViewById(R.id.txtCity);
        txtLastUpdate = findViewById(R.id.txtLastUpdate);
        txtCelsius = findViewById(R.id.txtCelsius);
        txtDescription = findViewById(R.id.txtDescription);
        txtHumidity = findViewById(R.id.txtHumidity);
        editCity = findViewById(R.id.customCityEdit);
        btnHistory = findViewById(R.id.btnHistory);

        btnHistory.setOnClickListener(onHistoryClick);
        editCity.setOnEditorActionListener(onCityChanged);

        db = new HistoryRepository(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.loadInitial();
    }

    private void loadInitial() {
        HistoryEntity last = db.getLast();
        if (last != null) {
            GetWeatherTask getTask = new GetWeatherTask();
            getTask.setSave(false);
            getTask.execute(last.getCity());
        }
    }

    private View.OnClickListener onHistoryClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, HistoryActivity.class);
            MainActivity.this.startActivity(myIntent);
        }
    };

    private EditText.OnEditorActionListener onCityChanged = new EditText.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != EditorInfo.IME_ACTION_DONE) {
                return false;
            }

            String city = editCity.getText().toString();
            if(city.isEmpty() || city == null){
                return false;
            }

            new GetWeatherTask().execute(city);

            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            return true;
        }
    };

    private void displayError(String error){
        txtCity.setText(error);
        txtLastUpdate.setText("");
        txtDescription.setText("");
        txtHumidity.setText("");
        txtCelsius.setText("");
    }

    private class GetWeatherTask extends AsyncTask<String,Void,OpenWeatherMap>{
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        private boolean save = true;

        public void setSave(boolean save) {
            this.save = save;
        }

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            pd.setTitle("Executing...");
            pd.show();
        }

        @Override
        protected OpenWeatherMap doInBackground(String... params) {
            String city = params[0];

            WeatherService http = new WeatherService();
            return http.getData(city);
        }

        @Override
        protected void onPostExecute(OpenWeatherMap openWeatherMap) {
            super.onPostExecute(openWeatherMap);

            if(openWeatherMap == null){
                pd.dismiss();
                displayError("Could Not Find City");
                return;
            }

            if(save){
                save(openWeatherMap);
            }

            show(openWeatherMap);
            pd.dismiss();
        }

        private void save(OpenWeatherMap map){
            HistoryEntity entity = new HistoryEntity();
            entity.setUpdateDate(map.getLastUpdate());
            entity.setCity(map.getName());

            db.insert(entity);
        }

        private void show(OpenWeatherMap map){
            txtCity.setText(String.format("%s %s ", map.getName(), map.getSys().getCountry()));
            txtLastUpdate.setText(String.format("%s", map.getLastUpdate()));
            txtDescription.setText(String.format("%s", map.getWeather().get(0).getDescription()));
            txtHumidity.setText(String.format("%s", map.getMain().getHumidity()));
            txtCelsius.setText(String.format("%s", (int)map.getMain().getTemp()) + "°");
            editCity.setText("");
        }
    }
}