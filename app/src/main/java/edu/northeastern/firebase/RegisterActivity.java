package edu.northeastern.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.dao.UserDao;
import edu.northeastern.firebase.entity.User;

/**
 * The register activity class that helps register a user.
 *
 * @author ShiChang Ye
 */
public class RegisterActivity extends AppCompatActivity {
    private static String CLIENT_REGISTRATION_TOKEN;

    Button btnRegister;
    TextView inputUserName;
    UserDao userDao;

    /**
     * The onCreate method called when the activity starts.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     *                           <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Binds widgets from the layout to the fields.
        btnRegister = findViewById(R.id.btnRegister);
        inputUserName = findViewById(R.id.inputUserName);

        // Create a user dao to access the database.
        userDao = new UserDao();

        btnRegister.setOnClickListener(view -> createUser());
    }

    /**
     * Creates the user in the database.
     */
    private void createUser() {
        String userName = inputUserName.getText().toString();
        // Checks the validity of the user name input.
        if (userName.strip().length() == 0) {
            Toast.makeText(this, "Invalid! Empty user name.", Toast.LENGTH_LONG).show();
            return;
        }

        // Creates the user based on the user name input and the
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, "Registration failed: failed to get token.", Toast.LENGTH_LONG).show();
                Log.w("RegisterActivity", "Registration failed: failed to get token.");
                return;
            }
            // The CLIENT_REGISTRATION_TOKEN can be obtained once the task succeeds.
            CLIENT_REGISTRATION_TOKEN = task.getResult();
            User user = new User(CLIENT_REGISTRATION_TOKEN, userName);

            // Call the create method from userdao to create the new user. --> CC: should be add not create
            userDao.add(user).addOnSuccessListener(result -> {
                Toast.makeText(this, "Registered successfully.", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(result -> {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_LONG).show();
            });
        });

        // Jumps to SendStickersActivity.
        Intent intent = new Intent(this, SentStickersActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}
