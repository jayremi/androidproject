package student.inti.gg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RestaurantActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        mRecyclerView=findViewById(R.id.restaurantrecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mDatabase= FirebaseDatabase.getInstance().getReference("Christmas").child("Restaurant");
        showRecycler();
    }

    private void showRecycler() {
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<ModelRestaurant>().setQuery(mDatabase,ModelRestaurant.class).build();
        FirebaseRecyclerAdapter<ModelRestaurant,resViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ModelRestaurant, resViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull resViewHolder resViewHolder, int i, @NonNull ModelRestaurant modelRestaurant) {
                resViewHolder.setDetails(getApplicationContext(),modelRestaurant.getName(),modelRestaurant.getRating(),modelRestaurant.getImage(),modelRestaurant.getCategory(),modelRestaurant.getHours(),modelRestaurant.getPhone(),modelRestaurant.getURL(),modelRestaurant.getAddress(),modelRestaurant.getDescription(),modelRestaurant.getMap(),modelRestaurant.getMapv1(),modelRestaurant.getMapv2(),modelRestaurant.getMenu(),modelRestaurant.getMenuview(),modelRestaurant.getImageview());

            }

            @NonNull
            @Override
            public resViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlikedrestaurant,parent,false);
                resViewHolder viewHolder=new resViewHolder(itemView);
                viewHolder.setOnClickListener(new resViewHolder.ClickLisener() {
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
                return viewHolder;
            }
        };
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }
}
