package com.example.thegardenofeatn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoViewHolder> {

    private List<Promo> promoList;
    private OnPromoClickListener onPromoClickListener;

    public interface OnPromoClickListener {
        void onPromoClick(Promo promo);
    }

    public PromoAdapter(List<Promo> promoList, OnPromoClickListener onPromoClickListener) {
        this.promoList = promoList;
        this.onPromoClickListener = onPromoClickListener;
    }

    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_item, parent, false);
        return new PromoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        Promo promo = promoList.get(position);
        holder.promoImage.setImageResource(promo.getImage());

        holder.itemView.setOnClickListener(v -> {
            if (onPromoClickListener != null) {
                onPromoClickListener.onPromoClick(promo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    public static class PromoViewHolder extends RecyclerView.ViewHolder {
        ImageView promoImage;
        TextView promoTitle;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            promoImage = itemView.findViewById(R.id.promo_image);
        }
    }
}

