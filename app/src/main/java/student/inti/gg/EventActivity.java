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

public class EventActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mRecyclerView=findViewById(R.id.eventrecycler);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mDatabase= FirebaseDatabase.getInstance().getReference("Christmas").child("Event");
        showRecycler();
    }

    private void showRecycler() {

        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<ModelEvent>().setQuery(mDatabase,ModelEvent.class).build();
        FirebaseRecyclerAdapter<ModelEvent,eventViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ModelEvent, eventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull eventViewHolder eventViewHolder, int i, @NonNull ModelEvent modelEvent) {
                eventViewHolder.setDetails(getApplicationContext(),modelEvent.getName(),modelEvent.getImage(),modelEvent.getCategory(),modelEvent.getDescription());

            }

            @NonNull
            @Override
            public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlikeevent,parent,false);
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
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}
