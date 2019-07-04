package com.hit.ispace;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<SpaceShipShop> spaceShip;
    private Context mContext;

    public ShopAdapter(List<SpaceShipShop> spaceShip) {
        this.spaceShip = spaceShip;
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
        SpaceShipShop space = spaceShip.get(i);
        holder.price.setText(String.format("%02d" ,space.getPrice()));
        //holder.imageSpaceShip.setImageDrawable(space.getSrc_path());
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
        private ImageView imageSpaceShip;
        private TextView price;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageSpaceShip = itemView.findViewById(R.id.image_space_ship);
            price = itemView.findViewById(R.id.price_space_ship);
        }
    }
}