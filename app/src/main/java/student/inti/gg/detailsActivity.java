package student.inti.gg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.jar.Attributes;

import static student.inti.gg.R.drawable.ic_favorite_border;
import static student.inti.gg.R.drawable.ic_like;


public class detailsActivity extends AppCompatActivity {
    TextView mBrandTv,mNameTV,mCategoryTv,mDescriptionTV,mPriceTv,mWebsiteTv,mUrlTv;
    ImageView mImageTV;
    Button mButton;

    Button rd_like;
    boolean checked;

    String Name,ID,Image,Brand,Category,Description,Price,URL,Website,Imageview,Status;

    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //initiate views
        mBrandTv=findViewById(R.id.tab_brand);
        mNameTV=findViewById(R.id.tab_name);
        mCategoryTv=findViewById(R.id.tab_category);
        mDescriptionTV=findViewById(R.id.tab_description);
        mPriceTv=findViewById(R.id.tab_price);
        mWebsiteTv=findViewById(R.id.tab_website);
        mButton=findViewById(R.id.bt_buy);

        //mImageTV=findViewById(R.id.tab_image);

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        mImageTV=findViewById(R.id.photo_view);
        rd_like=findViewById(R.id.likeButton);




        //get data from intent
        final String Image = getIntent().getStringExtra("image");
        this.Image=Image;
        String brand=getIntent().getStringExtra("brand");
        this.Brand=brand;
        String name=getIntent().getStringExtra("name");
        this.Name =name;
        String category=getIntent().getStringExtra("category");
        this.Category=category;
        String description=getIntent().getStringExtra("description");
        this.Description=description;
        String price=getIntent().getStringExtra("price");
        this.Price=price;
        final String url=getIntent().getStringExtra("url");
        this.URL=url;
        String website=getIntent().getStringExtra("website");
        this.Website=website;
        final String Imageview=getIntent().getStringExtra("imageview");
        this.Imageview=Imageview;
        String id=getIntent().getStringExtra("id");
        this.ID=id;


        //set data to views
        mBrandTv.setText(brand);
        mNameTV.setText(name);
        Picasso.get().load(Image).into(mImageTV);
        mCategoryTv.setText(category);
        mDescriptionTV.setText(description);
        mWebsiteTv.setText(website);
        mPriceTv.setText(price);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String uid = user.getUid();
            Query query = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Id list").child(Name);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        checked=true;
                        rd_like.setBackgroundResource(ic_like);

                    }
                    else
                    {
                        checked=false;
                        rd_like.setBackgroundResource(ic_favorite_border);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }





        rd_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked==false)
                {
                    checked=true;
                    rd_like.setBackgroundResource(ic_like);
                    toastMessage("Added to your library");
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null)
                    {
                        String uid = user.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("Users").child(uid).child("Liked").child("Id list").child(Name).setValue(ID);
                        moveFirebaseRecord();
                    }

                }
                else if(checked==true){
                    checked=false;
                    rd_like.setBackgroundResource(ic_favorite_border);
                    toastMessage("Removed from your library");
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null)
                    {
                        String uid = user.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Id list").child(Name);
                        mDatabase.removeValue();
                        removeFirebaseRecord();


                    }
                }
            }
        });




        mImageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(detailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.photoview, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                Picasso.get().load(Imageview).into(photoView);
                //photoView.setImageResource();
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent viewIntent =new Intent("android.intent.action.VIEW", Uri.parse(url));
                startActivity(viewIntent);
            }
        });

    }

    private void removeFirebaseRecord() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String uid = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Liked item").child(Name);
            mDatabase.removeValue();

        }


    }

    private void moveFirebaseRecord() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String uid = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Liked item").child(Name);
            mDatabase.child("Name").setValue(Name);
            mDatabase.child("Id").setValue(ID);
            mDatabase.child("Image").setValue(Image);
            mDatabase.child("Imageview").setValue(Imageview);
            mDatabase.child("Brand").setValue(Brand);
            mDatabase.child("Category").setValue(Category);
            mDatabase.child("Description").setValue(Description);
            mDatabase.child("Price").setValue(Price);
            mDatabase.child("URL").setValue(URL);
            mDatabase.child("Website").setValue(Website);
        }

    }


    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}

