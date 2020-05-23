package com.example.wheatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wheatherapplication.data.HistoryEntity;
import com.example.wheatherapplication.data.HistoryRepository;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    LinearLayout historyLayout;
    HistoryRepository historyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyLayout = findViewById(R.id.historyLayout);
        historyRepository = new HistoryRepository(this);

        refresh();
    }

    private void refresh() {
        List<HistoryEntity> data = historyRepository.getAll();

        historyLayout.removeAllViews();

        for(HistoryEntity entity : data){
            final LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            final TextView tv = new TextView(this);
            tv.setText(entity.getCity() + " " + entity.getUpdateDate());
            tv.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Medium);

            Button btnDelete = new Button(this);

            btnDelete.setText("X");
            btnDelete.setBackgroundColor(Color.TRANSPARENT);
            btnDelete.setTag(entity.getId());
            btnDelete.setOnClickListener(onDeleteClicked);

            layout.addView(btnDelete);
            layout.addView(tv);

            historyLayout.addView(layout);
        }
    }

    View.OnClickListener onDeleteClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String idString = v.getTag().toString();
            int id = Integer.parseInt(idString);
            historyRepository.delete(id);

            refresh();
        }
    };
}
