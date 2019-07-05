package com.hit.ispace;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<SpaceShipShop> spaceShip;
    private Context mContext;
    Dialog buyDialog,selectDialog;
    TextView name_space , price_space ;
    Button btn_yes , btn_no ;

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
        Glide.with(mContext)
                .asBitmap()
                .load(R.drawable.spaceship_6)
                .into(holder.imageSpaceShip);

        if(space.getLocked() == 0){
            holder.price.setText(R.string.you_already);
            holder.coin_or_done.setImageResource(R.drawable.icon_check);

            selectDialog = new Dialog(mContext);
            selectDialog.setContentView(R.layout.dialog_select_item);
            selectDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            holder.imageSpaceShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    name_space = (TextView) selectDialog.findViewById(R.id.name_space_ship);
                    name_space.setText(space.getName_ship());
                    btn_yes = (Button) selectDialog.findViewById(R.id.btn_yes_buy);
                    noSelect(view);
                    //Toast.makeText(mContext, space.getName_ship(), Toast.LENGTH_SHORT).show();
                    selectDialog.show();
                }
            });
        }
        else {
            holder.price.setText(String.format("%d" ,space.getPrice()));
            buyDialog = new Dialog(mContext);
            buyDialog.setContentView(R.layout.dialog_shop);
            buyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            holder.imageSpaceShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name_space = (TextView) buyDialog.findViewById(R.id.name_space_ship);
                    price_space = (TextView) buyDialog.findViewById(R.id.price_of_space_ship);
                    name_space.setText(space.getName_ship());
                    price_space.setText(String.valueOf(space.getPrice()) + "$");
                    //Toast.makeText(mContext, space.getName_ship(), Toast.LENGTH_SHORT).show();
                    buyDialog.show();
                }
            });
        }

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
        private ImageView coin_or_done;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSpaceShip = itemView.findViewById(R.id.image_space_ship);
            price = itemView.findViewById(R.id.price_space_ship);
            coin_or_done = itemView.findViewById(R.id.icon_animation_coin);
        }
    }

    public void noSelect(View view) {
        
    }
}