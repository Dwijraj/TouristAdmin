package com.firebase.pass.passadmin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Display extends AppCompatActivity {

    private ImageView scan_id2;
    private TextView Name2;
    private TextView Address2;
    private TextView Mobile2;
    private TextView CarNumber;
    private TextView DriverName;
    private TextView Dateofbirth2;
    private TextView Dateofjourney2;
     private TextView ID_No2;
    private TextView Purpose2;
     private ImageView Profile2;
    private  String pass;
    private TextView ID_Sources;
    private TextView Application_status2;
    private DatabaseReference ApplicationRef2;
    private DatabaseReference UsersRef;
    private FirebaseAuth mAuth;
    private  Application app;
    private Button Verify_button;
    private int WIDTH_SCREEN;
    private int HEIGHT_SCREEN;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

         WindowManager wm = (WindowManager) Display.this.getSystemService(Context.WINDOW_SERVICE);
         android.view.Display display = wm.getDefaultDisplay();
         DisplayMetrics metrics = new DisplayMetrics();
         display.getMetrics(metrics);
         WIDTH_SCREEN = metrics.widthPixels;
         HEIGHT_SCREEN = metrics.heightPixels;
        CarNumber=(TextView)findViewById(R.id.car_num);
        DriverName=(TextView)findViewById(R.id.driver_name);
        scan_id2=(ImageView)findViewById(R.id.SCAN_PIC) ;
        Name2=(TextView)findViewById(R.id.SCAN_NAME);
        Address2=(TextView)findViewById(R.id.SCAN_ADDRESS);
        Mobile2=(TextView)findViewById(R.id.SCAN_MOBILE);
        ID_No2=(TextView)findViewById(R.id.SCAN_ID);
        ID_Sources=(TextView)findViewById(R.id.ID_Source);
        mAuth=FirebaseAuth.getInstance();
        Dateofbirth2=(TextView)findViewById(R.id.SCAN_DOB);
        Dateofjourney2=(TextView)findViewById(R.id.SCAN_DOJ);
        Purpose2=(TextView)findViewById(R.id.SCAN_REASON);
        Profile2=(ImageView)findViewById(R.id.SCAN_PROFILE);
        Application_status2=(TextView)findViewById(R.id.SCAN_STATUS);
        ApplicationRef2= FirebaseDatabase.getInstance().getReference().child("Applications");//Points to the root directory of the Database
        UsersRef=FirebaseDatabase.getInstance().getReference();
        Verify_button=(Button)findViewById(R.id.VERIFY_BUTTON);

         android.view.ViewGroup.LayoutParams layoutParams = Profile2.getLayoutParams();
         layoutParams.width = WIDTH_SCREEN/2;
         layoutParams.height = HEIGHT_SCREEN/3;
         Profile2.setLayoutParams(layoutParams);

         android.view.ViewGroup.LayoutParams layoutParamss = scan_id2.getLayoutParams();
         layoutParamss.width = WIDTH_SCREEN/2;
         layoutParamss.height = HEIGHT_SCREEN/3;
         scan_id2.setLayoutParams(layoutParamss);



         Intent i=getIntent();
        Bundle extras=i.getExtras();
        pass=extras.getString("Pass");

         ApplicationRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(pass))
                {

                    ApplicationRef2.child(pass).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            app=dataSnapshot.getValue(Application.class);  //App crasehs due to this line

                            Name2.setText(app.Name.toUpperCase());
                            Address2.setText(app.Address.toUpperCase());
                            Mobile2.setText(app.Mobile.toUpperCase());
                            ID_No2.setText(app.ID_No.toUpperCase());
                            Dateofbirth2.setText(app.DateOfBirth.toUpperCase());
                            Dateofjourney2.setText(app.DateOfJourney.toUpperCase());
                            Purpose2.setText(app.Purpose.toUpperCase());
                            ID_Sources.setText(app.ID_Source.toUpperCase());
                            CarNumber.setText(app.Carnumber.toUpperCase());
                            DriverName.setText(app.Drivername.toUpperCase());
                            Application_status2.setText(app.ApplicationStatus.toUpperCase());



                            Glide.with(getApplicationContext())
                                    .load(app.ApplicantPhoto)
                                    .into(Profile2);


                            Glide.with(getApplicationContext())
                                    .load(app.ApplicantScanId)
                                    .into(scan_id2);




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });





                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No such application exists",Toast.LENGTH_SHORT).show();
                  //  finish();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         Verify_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 AlertDialog.Builder builder = new AlertDialog.Builder(Display.this);
                 builder.setMessage("Confirm verification of the application")
                         .setTitle("Confirmation")
                         .setNegativeButton("No", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                                 dialog.dismiss();
                             }
                         }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                         ApplicationRef2.child(pass).child("ApplicationStatus").setValue("Verified");
                         UsersRef.child("VerifiedUsers").child(pass).setValue("Verified");
                         UsersRef.child("Users").child(app.Uid).child("Applications").child(pass).setValue("Verified");
                     }
                 });

                 AlertDialog dialog=builder.create();
                 dialog.show();


             }
         });






    }
}
