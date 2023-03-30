package com.example.safedrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GetData extends AppCompatActivity {

    private TextView tvAngry, tvDrowsy, tvHappy, tvNeutral, tvSurprise;
    private TextView tvAngryData, tvDrowsyData, tvHappyData, tvNeutralData, tvSurpriseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_data);

        tvAngry = findViewById(R.id.tv_angry);
        tvDrowsy = findViewById(R.id.tv_drowsy);
        tvHappy = findViewById(R.id.tv_happy);
        tvNeutral = findViewById(R.id.tv_neutral);
        tvSurprise = findViewById(R.id.tv_surprise);

        tvAngryData = findViewById(R.id.tv_angry_data);
        tvDrowsyData = findViewById(R.id.tv_drowsy_data);
        tvHappyData = findViewById(R.id.tv_happy_data);
        tvNeutralData = findViewById(R.id.tv_neutral_data);
        tvSurpriseData = findViewById(R.id.tv_surprise_data);

        FirebaseFirestore.getInstance().collection("drivers").document("Hemshikha").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            double angry = documentSnapshot.getDouble("angry");
                            double drowsy = documentSnapshot.getDouble("drowsy");
                            double happy = documentSnapshot.getDouble("happy");
                            double neutral = documentSnapshot.getDouble("neutral");
                            double surprise = documentSnapshot.getDouble("surprise");

                            double sum = angry + drowsy + happy + neutral + surprise;

                            tvAngryData.setText(String.format("%d (%.2f%%)", (int)angry, angry/sum * 100));
                            tvDrowsyData.setText(String.format("%d (%.2f%%)", (int)drowsy, drowsy/sum * 100));
                            tvHappyData.setText(String.format("%d (%.2f%%)", (int)happy, happy/sum * 100));
                            tvNeutralData.setText(String.format("%d (%.2f%%)", (int)neutral, neutral/sum * 100));
                            tvSurpriseData.setText(String.format("%d (%.2f%%)", (int)surprise, surprise/sum * 100));

                        } else {
                            Toast.makeText(GetData.this, "Document not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GetData.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
