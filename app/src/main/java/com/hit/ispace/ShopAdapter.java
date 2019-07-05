package com.hit.ispace;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<SpaceShipShop> spaceShip;
    private Context mContext;
    Dialog myDialog;
    TextView name_space , price_space ;

    public ShopAdapter(Context context , List<SpaceShipShop> spaceShip) {
        this.spaceShip = spaceShip;
        mContext = context;
    }

    //Inflate layout in view holder
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parant, int i) {
        View view = LayoutInflater.from(parant.getContext()).inflate(R.layout.pattern_shop, parant, false);
        ShopAdapter.ShopViewHolder spaceShipViewHolder = new ShopAdapter.ShopViewHolder(view);
        return spaceShipViewHolder;
    }

    //Set display values of view objects.
    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ShopViewHolder holder, int i) {
        final SpaceShipShop space = spaceShip.get(i);
        holder.price.setText(String.format("%d" ,space.getPrice()));
        Glide.with(mContext)
                .asBitmap()
                .load(R.drawable.spaceship_5)
                .into(holder.imageSpaceShip);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_shop);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        holder.imageSpaceShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_space = (TextView) myDialog.findViewById(R.id.name_space_ship);
                price_space = (TextView) myDialog.findViewById(R.id.price_of_space_ship);
                name_space.setText(space.getName_ship());
                price_space.setText(String.valueOf(space.getPrice()) + "$");
                //Toast.makeText(mContext, space.getName_ship(), Toast.LENGTH_SHORT).show();
                myDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return spaceShip.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ShopViewHolder extends RecyclerView.ViewHolder
    {
        private CircleImageView imageSpaceShip;
        private TextView price;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSpaceShip = itemView.findViewById(R.id.image_space_ship);
            price = itemView.findViewById(R.id.price_space_ship);
        }
    }
}