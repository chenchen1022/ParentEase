package edu.northeastern.firebase.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import edu.northeastern.firebase.entity.User;

/**
 * The data access object class for the user entity.
 *
 * @author Chen Chen
 * @author Shichang Ye
 */
public class UserDao {
    private DatabaseReference databaseReference;

    /**
     * Non-argument constructor for the class.
     */
    public UserDao() {
        //Gets the database reference, which is the root of the json document.
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    /**
     * Creates a user in the database.
     *
     * @param user the user to be created
     * @return a task
     */
    public Task<Void> create(User user) {
        Objects.requireNonNull(user);
        return databaseReference.child("users").child(user.getUserToken()).setValue(user);
    }

    /**
     * Add a user in the database.
     *
     * @param user the user to be added
     * @return a task
     */
    public Task <Void> add(User user){
        return databaseReference.push().setValue(user);
    }
}
