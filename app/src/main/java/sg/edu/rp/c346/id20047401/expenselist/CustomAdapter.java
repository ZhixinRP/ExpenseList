package sg.edu.rp.c346.id20047401.expenselist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Expense> versionList;


    public CustomAdapter(Context context, int resource, ArrayList<Expense> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        versionList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvCost = rowView.findViewById(R.id.textViewCost);
        ImageView ivDollar = rowView.findViewById(R.id.imageView);

        // Obtain the Android Version information based on the position
        Expense currentVersion = versionList.get(position);

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentVersion.getName());
        tvCost.setText(String.valueOf(currentVersion.getCost()));

        if (currentVersion.getCost() <= 50){
            ivDollar.setImageResource(android.R.color.holo_green_light);
        } else if (currentVersion.getCost() <= 200){
            ivDollar.setImageResource(android.R.color.holo_orange_light);
        } else {
            ivDollar.setImageResource(android.R.color.holo_red_light);
        }

        return rowView;
    }

}
