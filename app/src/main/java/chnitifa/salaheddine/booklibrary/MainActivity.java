package chnitifa.salaheddine.booklibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import chnitifa.salaheddine.booklibrary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseHelper DB;
    ArrayList<String> book_id, book_title, book_author;
    AdapterRecyclerView Adapter;

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

        DB = new DatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();

        storeDataInArrays();

        Adapter=new AdapterRecyclerView(MainActivity.this,this, book_id, book_title, book_author);
        binding.recyclerView.setAdapter(Adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialogDeleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDialogDeleteAll() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View layoutView=getLayoutInflater().inflate(R.layout.delete_all,null);
        builder.setView(layoutView);
        final AlertDialog dialog=builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes=layoutView.findViewById(R.id.yes);
        Button no=layoutView.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper DB = new DatabaseHelper(MainActivity.this);
                DB.deleteAllData();
                dialog.dismiss();
                recreate();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    void storeDataInArrays(){
        Cursor cursor = DB.readAllData();
        if(cursor.getCount() == 0){
            binding.imageView.setVisibility(View.VISIBLE);
            binding.textView.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
            }
            binding.imageView.setVisibility(View.GONE);
            binding.textView.setVisibility(View.GONE);
        }
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
                dialog.dismiss();
                recreate();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                recreate();
            }
        });

        dialog.show();
    }
}
