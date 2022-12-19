package com.hoss.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditBookActivity extends AppCompatActivity {
    private TextInputEditText bookTitle, bookPrice, bookDesc, bookImg, bookKind, bookAuthor;
    private ProgressBar loading;
    private Button btnUpd,btnDelete;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    BookModal book;
    String bookId;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        firebaseDatabase = FirebaseDatabase.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth=FirebaseAuth.getInstance();
        bookKind=findViewById(R.id.idEdtBookKind);
        bookTitle = findViewById(R.id.idEdtBookTitle);
        bookPrice = findViewById(R.id.idEdtBookPrice);
        bookDesc = findViewById(R.id.idEdtBookDesc);
        bookImg = findViewById(R.id.idEdtBookImage);
        bookAuthor = findViewById(R.id.idEdtBookAuthor);
        btnUpd = findViewById(R.id.idBtnUpdate);
        btnDelete=findViewById(R.id.idBtnDelete);
        loading = findViewById(R.id.idpBLoiding);

        book = getIntent().getParcelableExtra("book");
        System.out.print(book);
        if(book!=null){

            bookTitle.setText(book.getTitle());
            bookPrice.setText(book.getPrice());
            bookDesc.setText(book.getDesc());
            bookAuthor.setText(book.getAuthor());
            bookImg.setText(book.getImg());
            bookKind.setText(book.getKind());
            bookId=book.getBookID();
        }
        databaseReference=firebaseDatabase.getReference("Books").child(bookId);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue();
                Toast.makeText(EditBookActivity.this,"Book removed !",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(EditBookActivity.this,MainActivity.class);
                startActivity(i);
            }
        });



        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = bookTitle.getText().toString();
                String desc = bookDesc.getText().toString();
                String img = bookImg.getText().toString();
                String author = bookAuthor.getText().toString();
                String price = bookPrice.getText().toString();
                String kind = bookKind.getText().toString();
                Map<String,Object>map=new HashMap<>();
                map.put("title",title);
                map.put("author",author);
                map.put("desc",desc);
                map.put("img",img);
                map.put("kind",kind);
                map.put("price",price );
                map.put("bookID",bookId);

                BookModal book = new BookModal(title, author, desc, img, price, kind, bookId);
                databaseReference.addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                             loading.setVisibility(View.GONE);
                             databaseReference.updateChildren(map);

                                Toast.makeText(EditBookActivity.this, "Book updated successfully", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(EditBookActivity.this,MainActivity.class);
                                startActivity(i);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(EditBookActivity.this, "Failed to update book", Toast.LENGTH_SHORT).show();
                            }
                        }
                );


            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                Toast.makeText(getApplicationContext(), "User logged out", Toast.LENGTH_SHORT).show();
                auth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                this.finish();

            case R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                this.finish();

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}