package edu.northeastern.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.northeastern.atyourservice.R;
import edu.northeastern.firebase.entity.Sticker;
import edu.northeastern.firebase.entity.User;

/**
 * The class for Stickers Received activity.
 *
 * @author ShiChang Ye
 * @author Chen Chen
 * @author Lin Han
 * @author Manping Zhao
 */
public class StickersCollectedHistory extends AppCompatActivity {
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";
    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";

    private List<Sticker> stickersList;
    private RecyclerView stickerRecyclerView;

    private String userName;
    private User currentUser;
    private DatabaseReference myDatabase;

    private StickersAdapter stickersAdapter;
    private RecyclerView.LayoutManager stickersLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers_received_history);
        init(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        userName = extras.getString("userName");
        myDatabase = FirebaseDatabase.getInstance().getReference();

        currentUser = getIntent().getExtras().getParcelable("User");
        System.out.println(currentUser);
        System.out.println("Here I am");

        //System.out.println(currUser);
        


    }

    /**
     * Creates the recycler view.
     */
    private void createRecyclerView() {
        stickerRecyclerView = findViewById(R.id.receivedStickersRecyclerView);
        stickersLayoutManager = new LinearLayoutManager(this);
        stickersAdapter = new StickersAdapter(stickersList);

        stickerRecyclerView.setHasFixedSize(true);
        stickerRecyclerView.setLayoutManager(stickersLayoutManager);
        stickerRecyclerView.setAdapter(stickersAdapter);
        stickerRecyclerView.setItemAnimator(null);

        stickersList = new ArrayList<Sticker>();
        Sticker sticker1 = new Sticker("Alan", "John", "2023-03-03", "1");
        for (Sticker s: currentUser.getStickersReceived()){
            stickersList.add(s);
        }

        stickersAdapter = new StickersAdapter(stickersList);
        stickerRecyclerView.setAdapter(stickersAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (stickersList == null || stickersList.size() == 0) {
            return;
        }
        int size = stickersList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            outState.putString(KEY_OF_INSTANCE + i + "0", stickersList.get(i).getReceiver());
            outState.putString(KEY_OF_INSTANCE + i + "1", stickersList.get(i).getSender());
            outState.putString(KEY_OF_INSTANCE + i + "2", stickersList.get(i).getStickerDes());
            outState.putString(KEY_OF_INSTANCE + i + "3", stickersList.get(i).getTimeStamp());
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialization of the saved state.
     */
    private void init(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            initialItemData(savedInstanceState);
            createRecyclerView();
        }
    }

    /**
     * Initializes the data in the Bundle instance if it is not empty.
     *
     * @param savedInstanceState the Bundle instance
     */
    private void initialItemData(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (stickersList == null || stickersList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieves weather objects stored in the state.
                for (int i = 0; i < size; i++) {
                    String receiver = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String sender = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String stickerDesc = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");
                    String timeStamp = savedInstanceState.getString(KEY_OF_INSTANCE + i + "3");
                    Sticker sticker = new Sticker(sender, receiver, stickerDesc, timeStamp);
                    stickersList.add(sticker);
                }
            }
        }
    }
}
