package student.inti.gg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



public class SearchbarActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    EditText search_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbar);


        search_txt=findViewById(R.id.txt_search);
        search_txt.requestFocus();
        mRecyclerView=findViewById(R.id.recyclersearch);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mFirebaseDatabase.getReference("Christmas").child("Top10");
        search_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text=search_txt.getText().toString();
                firebaseSearch(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text=search_txt.getText().toString();
                firebaseSearch(text);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text=search_txt.getText().toString();
                firebaseSearch(text);
            }
        });
    }


    //searchdata
    private void firebaseSearch(String SearchText)
    {
        String query=SearchText.toLowerCase();
        Query firebaseSearchQuery=mRef.orderByChild("Search").startAt(query).endAt(query+"\uf8ff");
        FirebaseRecyclerOptions options =new FirebaseRecyclerOptions.Builder<Model>().setQuery(firebaseSearchQuery,Model.class).build();
        FirebaseRecyclerAdapter<Model,ViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Model model) {
                viewHolder.setDetails(getApplicationContext(),model.getBrand(), model.getName(), model.getImage(), model.getCategory(), model.getDescription(),
                        model.getPrice(), model.getURL(), model.getWebsite(),model.getImageview(),model.getId());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlikeitems,null,false);
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
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

}
