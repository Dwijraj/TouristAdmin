package com.firebase.pass.passadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sign_In_Activity extends AppCompatActivity {




    private EditText Phone;
    private EditText Name;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;
    private Button buttons;
    //TelephonyManager Object to help fetch IMEI of the mobile
    private ProgressDialog prog;
    private DatabaseReference mDatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in_);

        Name=(EditText)findViewById(R.id.editText4);
        Phone=(EditText)findViewById(R.id.editText3);
        prog=new ProgressDialog(this);
        buttons=(Button)findViewById(R.id.button);
        mDatabaseref= FirebaseDatabase.getInstance().getReference();   //Points to the Users child  of the root parent

        // telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);  //Telephony manager object is initiated
        mAuth=FirebaseAuth.getInstance();                           //Firebase Auth instance
        mAuthlistener=new FirebaseAuth.AuthStateListener() {        //Firebase Auth Listener that checks if the user if logged in
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


            }
        };


        buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!(Phone.getText().toString().isEmpty() && Name.getText().toString().isEmpty())) {            //makes sure that user enter his/her phone number
                    prog.setMessage("Signing you..");



                    final String IMEI = Phone.getText().toString().trim();
                    final String EMAIL = IMEI + "@" + IMEI + ".com";
                    final String PASSWORD = IMEI;
                    prog.show();


                    mAuth.signInWithEmailAndPassword("admin@admin.com","Admin123").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            mDatabaseref.child("Admin").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.hasChild(IMEI))
                                    {
                                        prog.dismiss();
                                        Intent Apply = new Intent(sign_In_Activity.this, MainActivity.class);
                                        finish();
                                        startActivity(Apply);

                             /*   mAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {


                                        prog.dismiss();
                                        Toast.makeText(getApplicationContext(), "ACCOUNT CREATED", Toast.LENGTH_SHORT).show();




                                        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {



                                                DatabaseReference  particular_user = mDatabaseref.child("Admins").child(mAuth.getCurrentUser().getUid());
                                                particular_user.child("IMEI").setValue(IMEI);
                                                particular_user.child("Name").setValue(Name.getText().toString().trim());
                                                particular_user.child("Contact").setValue(Phone.getText().toString());

                                                prog.dismiss();
                                                Toast.makeText(getApplicationContext(), "Signed in successful", Toast.LENGTH_SHORT).show();

                                                Intent Apply = new Intent(sign_In_Activity.this, MainActivity.class);
                                                finish();
                                                startActivity(Apply);


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Log.v("TAG1", "CREATED CAN'T SIGNED IN");

                                                prog.dismiss();
                                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {


                                        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {


                                                prog.dismiss();
                                                  Toast.makeText(getApplicationContext(), "Signed in successful", Toast.LENGTH_SHORT).show();

                                                Intent Apply = new Intent(sign_In_Activity.this, MainActivity.class);
                                                Apply.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(Apply);


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                prog.dismiss();
                                                Toast.makeText(getApplicationContext(),"Couldn't sign you up please check internet settings", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }
                                }); */

                                    }
                                    else
                                    {
                                        prog.dismiss();
                                        Toast.makeText(getApplicationContext(),"You are not authorized",Toast.LENGTH_SHORT).show();
                                    }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });



                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter Phone number...",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
