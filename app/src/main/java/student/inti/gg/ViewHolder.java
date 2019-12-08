package student.inti.gg;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongCLick(view,getAdapterPosition());
                return true;
            }
        });
    }


    public void setDetails(Context ctx, String Brand, String Name, String Image, String Category,
                           String Description, String Price, String URL, String Website,String Imageview,String Id)
    {
        //Views
        TextView mBrandTv=mView.findViewById(R.id.item_brand);
        TextView mNameTv=mView.findViewById(R.id.item_name);
        ImageView mImageTv=mView.findViewById(R.id.item_firebaseimage);

        //set data into Views
        mBrandTv.setText(Brand);
        mNameTv.setText(Name);
        Picasso.get().load(Image).into(mImageTv);


    }

    private ViewHolder.ClickListener mClickListener;

   public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongCLick(View view,int position);
   }

   public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener=clickListener;
   }

}