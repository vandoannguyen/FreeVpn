package com.example.init_app_vpn_native.ui.switchRegion.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.init_app_vpn_native.R;
import com.example.init_app_vpn_native.data.api.model.Country;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    List<Country> countries;
    @BindView(R.id.imgFlagItem)
    ImageView imgFlagItem;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public ItemAdapter(List<Country> countries) {
        this.countries = countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) onItemClick.onClick(countries.get(position));
            }
        });
        holder.txtCoinItemServer.setText(countries.get(position).getPrice());
        holder.txtName.setText(countries.get(position).getName());
//        if (countries.get(position).getFlag().contains(".svg")) {
//            Glide.with(holder.itemView.getContext()).as(SVG.class).load(countries.get(position).getFlag()).into(holder.imgFlagItem);
//        } else
        Glide.with(holder.itemView.getContext()).load("https://www.countryflags.io/" + countries.get(position).getCode() + "/flat/64.png").into(holder.imgFlagItem);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public interface OnItemClick {
        void onClick(Country index);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtCoinItemServer)
        TextView txtCoinItemServer;
        @BindView(R.id.imgFlagItem)
        ImageView imgFlagItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
