package edu.northeastern.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.northeastern.atyourservice.R;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    TextView inputUserName;
    DAOUser userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDao = new DAOUser();
        btnRegister = findViewById(R.id.btn_register);
        inputUserName = findViewById(R.id.id_username);

        btnRegister.setOnClickListener(view -> {
            User user = new User("1", inputUserName.getText().toString());
            userDao.add(user).addOnSuccessListener(success -> {
                Toast.makeText(RegisterActivity.this, "Registered successfully.", Toast.LENGTH_LONG).show();
            }).addOnFailureListener(error -> {
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            });
        });

        final String[] tokenResult = {""};
        Task<String> token = FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    System.out.println("Not successful!");
                } else {
                    tokenResult[0] = task.getResult();
                    System.out.println("token got ===" + tokenResult[0]);
                }
            }
        });
    }
}