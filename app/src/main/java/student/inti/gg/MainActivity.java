package student.inti.gg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    RecyclerView mRecyclerView2;
    FirebaseDatabase mFirebaseDatabase2;
    DatabaseReference mRef2;

    RecyclerView mRecyclerView3;
    FirebaseDatabase mFirebaseDatabase3;
    DatabaseReference mRef3;

    Button btn_logout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RecyclerView
        mRecyclerView=findViewById(R.id.recycleview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mFirebaseDatabase.getReference("Christmas").child("Top10");

        //RecyclerView2
        mRecyclerView2=findViewById(R.id.recycler2);
        mRecyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView2.setLayoutManager(layoutManager2);
        mFirebaseDatabase2=FirebaseDatabase.getInstance();
        mRef2=mFirebaseDatabase2.getReference("Christmas").child("Restaurant");

        //RecyclerView3
        mRecyclerView3=findViewById(R.id.recycler3);
        mRecyclerView3.setHasFixedSize(true);
        LinearLayoutManager layoutManager3=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView3.setLayoutManager(layoutManager3);
        mFirebaseDatabase3=FirebaseDatabase.getInstance();
        mRef3=mFirebaseDatabase3.getReference("Christmas").child("Event");

        mAuth=FirebaseAuth.getInstance();

        btn_logout=findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                toastMessage("Logged out");
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));            }
        });


    }



    /*protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model,ViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ViewHolder>(
                Model.class,
                R.layout.row,
                ViewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(ViewHolder viewHolder, Model model, int i) {
                viewHolder.setDetails(getApplicationContext(), model.getBrand(), model.getName(), model.getImage(), model.getCategory(), model.getDescription(), model.getPrice(), model.getURL(), model.getWebsite(),model.getImageview());
            }
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
            {
                ViewHolder viewHolder=super.onCreateViewHolder(parent,viewType);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //getdatafrom firebase where clicked
                        String Brand = getItem(position).getBrand();
                        String Name = getItem(position).getName();
                        String Image = getItem(position).getImage();
                        String Category = getItem(position).getCategory();
                        String Description = getItem(position).getDescription();
                        String Price = getItem(position).getPrice();
                        String URL = getItem(position).getURL();
                        String Website = getItem(position).getWebsite();
                        String Imageview=getItem(position).getImageview();


                        //pass data into new activity
                        Intent intent = new Intent(view.getContext(), detailsActivity.class);
                        intent.putExtra("image", Image);//put image URL
                        intent.putExtra("brand", Brand);//put brand
                        intent.putExtra("name", Name);// put name
                        intent.putExtra("category", Category);//put category
                        intent.putExtra("description", Description);//put description
                        intent.putExtra("price", Price);//putprice
                        intent.putExtra("url", URL);//put url
                        intent.putExtra("website", Website);//put website
                        intent.putExtra("imageview",Imageview);
                        startActivity(intent);//start activity
                    }

                    @Override
                    public void onItemLongCLick(View view, int position) {

                    }
                });
                return viewHolder;
            }
        };
        //set adapter to recyclerview
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        FirebaseRecyclerAdapter<ModelRestaurant,resViewHolder>firebaseRecyclerAdapter1=new FirebaseRecyclerAdapter<ModelRestaurant, resViewHolder>(
                ModelRestaurant.class,
                R.layout.restaurantrow,
                resViewHolder.class,
                mRef2) {
            @Override
            protected void populateViewHolder(resViewHolder resViewHolder, ModelRestaurant modelRestaurant, int i) {
                resViewHolder.setDetails(getApplicationContext(),modelRestaurant.getName(),modelRestaurant.getRating(),modelRestaurant.getImage(),modelRestaurant.getCategory(),modelRestaurant.getHours(),modelRestaurant.getPhone(),modelRestaurant.getURL(),modelRestaurant.getAddress(),modelRestaurant.getDescription(),modelRestaurant.getMap(),modelRestaurant.getMapv1(),modelRestaurant.getMapv2(),modelRestaurant.getMenu(),modelRestaurant.getMenuview(),modelRestaurant.getImageview());
            }
            @Override
            public resViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
                resViewHolder resviewHolder =super.onCreateViewHolder(parent,viewType);
                resviewHolder.setOnClickListener(new resViewHolder.ClickLisener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //getdata from firebase where clicked
                        String Name=getItem(position).getName();
                        String Rating=getItem(position).getRating();
                        String Image=getItem(position).getImage();
                        String Category=getItem(position).getCategory();
                        String Hours=getItem(position).getHours();
                        String Phone=getItem(position).getPhone();
                        String URL=getItem(position).getURL();
                        String Address=getItem(position).getAddress();
                        String Description=getItem(position).getDescription();
                        String Map=getItem(position).getMap();
                        String Mapv1=getItem(position).getMapv1();
                        String Mapv2=getItem(position).getMapv2();
                        String Menu=getItem(position).getMenu();
                        String Menuview=getItem(position).getMenuview();
                        String Imageview=getItem(position).getImageview();

                        //pass data into new activity
                        Intent intent =new Intent(view.getContext(),ResdetailsActivity.class);
                        intent.putExtra("name",Name);
                        intent.putExtra("rating",Rating);
                        intent.putExtra("image",Image);
                        intent.putExtra("category",Category);
                        intent.putExtra("hours",Hours);
                        intent.putExtra("phone",Phone);
                        intent.putExtra("url",URL);
                        intent.putExtra("address",Address);
                        intent.putExtra("description",Description);
                        intent.putExtra("map",Map);
                        intent.putExtra("mapv1",Mapv1);
                        intent.putExtra("mapv2",Mapv2);
                        intent.putExtra("menu",Menu);
                        intent.putExtra("menuview",Menuview);
                        intent.putExtra("imageview",Imageview);
                        startActivity(intent);


                    }

                    @Override
                    public void onItemlongClick(View view, int position) {

                    }
                });
                return resviewHolder;
            }

        };

        mRecyclerView2.setAdapter(firebaseRecyclerAdapter1);

        FirebaseRecyclerAdapter<ModelEvent,eventViewHolder>firebaseRecyclerAdapter2=new FirebaseRecyclerAdapter<ModelEvent, eventViewHolder>(
                ModelEvent.class,
                R.layout.eventrow,
                eventViewHolder.class,
                mRef3) {
            @Override
            protected void populateViewHolder(eventViewHolder eventViewHolder, ModelEvent modelEvent, int i) {
                eventViewHolder.setDetails(getApplicationContext(),modelEvent.getName(),modelEvent.getImage(),modelEvent.getCategory(),modelEvent.getDescription());
            }

            @Override
            public eventViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
            {
                eventViewHolder eventviewHolder =super.onCreateViewHolder(parent,viewType);
                eventviewHolder.setOnClickListener(new eventViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //get data from firebase where clicked
                        String Name=getItem(position).getName();
                        String Image=getItem(position).getImage();
                        String Category=getItem(position).getCategory();
                        String Description=getItem(position).getDescription();

                        //pass data into new activity
                        Intent intent=new Intent(view.getContext(),EventdetailsActivity.class);
                        intent.putExtra("name",Name);
                        intent.putExtra("image", Image);
                        intent.putExtra("category",Category);
                        intent.putExtra("description",Description);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemlongClick(View view, int position) {

                    }
                });
                return eventviewHolder;
            }
        };
        mRecyclerView3.setAdapter(firebaseRecyclerAdapter2);
    }*/




    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
