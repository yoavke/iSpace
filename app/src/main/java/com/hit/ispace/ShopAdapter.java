package com.hit.ispace;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.sql.Types.NULL;


public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {

    private List<SpaceShipShop> spaceShip;
    private Context mContext;
    Dialog buyDialog,selectDialog;
    TextView name_space , price_space ;
    Button btn_yes , btn_no , shop_or_select ;
    DatabaseHelper mDatabaseHelper;


    public ShopAdapter(Context context , List<SpaceShipShop> spaceShip) {
        this.spaceShip = spaceShip;
        mContext = context;
        mDatabaseHelper = new DatabaseHelper(context);

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
    public void onBindViewHolder(@NonNull final ShopAdapter.ShopViewHolder holder, int i) {
        final SpaceShipShop space = spaceShip.get(i);
        Glide.with(mContext)
                .asBitmap()
                .load(R.drawable.spaceship_3)
                .into(holder.imageSpaceShip);

        if(space.getLocked() == 0) {
            holder.price.setText(R.string.you_already);
            holder.coin_or_done.setImageResource(R.drawable.icon_check);
            holder.buy_or_select.setText(R.string.use_this_space);
            //holder.buy_or_select.setOnClickListener(new View.OnClickListener() {
                //@Override
                //need add metoda for say if he use this space so dont need change this space else do change and say this change successful
                /*public void onClick(final View view) {
                    if () {
                        FancyToast.makeText(mContext, mContext.getString(R.string.cant_buy), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                    }
                    else {

                    }
                }

            });*/
        }
        else {
            holder.price.setText(String.format("%d" ,space.getPrice()));
            holder.coin_or_done.setImageResource(R.drawable.icon_coin);
            holder.buy_or_select.setText(R.string.buy_now);
            holder.buy_or_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(final View view) {
                    int number_of_coins;
                    number_of_coins = mDatabaseHelper.getTotalCoins();
                    if(number_of_coins < space.getPrice())
                    {
                        FancyToast.makeText(mContext , mContext.getString(R.string.cant_buy),FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
                    }
                    else {
                        BuySpaceShip(space.getId());
                    }
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
        private Button buy_or_select;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            imageSpaceShip = itemView.findViewById(R.id.image_space_ship);
            price = itemView.findViewById(R.id.price_space_ship);
            coin_or_done = itemView.findViewById(R.id.icon_animation_coin);
            buy_or_select = itemView.findViewById(R.id.btn_buy_or_select);
        }
    }

    public void noSelectAndBuy(View view) {
        view.setEnabled(false);
    }

    public void BuySpaceShip(int id) {
        boolean updateData = mDatabaseHelper.buySpaceShip(id);

        if (updateData) {
            FancyToast.makeText(mContext , mContext.getString(R.string.success_buying),FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
        }
      else {
            FancyToast.makeText(mContext , mContext.getString(R.string.no_success_buying),FancyToast.LENGTH_LONG, FancyToast.ERROR,true).show();
      }
    }

}