package student.inti.gg;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class eventViewHolder extends RecyclerView.ViewHolder {

    View mView;
    public eventViewHolder(@NonNull View itemView) {
        super(itemView);
        mView=itemView;

        //Item Click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });

        //long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }


    public void setDetails(Context ctx,String Name,String Image,String Category,String Description)
    {
        //views
        TextView mNameTv=mView.findViewById(R.id.event_name);
        ImageView mImageTv=mView.findViewById(R.id.event_image);
        TextView mCategory=mView.findViewById(R.id.event_category);



        //set data into views
        mNameTv.setText(Name);
        Picasso.get().load(Image).into(mImageTv);
        mCategory.setText(Category);


    }

    private eventViewHolder.ClickListener mClickListener;

    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemlongClick(View view, int position);
    }

    public void setOnClickListener(eventViewHolder.ClickListener clickListener)
    {
        mClickListener=clickListener;
    }
}
