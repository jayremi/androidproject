package student.inti.gg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_signup;
    EditText input_name,input_email,input_password;
    TextView link_login;

    FirebaseAuth mFirebaseAuth;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mProgressDialog=new ProgressDialog(this);
        btn_signup=findViewById(R.id.btn_signup);
        input_name=findViewById(R.id.input_name);
        input_email=findViewById(R.id.input_email);
        input_password=findViewById(R.id.txt_search);
        link_login=findViewById(R.id.link_login);

        btn_signup.setOnClickListener(this);
        link_login.setOnClickListener(this);

}

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAuth.getCurrentUser()!=null)
        {

        }
    }



    private void registerUser(){
        final String email=input_email.getText().toString().trim();
        String password=input_password.getText().toString().trim();
        final String name=input_name.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            // email is empty
            toastMessage("Please enter email");

        }
        else if(TextUtils.isEmpty(password))
        {
            //password is empty
            toastMessage("Please enter password");
        }
        else if (TextUtils.isEmpty(name))
        {
            //name is empty
            toastMessage("Please enter name");
        }

        mProgressDialog.setMessage("Registering User...");
        mProgressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user=new User(name,email);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        if(mProgressDialog.isShowing())
                                        {
                                            mProgressDialog.dismiss();
                                        }
                                        toastMessage("Register Successfully");
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),navigationActivity.class));
                                    }
                                    else
                                    {
                                        toastMessage("Fail to resgister.. Please try again");
                                    }

                                }
                            });


                }
                else
                {
                    if(mProgressDialog.isShowing())
                    {
                        mProgressDialog.dismiss();
                    }
                    toastMessage("Fail to resgister.. Please try again");
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v==btn_signup)
        {
            registerUser();
        }

        if(v==link_login)
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class)); //open login activity
        }
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
