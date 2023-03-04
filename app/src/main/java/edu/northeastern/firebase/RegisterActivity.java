package edu.northeastern.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.List;

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
            Sticker sticker1 = new Sticker("Alan", "John", "2023-03-03", "1");
            Sticker sticker2 = new Sticker("Alan", "Smith", "2023-03-03", "2");
            Sticker sticker3 = new Sticker("Smith", "Alan", "2023-03-03", "3");
            Sticker sticker4 = new Sticker("John", "Alan", "2023-03-03", "4");

            List<Sticker> stickersSent = Arrays.asList(sticker1, sticker2);
            List<Sticker> stickersReceived = Arrays.asList(sticker3, sticker4);

            User user = new User(userName, CLIENT_REGISTRATION_TOKEN, stickersSent, stickersReceived);

            // Call the create method from userdao to create the new user. --> CC: should be add not create
            userDao.create(user).addOnSuccessListener(result -> {
                Toast.makeText(this, "Registered successfully.", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(result -> {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_LONG).show();
            });
        });

        // Jumps to SendStickersActivity.
        Intent intent = new Intent(this, SendStickersActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}
