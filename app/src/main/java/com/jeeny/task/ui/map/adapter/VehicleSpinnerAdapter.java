package com.jeeny.task.ui.map.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class VehicleSpinnerAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resourceID;

    public VehicleSpinnerAdapter(@NonNull Context context, int resourceID, @NonNull List<String> categories) {
        super(context, resourceID, categories);
        this.context = context;
        this.resourceID = resourceID;
    }


    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        return rowView(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return rowDropDownView(convertView, position);
    }

    private View rowView(View convertView, int position) {
        viewHolder holder;
        View rowView = convertView;
/*         if (rowView == null) {
            holder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.category_spinner_item, null, false);
            holder.txtTitle = (TextView) rowView.findViewById(R.id.itemCat_title);
            rowView.setTag(holder);
        } else
            holder = (viewHolder) rowView.getTag();
        holder.bindViews(categories.get(position));*/
        return rowView;
    }

    private View rowDropDownView(View convertView, int position) {
        viewHolderDropDown holder;
        View rowView = convertView;
     /*   if (rowView == null) {
            holder = new viewHolderDropDown();
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.category_spinner_drop_down_item, null, false);
            holder.txtTitle = (TextView) rowView.findViewById(R.id.itemCat_dropDown);
            rowView.setTag(holder);
        } else
            holder = (viewHolderDropDown) rowView.getTag();
        holder.bindViews(categories.get(position));*/
        return rowView;
    }

    private class viewHolder {
        TextView txtTitle;

        public void bindViews(String category) {
            // txtTitle.setText(category.getName());
        }
    }

    private class viewHolderDropDown {
        TextView txtTitle;

        public void bindViews(String category) {
            //   txtTitle.setText(category.getName());
        }
    }
}