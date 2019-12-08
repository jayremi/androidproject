package student.inti.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventdetailsActivity extends AppCompatActivity {

    TextView mNameTv,mCategoryTv,mDescriptionTv;
    ImageView mImageTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetails);

        //initiate views
        mNameTv=findViewById(R.id.tab_event_name);
        mCategoryTv=findViewById(R.id.tab_event_category);
        mDescriptionTv=findViewById(R.id.tab_event_description);
        mImageTv=findViewById(R.id.tab_event_image);

        //ger data from intent
        String Image=getIntent().getStringExtra("image");
        String Name=getIntent().getStringExtra("name");
        String Category=getIntent().getStringExtra("category");
        String Description=getIntent().getStringExtra("description");

        //set data into views
        mNameTv.setText(Name);
        mCategoryTv.setText(Category);
        mDescriptionTv.setText(Description);
        Picasso.get().load(Image).into(mImageTv);


    }
}
