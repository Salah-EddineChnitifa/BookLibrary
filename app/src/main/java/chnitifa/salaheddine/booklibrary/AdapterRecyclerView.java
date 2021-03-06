package chnitifa.salaheddine.booklibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList book_id, book_title, book_author;

    String id, title, author;

    AdapterRecyclerView(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author) {
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
    }

    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.data_library, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.ViewHolder holder, final int position) {
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id=String.valueOf(book_id.get(position));
                title=String.valueOf(book_title.get(position));
                author=String.valueOf(book_author.get(position));

                DialogUpdateBook(id,title,author);
            }
        });
    }

    private void DialogUpdateBook(final String id, String title, String author) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        LayoutInflater inflater=LayoutInflater.from(context);
        View layoutView=inflater.inflate(R.layout.update_book,null, false);
        builder.setView(layoutView);
        final AlertDialog dialog=builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button update=layoutView.findViewById(R.id.buttonUpdateBook);
        Button delete=layoutView.findViewById(R.id.buttonDeleteBook);
        Button cancel=layoutView.findViewById(R.id.button2Cancel);
        final EditText title_input = layoutView.findViewById(R.id.dataTitreBook);
        final EditText author_input = layoutView.findViewById(R.id.dataAuthorBook);

        title_input.setText(title);
        author_input.setText(author);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper DB = new DatabaseHelper(context);
                String t = title_input.getText().toString().trim();
                String a = author_input.getText().toString().trim();
                DB.updateData(id, t, a);
                dialog.dismiss();
                ((Activity)context).recreate();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper DB = new DatabaseHelper(context);
                DB.deleteOneRow(id);
                dialog.dismiss();
                ((Activity)context).recreate();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((Activity)context).recreate();
            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView book_id_txt, book_title_txt, book_author_txt;
        LinearLayout mainLayout;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.textViewID);
            book_title_txt = itemView.findViewById(R.id.textViewTitre);
            book_author_txt = itemView.findViewById(R.id.textViewAuthor);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
