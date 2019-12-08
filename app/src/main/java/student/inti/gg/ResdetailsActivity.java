package student.inti.gg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import static student.inti.gg.R.drawable.ic_favorite_border;
import static student.inti.gg.R.drawable.ic_like;

public class ResdetailsActivity extends FragmentActivity implements OnMapReadyCallback {

    TextView mNameTv, mRatingTv, mCategoryTv, mHoursTv, mPhoneTv, mAddressTv, mDescriptionTv;
    ImageView mImageTV,mMenuTv;
    Button mButton;
    Button bt_like;
    
    DatabaseReference mDatabase;


    GoogleMap mGoogleMap;
    String Mapv1,Mapv2,Name;
    String Image,Rating,Category,Description,Hours,Phone,Address,URL,Map,Menu,Menuview,Imageview;
    boolean checked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resdetails);

        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        //initiate Views
        mNameTv = findViewById(R.id.tab_res_name);
        mRatingTv = findViewById(R.id.tab_res_rating);
        mCategoryTv = findViewById(R.id.tab_res_category);
        mHoursTv = findViewById(R.id.tab_res_hours);
        mPhoneTv = findViewById(R.id.tab_res_phone);
        mAddressTv = findViewById(R.id.tab_res_address);
        mDescriptionTv = findViewById(R.id.tab_res_description);
        mButton = findViewById(R.id.bt_fb);
        mImageTV = findViewById(R.id.res_photo_view);
        mMenuTv=findViewById(R.id.res_menu_photo_view);
        bt_like=findViewById(R.id.res_likeButton);


        //get data from intent
        String Image = getIntent().getStringExtra("image");
        this.Image=Image;
        final String Name = getIntent().getStringExtra("name");
        this.Name=Name;
        final String Rating = getIntent().getStringExtra("rating");
        this.Rating=Rating;
        final String Category = getIntent().getStringExtra("category");
        this.Category=Category;
        String Hours = getIntent().getStringExtra("hours");
        this.Hours=Hours;
        String Phone = getIntent().getStringExtra("phone");
        this.Phone=Phone;
        String Address = getIntent().getStringExtra("address");
        this.Address=Address;
        String Description = getIntent().getStringExtra("description");
        this.Description=Description;
        final String URL = getIntent().getStringExtra("url");
        this.URL=URL;
        String Map = getIntent().getStringExtra("map");
        this.Map=Map;
        String Mapv1=getIntent().getStringExtra("mapv1");
        this.Mapv1=Mapv1;
        String Mapv2=getIntent().getStringExtra("mapv2");
        this.Mapv2=Mapv2;
        String Menu=getIntent().getStringExtra("menu");
        this.Menu=Menu;
        final String Imageview=getIntent().getStringExtra("imageview");
        this.Imageview=Imageview;
        final String Menuview=getIntent().getStringExtra("menuview");
        this.Menuview=Menuview;




        //set data into views
        mNameTv.setText(Name);
        mRatingTv.setText(Rating);
        Picasso.get().load(Image).into(mImageTV);
        mCategoryTv.setText(Category);
        mHoursTv.setText(Hours);
        mPhoneTv.setText(Phone);
        mAddressTv.setText(Address);
        mDescriptionTv.setText(Description);
        Picasso.get().load(Menu).into(mMenuTv);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String uid = user.getUid();
            Query query = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Restaurant list").child(Name);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        checked=true;
                        bt_like.setBackgroundResource(ic_like);

                    }
                    else
                    {
                        checked=false;
                        bt_like.setBackgroundResource(ic_favorite_border);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        bt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked==false)
                {
                    checked=true;
                    bt_like.setBackgroundResource(ic_like);
                    toastMessage("Added to your library");
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null)
                    {
                        String uid = user.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("Users").child(uid).child("Liked").child("Restaurant list").child(Name).setValue(Name);
                        moveFirebaseRecord();
                    }

                }
                else if(checked==true){
                    checked=false;
                    bt_like.setBackgroundResource(ic_favorite_border);
                    toastMessage("Removed from your library");
                    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null)
                    {
                        String uid = user.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Restaurant list").child(Name);
                        mDatabase.removeValue();
                        removeFirebaseRecord();


                    }
                }
            }
        });


        mImageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResdetailsActivity.this);
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
                Intent viewIntent =new Intent("android.intent.action.VIEW", Uri.parse(URL));
                startActivity(viewIntent);
            }
        });

        mMenuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ResdetailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.photoview, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                Picasso.get().load(Menuview).into(photoView);
                //photoView.setImageResource();
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

    }

    private void removeFirebaseRecord() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String uid = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Liked Restaurant").child(Name);
            mDatabase.removeValue();

        }
    }

    private void moveFirebaseRecord() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            String uid = user.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Liked").child("Liked Restaurant").child(Name);
            mDatabase.child("Name").setValue(Name);
            mDatabase.child("Image").setValue(Image);
            mDatabase.child("Imageview").setValue(Imageview);
            mDatabase.child("Rating").setValue(Rating);
            mDatabase.child("Category").setValue(Category);
            mDatabase.child("Phone").setValue(Phone);
            mDatabase.child("Description").setValue(Description);
            mDatabase.child("Address").setValue(Address);
            mDatabase.child("Hours").setValue(Hours);
            mDatabase.child("Map").setValue(Map);
            mDatabase.child("Mapv1").setValue(Mapv1);
            mDatabase.child("Mapv2").setValue(Mapv2);
            mDatabase.child("URL").setValue(URL);
            mDatabase.child("Menu").setValue(Menu);
            mDatabase.child("Menuview").setValue(Menuview);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;

        double v1 = Double.parseDouble(Mapv1);
        double v2 = Double.parseDouble(Mapv2);
        LatLng restaurant = new LatLng(v1,v2);
        mGoogleMap.addMarker(new MarkerOptions().position(restaurant).title(Name));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant));

    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
