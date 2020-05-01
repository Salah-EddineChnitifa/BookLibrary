package chnitifa.salaheddine.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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
        final AlertDialog dialog=builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button add=layoutView.findViewById(R.id.buttonAddBook);
        Button cancel=layoutView.findViewById(R.id.button1Cancel);
        final EditText title_input = layoutView.findViewById(R.id.editTitreBook);
        final EditText author_input = layoutView.findViewById(R.id.editAuthorBook);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper DB = new DatabaseHelper(MainActivity.this);
                DB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim()
                        );
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
