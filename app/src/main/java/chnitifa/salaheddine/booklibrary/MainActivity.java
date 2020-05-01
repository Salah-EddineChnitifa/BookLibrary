package chnitifa.salaheddine.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import chnitifa.salaheddine.booklibrary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddBook();
            }
        });
    }

    private void DialogAddBook() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View layoutView=getLayoutInflater().inflate(R.layout.add_book,null);
        builder.setView(layoutView);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
