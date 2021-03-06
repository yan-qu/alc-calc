package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.androidapp.buttonpage.userID;

public class Calculator extends AppCompatActivity {
    private static long weights;
    private static double gends;
    private static double nums2;
    private static double time2;
    private static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        userID = getIntent().getStringExtra("currentUserRefID");
        configuretheButton();
        configureBackButton();
    }

    private void configuretheButton() {
        Button calcButton = (Button) findViewById(R.id.calcu);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nums = findViewById(R.id.num);
                String nums1 = nums.getText().toString();
                nums2 = 0.0;
                if (!(nums1.equals("")))
                    nums2 = Integer.parseInt(nums1);
                EditText time = findViewById(R.id.timef);
                String time1 = time.getText().toString();
                time2 = -1.0;
                if (!(time1.equals("")))
                    time2 = Integer.parseInt(time1);
                FirebaseFirestore fr = FirebaseFirestore.getInstance();
                DocumentReference userRef = fr.collection("users").document(userID);
                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        weights = (long) document.get("weight");
                        gends = (double) document.get("gender");
                        Log.d("test", Double.toString(gends));
                        double theBAC = ((nums2 * 0.6) * 5.14 / (weights * gends)) - (0.015 * time2);
                        TextView bacLevel = findViewById(R.id.baclevel);
                        if(time2 == -1 || nums2 ==0)
                            bacLevel.setText("Please complete all fields");
                        else
                            bacLevel.setText("Your BAC is: "+theBAC);
                    }
                });


            }
        });
    }

    private void configureBackButton() {
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Calculator.this, buttonpage.class);
                startActivity(i);
            }

        });
    }
}


