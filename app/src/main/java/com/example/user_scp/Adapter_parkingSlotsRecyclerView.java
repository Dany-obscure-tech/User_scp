package com.example.user_scp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_parkingSlotsRecyclerView extends RecyclerView.Adapter<Adapter_parkingSlotsRecyclerView.ViewHolder> {
    Context context;
    List<String> parkingSlotsName_List;
    List<String> parkingSlots_List;

    public Adapter_parkingSlotsRecyclerView(Context context, List<String> parkingSlotsName_List, List<String> parkingSlots_List) {
        this.context = context;
        this.parkingSlotsName_List = parkingSlotsName_List;
        this.parkingSlots_List = parkingSlots_List;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_slotlist_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.parkingName_textView.setText("Slot "+parkingSlotsName_List.get(position));
        if (parkingSlots_List.get(position).equals("0")){
            holder.image_imageView.setImageResource(R.drawable.blank_car);
        }

    }


    @Override
    public int getItemCount() {
        return parkingSlotsName_List.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView parkingName_textView;
        public ImageView image_imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.parkingName_textView = (TextView) itemView.findViewById(R.id.parkingName_textView);
            this.image_imageView = (ImageView) itemView.findViewById(R.id.image_imageView);
        }
    }
}