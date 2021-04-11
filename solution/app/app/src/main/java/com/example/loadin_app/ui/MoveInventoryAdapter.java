package com.example.loadin_app.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loadin_app.ItemViewActivity;
import com.example.loadin_app.MoveInventoryActivity;
import com.example.loadin_app.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import odu.edu.loadin.common.Inventory;

import static com.example.loadin_app.MoveInventoryActivity.sp;

public class MoveInventoryAdapter extends ArrayAdapter<Inventory> {

    ArrayList<Inventory> inventory;

    Context context;

    int resource;

    public MoveInventoryAdapter(Context context, int resource, ArrayList<Inventory> inventory){
        super(context, resource, inventory);
        this.context = context;
        this.resource = resource;
        this.inventory = inventory;
    }

    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);


        TextView item_description_value = view.findViewById(R.id.item_name_value);
        TextView item_room_value = view.findViewById(R.id.item_room_value);
        TextView item_status_value = view.findViewById(R.id.item_status_value);
        TextView item_boxID = view.findViewById(R.id.item_boxID_value);
        TextView item_contents_value = view.findViewById(R.id.item_contents_value);

        Inventory newInventory = inventory.get(position);
        Integer boxID = newInventory.getBoxID();

        item_description_value.setText(newInventory.getDescription());
        item_room_value.setText(newInventory.getRoom());
        item_status_value.setText(newInventory.getStatus());
        item_boxID.setText(boxID.toString());
        item_contents_value.setText(newInventory.getItemList());

        return view;
    }
}
