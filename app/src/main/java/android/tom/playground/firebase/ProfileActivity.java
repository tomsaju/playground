
package android.tom.playground.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.tom.playground.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button saveStatus;
    private Button load;
    private EditText userStatus;
    DatabaseReference rootRef,childRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        rootRef = FirebaseDatabase.getInstance().getReference();
        //database reference pointing to demo node
        childRef = rootRef.child("Users");


        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
         buttonLogout = (Button) findViewById(R.id.buttonLogout);
        saveStatus = (Button) findViewById(R.id.saveStatus);
        userStatus = (EditText) findViewById(R.id.userStatus);
        //displaying logged in user name
       // textViewUserEmail.setText("Welcome "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        saveStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogout){
            //logging out the user
           // firebaseAuth.signOut();
            //closing activity
           // finish();
            //starting login activity
          //  startActivity(new Intent(this, LoginActivity.class));

            rootRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //HashMap value = dataSnapshot.getValue(HashMap.class);
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    Map.Entry<String,Object> entry=map.entrySet().iterator().next();
                    String key= entry.getKey();
                    Map<String, Object> mapn= (Map)entry.getValue();
                    String value = (String) mapn.get("status");
                    textViewUserEmail.setText(value);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }else if(view==saveStatus){
            saveData();
        }
    }

    private void saveData() {
        String status = userStatus.getText().toString();
        if(TextUtils.isEmpty(status)){
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
        }else{
            Map<String, String> userData = new HashMap<String, String>();

            userData.put("email", "");
            userData.put("status", status);

            childRef.setValue(userData);
        }
    }
}
