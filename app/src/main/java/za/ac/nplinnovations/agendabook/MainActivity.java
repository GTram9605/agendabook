package za.ac.nplinnovations.agendabook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import za.ac.nplinnovations.agendabook.features.FeaturesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStart(View view) {
        startActivity(new Intent(view.getContext(), FeaturesActivity.class));
    }
}