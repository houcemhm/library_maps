package com.hoss.crud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookRVAdapter.BookClickInterface {

    private RecyclerView bookRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB,locFAB;
    private RelativeLayout bottomSheetRL;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<BookModal> bookAl;
    private BookRVAdapter bookRVAdapter;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();

        bookRV = findViewById(R.id.idRVBooks);
        loadingPB = findViewById(R.id.idpBLoiding);
        addFAB = findViewById(R.id.idFABAdd);
        locFAB=findViewById(R.id.idFAPosition);
        bottomSheetRL = findViewById(R.id.idBSDialog);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");

        bookAl = new ArrayList<>();
        bookRVAdapter = new BookRVAdapter(bookAl, this, this);
        bookRV.setLayoutManager(new GridLayoutManager(this, 3));
        bookRV.setAdapter(bookRVAdapter);
//        System.out.println(databaseReference);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(i);
            }
        });
        try {
        } catch (Exception e) {
            loadingPB.setVisibility(View.GONE);
        }
        locFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllBooks();
    }

    private void getAllBooks() {

        bookAl.clear();
        System.out.println(bookAl.size());
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                System.out.print(snapshot.getValue());
                bookAl.add(snapshot.getValue(BookModal.class));

                bookRVAdapter.notifyDataSetChanged();
                System.out.println(bookAl.size());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                bookRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                bookRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                bookRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error is : " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickBook(int position) {
        displaBottomSheet(bookAl.get(position));
    }

    private void displaBottomSheet(BookModal book) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView titleTV = layout.findViewById(R.id.idTVTitle);
        TextView descTV = layout.findViewById(R.id.idTVDesc);
        descTV.setMovementMethod(new ScrollingMovementMethod());
        TextView priceTV = layout.findViewById(R.id.idTVPrice);
        TextView kindTV = layout.findViewById(R.id.idTVKind);
        ImageView imageV = layout.findViewById(R.id.idImgv);
        Button editBtn = layout.findViewById(R.id.idBtnEdit);


        titleTV.setText(book.getTitle()+" by "+book.getAuthor());
        descTV.setText(book.getDesc());
        priceTV.setText(book.getPrice());
        kindTV.setText(book.getKind());
        Picasso.get().load(book.getImg()).into(imageV);


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditBookActivity.class);
                i.putExtra("book", book);
                startActivity(i);
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
                Toast.makeText(MainActivity.this, "User logged out", Toast.LENGTH_SHORT).show();
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                this.finish();

            case R.id.home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                this.finish();

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}