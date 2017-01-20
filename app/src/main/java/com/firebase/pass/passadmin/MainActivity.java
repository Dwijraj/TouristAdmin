package com.firebase.pass.passadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

     /*   if(mAuth.getCurrentUser()==null)
        {
            Intent SIGN_IN=new Intent(MainActivity.this,sign_In_Activity.class);
            finish();
            startActivity(SIGN_IN);
        } */





    }


}
