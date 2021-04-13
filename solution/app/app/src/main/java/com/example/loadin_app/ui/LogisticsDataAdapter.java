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
import com.example.loadin_app.data.model.LogisticsResult;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import odu.edu.loadin.common.Inventory;
import odu.edu.loadin.common.MovingTruck;

import static com.example.loadin_app.MoveInventoryActivity.sp;

public class LogisticsDataAdapter extends ArrayAdapter<LogisticsResult> {

    ArrayList<LogisticsResult> listOfLogisticsResults;

    Context context;

    int resource;

    public LogisticsDataAdapter(Context context, int resource, ArrayList<LogisticsResult> listOfLogisticsResults){
        super(context, resource, listOfLogisticsResults);
        this.context = context;
        this.resource = resource;
        this.listOfLogisticsResults = listOfLogisticsResults;
    }
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(resource, null, false);


        // Sets all of our textviews so we can update the values.
        TextView truck_dimensions_value = view.findViewById(R.id.item_contents_value);
        TextView truck_cost_value = view.findViewById(R.id.truck_cost_header);
        TextView truck_mpg_value = view.findViewById(R.id.truck_mpg_header);
        TextView total_trips_value = view.findViewById(R.id.logistics_trips_header);
        TextView total_move_distance = view.findViewById(R.id.logistics_distance_header);

        // Gets the LogisticsResults object.
        LogisticsResult logisticsResult = listOfLogisticsResults.get(position);

        // Gets the MovingTruck object contained within the LogisticsResult object.
        MovingTruck newMovingTruck = logisticsResult.getMovingTruck();

        // Gets the dimensions of the truck in inches.
        Float truckLengthInInches = newMovingTruck.getLengthInInches();
        Float truckHeightInInches = newMovingTruck.getHeightInInches();
        Float truckWidthInInches = newMovingTruck.getWidthInInches();

        // Converts the dimensions from inches to feet, and from Floats to Strings.
        String truckLengthInFeet = new DecimalFormat("0.00").format(truckLengthInInches/12);
        String truckWidthInFeet = new DecimalFormat("0.00").format(truckWidthInInches/12);
        String truckHeightInFeet = new DecimalFormat("0.00").format(truckHeightInInches/12);

        // Gets the truck company name, truck name, and concats them into one String.
        String truckCompanyName = newMovingTruck.getCompanyName();
        String truckTruckName = newMovingTruck.getTruckName();
        String truckCompanyNameConcat = truckCompanyName + " " + truckTruckName;


        // Gets the truck costPerMile, numberOfLoads, and calculates the numberOfTrips.
        Float truckCostPerMile = newMovingTruck.getCostPerMile();
        Integer numberOfLoads = logisticsResult.getLoadPlan().GetLoads().size();
        Integer numberOfTrips = numberOfLoads * 2;

        // Rough check to estimate number of days needed. Used 3 to avoid extremely complex time calculation.
        Integer numOfDays = 1;
        if(numberOfTrips != null && numberOfTrips > 3)
        {
            numOfDays = (int) Math.ceil((double)numberOfTrips / 3);
        }


        // Calculates the total distance, total cost of the distance, and finally total cost of move.
        // Formula for total cost of move = ((base rental cost * number of days) + (total miles * cost per mile)).
        Float totalDistance = logisticsResult.calculateTotalDistance(logisticsResult.getNumOfMiles(), numberOfTrips);
        Float totalCostOfDistance = logisticsResult.calculateTotalCostOfDistance(totalDistance, truckCostPerMile);
        Float totalCostOfMove = logisticsResult.calculateTotalCost(newMovingTruck.getBaseRentalCost() * numOfDays, totalCostOfDistance);


        // Converts all of our calculations into Strings.
        String totalDistanceInString = new DecimalFormat("0.00").format(totalDistance) + " miles";
        String truckCostOfMoveInString = "$" + new DecimalFormat("0").format(totalCostOfMove);
        String combinedDimensionsInString = truckLengthInFeet + "'" + " x " + truckWidthInFeet + "'" + " x " + truckHeightInFeet + "'";

        // Passes all of our calculated values into the textViews.
        truck_dimensions_value.setText(combinedDimensionsInString);
        truck_mpg_value.setText(truckCompanyNameConcat);
        truck_cost_value.setText(truckCostOfMoveInString);
        total_trips_value.setText(numberOfTrips.toString());
        total_move_distance.setText(totalDistanceInString);


        return view;
    }
}
