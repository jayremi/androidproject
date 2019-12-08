package student.inti.gg;

import android.content.Intent;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class HomeFragment extends Fragment {


    RecyclerView mRecyclerView,mRecyclerView2,mRecyclerView3;
    FirebaseDatabase mFirebaseDatabase,mFirebaseDatabase2,mFirebaseDatabase3;
    DatabaseReference mRef,mRef2,mRef3;
    Button btn_logout;
    private FirebaseAuth mAuth;

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_home,container,false);

        mAuth= FirebaseAuth.getInstance();

        btn_logout=(Button)v.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity().getApplicationContext(),LoginActivity.class));
            }
        });

        //Recyclerview1
        mRecyclerView=(RecyclerView)v.findViewById(R.id.recycler1);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Christmas").child("Top10");
        showRecycler1();


        //Recyclerview2
        mRecyclerView2=(RecyclerView)v.findViewById(R.id.recycler2);
        mRecyclerView2.setHasFixedSize(true);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView2.setLayoutManager(layoutManager2);
        mFirebaseDatabase2=FirebaseDatabase.getInstance();
        mRef2=mFirebaseDatabase2.getReference("Christmas").child("Restaurant");
        showRecycler2();

        //Recyclerview3
        mRecyclerView3=(RecyclerView)v.findViewById(R.id.recycler3);
        mRecyclerView3.setHasFixedSize(true);
        LinearLayoutManager layoutManager3=new LinearLayoutManager(getActivity());
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView3.setLayoutManager(layoutManager3);
        mFirebaseDatabase3=FirebaseDatabase.getInstance();
        mRef3=mFirebaseDatabase3.getReference("Christmas").child("Event");
        showRecycler3();
        return v;
    }


    private void showRecycler1(){
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Model>().setQuery(mRef, Model.class).build();
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Model model) {
                viewHolder.setDetails(getActivity().getApplicationContext(),model.getBrand(), model.getName(), model.getImage(), model.getCategory(),
                        model.getDescription(), model.getPrice(), model.getURL(), model.getWebsite(),model.getImageview(),model.getId());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                ViewHolder viewHolder=new ViewHolder(itemView);
                viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String Brand = getItem(position).getBrand();
                        String Name = getItem(position).getName();
                        String Image = getItem(position).getImage();
                        String Category = getItem(position).getCategory();
                        String Description = getItem(position).getDescription();
                        String Price = getItem(position).getPrice();
                        String URL = getItem(position).getURL();
                        String Website = getItem(position).getWebsite();
                        String Imageview=getItem(position).getImageview();
                        String Id=getItem(position).getId();



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
                        intent.putExtra("id",Id);
                        startActivity(intent);//start activity

                    }

                    @Override
                    public void onItemLongCLick(View view, int position) {

                    }
                });

                return viewHolder;
            }
        };

        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    private void showRecycler2()
    {
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<ModelRestaurant>().setQuery(mRef2,ModelRestaurant.class).build();
        FirebaseRecyclerAdapter<ModelRestaurant,resViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ModelRestaurant, resViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull resViewHolder resViewHolder, int i, @NonNull ModelRestaurant modelRestaurant) {
                resViewHolder.setDetails(getActivity().getApplicationContext(),modelRestaurant.getName(),modelRestaurant.getRating(),modelRestaurant.getImage(),modelRestaurant.getCategory(),modelRestaurant.getHours(),modelRestaurant.getPhone(),modelRestaurant.getURL(),modelRestaurant.getAddress(),modelRestaurant.getDescription(),modelRestaurant.getMap(),modelRestaurant.getMapv1(),modelRestaurant.getMapv2(),modelRestaurant.getMenu(),modelRestaurant.getMenuview(),modelRestaurant.getImageview());
            }

            @NonNull
            @Override
            public resViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurantrow,parent,false);
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
        mRecyclerView2.setAdapter(firebaseRecyclerAdapter);

    }

    private void showRecycler3()
    {
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<ModelEvent>().setQuery(mRef3,ModelEvent.class).build();
        FirebaseRecyclerAdapter<ModelEvent,eventViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ModelEvent, eventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull eventViewHolder eventViewHolder, int i, @NonNull ModelEvent modelEvent) {
                eventViewHolder.setDetails(getActivity().getApplicationContext(),modelEvent.getName(),modelEvent.getImage(),modelEvent.getCategory(),modelEvent.getDescription());

            }

            @NonNull
            @Override
            public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.eventrow,parent,false);
                eventViewHolder viewHolder=new eventViewHolder(itemView);
                viewHolder.setOnClickListener(new eventViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
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
                return viewHolder;
            }
        };
        firebaseRecyclerAdapter.startListening();
        mRecyclerView3.setAdapter(firebaseRecyclerAdapter);

    }

}
