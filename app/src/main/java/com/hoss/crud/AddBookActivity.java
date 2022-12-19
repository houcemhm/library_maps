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

public class AddBookActivity extends AppCompatActivity {

    private TextInputEditText bookTitle, bookPrice, bookDesc, bookImg, bookKind, bookAuthor;
    private ProgressBar loading;
    private Button btnAdd;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth= FirebaseAuth.getInstance();

        bookTitle = findViewById(R.id.idEdtBookTitle);
        bookPrice = findViewById(R.id.idEdtBookPrice);
        bookDesc = findViewById(R.id.idEdtBookDesc);
        bookImg = findViewById(R.id.idEdtBookImage);
        bookKind = findViewById(R.id.idEdtBookKind);
        bookAuthor = findViewById(R.id.idEdtBookAuthor);
        btnAdd = findViewById(R.id.idBtnAdd);
        loading = findViewById(R.id.idpBLoiding);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = bookTitle.getText().toString();
                String desc = bookDesc.getText().toString();
                String img = bookImg.getText().toString();
                String author = bookAuthor.getText().toString();
                String price = bookPrice.getText().toString();
                String kind = bookKind.getText().toString();
                bookId = title;
                BookModal book = new BookModal(title, author, desc, img, price, kind,bookId);
                databaseReference.addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                databaseReference.child(bookId).setValue(book);
                                Toast.makeText(AddBookActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(AddBookActivity.this,MainActivity.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(AddBookActivity.this, "Error is : "+error, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddBookActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
                auth.signOut();
                startActivity(new Intent(AddBookActivity.this, LoginActivity.class));
                this.finish();

            case R.id.home:
                startActivity(new Intent(AddBookActivity.this, MainActivity.class));
                this.finish();

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}