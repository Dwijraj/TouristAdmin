package com.firebase.pass.passadmin;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private Button AUTHENTICATE_GAURD;
    private Button VIEW_PASS;
    private EditText PASS_NUMBER;
    private Button CHANGE_GATE_PASSWORD;
    private Dialog dialog;
    private DatabaseReference ROOT_REF;
    private DatabaseReference Gaurds;
    private FirebaseAuth mAuth;
    private ImageView CLEAR_VIEW;
    private static final  String[] PLACES={"","1","2","3"};
    private Button CHANGE_DESTINATION_PASS_PRICE;

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ROOT_REF=FirebaseDatabase.getInstance().getReference();
        CHANGE_DESTINATION_PASS_PRICE=(Button) findViewById(R.id.CHANGE_DESTINATION_PRICE);
        CLEAR_VIEW=(ImageView) findViewById(R.id.CLEAR_VIEW_IMAGE);
        mAuth=FirebaseAuth.getInstance();
        Gaurds= FirebaseDatabase.getInstance().getReference();
        dialog= new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AUTHENTICATE_GAURD=(Button)findViewById(R.id.AUTHENTICATE_USER_ID);
        VIEW_PASS=(Button)findViewById(R.id.VIEW_PASS_ID);
        CHANGE_GATE_PASSWORD=(Button)findViewById(R.id.CHANGE_GATE_PASSWORD_ID);
        PASS_NUMBER=(EditText)findViewById(R.id.PASS_NUMBER_ID);



        CLEAR_VIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PASS_NUMBER.setText("");


            }
        });


        mAuth.signInWithEmailAndPassword("admin@admin.com","Admin123");


        VIEW_PASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String PASS_NUMBER_=PASS_NUMBER.getText().toString().trim();
                if(!TextUtils.isEmpty(PASS_NUMBER_))
                {

                    if(mAuth.getCurrentUser()==null)
                    {
                        Toast.makeText(getApplicationContext(),"NULL",Toast.LENGTH_SHORT).show();
                    }

                    Intent I=new Intent(MainActivity.this,Display.class);
                    I.putExtra("Pass",PASS_NUMBER_);
                    startActivity(I);


                }
            }
        });






        CHANGE_GATE_PASSWORD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.setContentView(R.layout.gaurd_gate);
                dialog.show();

                final EditText NAME=(EditText) dialog.findViewById(R.id.GateANameEdit);
                final EditText PHONE=(EditText) dialog.findViewById(R.id.GateBPhoneEdit);
                Button SUBMIT=(Button) dialog.findViewById(R.id.Submit_button);

                SUBMIT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!TextUtils.isEmpty(NAME.getText().toString().trim()))
                        {
                            Gaurds.child("Gate").child("GateA").setValue(NAME.getText().toString().trim());

                        }
                        if(!TextUtils.isEmpty(PHONE.getText().toString().trim()))
                        {
                            Gaurds.child("Gate").child("GateB").setValue(PHONE.getText().toString().trim());

                        }

                    }
                });



            }
        });
        AUTHENTICATE_GAURD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.gaurd_gate);
                dialog.show();

                TextView Name =(TextView) dialog.findViewById(R.id.GateANameText);
                TextView Password=(TextView) dialog.findViewById(R.id.GateBPhoneText);
                final EditText NAME=(EditText) dialog.findViewById(R.id.GateANameEdit);
                final EditText PHONE=(EditText) dialog.findViewById(R.id.GateBPhoneEdit);
                PHONE.setInputType(InputType.TYPE_CLASS_PHONE);
                Button SUBMIT=(Button) dialog.findViewById(R.id.Submit_button);
                NAME.setHint("Name of the Gaurd");
                PHONE.setHint("Mobile number of the Gaurd");
                Password.setText("Phone Number");
                Name.setText("Name");

                SUBMIT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String Name =NAME.getText().toString().trim();
                        final String Phone=PHONE.getText().toString().trim();

                        if(!(TextUtils.isEmpty(Name)|| TextUtils.isEmpty(Phone) || Phone.length()!=10))
                        {
                            mAuth.createUserWithEmailAndPassword(Phone+"@"+Phone+".com",Phone).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    mAuth.signInWithEmailAndPassword(Phone+"@"+Phone+".com",Phone).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {

                                            DatabaseReference df=  Gaurds.child("Guards").child(mAuth.getCurrentUser().getUid());
                                            df.child("Contact").setValue(Phone);
                                            df.child("Name").setValue(Name).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    Toast.makeText(getApplicationContext(),"USER CREATED",Toast.LENGTH_SHORT).show();

                                                }
                                            });

                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"FAILED TO CREATE USER",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Make sure fields are field and mobile number is valid 10 digit number",Toast.LENGTH_SHORT).show();
                        }



                    }
                });


            }
        });

        CHANGE_DESTINATION_PASS_PRICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.price_change);
                dialog.getWindow().setLayout(600,200);
                dialog.show();

                Spinner PLACES_SPINNER=(Spinner) dialog.findViewById(R.id.PLACE_SELECT);
                final EditText NEW_COST=(EditText) dialog.findViewById(R.id.COST_CHANGED);
                Button MAKE_CHANGES=(Button) dialog.findViewById(R.id.BUTTON_PRICE_CHANGE);
                CustomAdapter customAdapter1=new CustomAdapter(MainActivity.this,PLACES);
                PLACES_SPINNER.setAdapter(customAdapter1);

                MAKE_CHANGES.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(MainActivity.this,"HELLO" + CustomAdapter.PLACE_NAME,Toast.LENGTH_SHORT).show();

                        if (!(CustomAdapter.PLACE_NAME.equals("NO PLACE SELECTED")||TextUtils.isEmpty(NEW_COST.getText().toString().trim()))&& TextUtils.isDigitsOnly(NEW_COST.getText().toString().trim()))
                        {
                            ROOT_REF.child("Prices").child(CustomAdapter.PLACE_NAME).setValue(NEW_COST.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(getApplicationContext(),"CHANGES MADE SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                });




            }
        });




    }


}
