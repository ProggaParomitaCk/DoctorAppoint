package com.example.doctorappoinment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.doctorappoinment.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DemoActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        binding.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),BookAppActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("processing");
        if(auth.getCurrentUser()==null){
            progressDialog.show();
            auth.signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialog.cancel();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(DemoActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
        }else {
            progressDialog.cancel();
        }
    }
    private void loaddata(){
        fAuth.collection("Booking").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            }
        });
    }
}