package student.inti.gg;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class resViewHolder extends RecyclerView.ViewHolder {

    View mView;
    public resViewHolder(@NonNull View itemView) {
        super(itemView);
        mView=itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemlongClick(view,getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx,String Name,String Rating,String Image,String Category,String Hours,String Phone,String URL,String Address,String Description,String Map,String Mapv1,String Mapv2,String Menu,String Menuview,String Imageview){

        TextView mNameTv=mView.findViewById(R.id.res_name);
        TextView mRatingTv=mView.findViewById(R.id.res_rating);
        ImageView mImageTv=mView.findViewById(R.id.res_image);


        //set data into Views
        mNameTv.setText(Name);
        mRatingTv.setText(Rating);
        //Picasso.get().load(Image.toString()).into(mImageTv);
        Picasso.get().load(Image).into(mImageTv);
    }


    private resViewHolder.ClickLisener mClickListener;

    public interface ClickLisener{
        void onItemClick(View view,int position);
        void onItemlongClick(View view,int position);
    }

    public void setOnClickListener(resViewHolder.ClickLisener clickListener)
    {
        mClickListener=clickListener;
    }


}
