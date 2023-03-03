package edu.northeastern.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOUser {
    private DatabaseReference databaseReference;

    public DAOUser() {
        //set up database reference
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(User.class.getSimpleName());
    }

    public Task<Void> add(User user){
        //TODO: if (user == null) // throw exception
        return databaseReference.push().setValue(user);
    }
}
