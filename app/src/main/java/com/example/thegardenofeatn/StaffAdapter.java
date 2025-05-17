package com.example.thegardenofeatn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.StaffViewHolder> {

    private Context context;
    private List<StaffModel> staffList;

    public StaffAdapter(Context context, List<StaffModel> staffList) {
        this.context = context;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_staff, parent, false);
        return new StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        StaffModel staff = staffList.get(position);
        holder.nameTextView.setText(staff.getName());
        holder.positionTextView.setText(staff.getPosition());
        holder.clockInTextView.setText("In: " + staff.getClockIn());
        holder.clockOutTextView.setText("Out: " + staff.getClockOut());
        holder.hoursWorkedTextView.setText("Hours: " + staff.getHoursWorked());
        holder.statusTextView.setText("Status: " + staff.getStatus()); // ✅ KEEP THIS

// ✅ Color logic based on status
        switch (staff.getStatus().toLowerCase()) {
            case "active":
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));
                break;
            case "on leave":
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.status_absent));
                break;
            case "inactive":
            default:
                holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.status_late));
                break;
        }



    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public static class StaffViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, positionTextView, clockInTextView, clockOutTextView, hoursWorkedTextView, statusTextView;

        public StaffViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.tvStaffName);
            positionTextView = itemView.findViewById(R.id.tvPosition);
            clockInTextView = itemView.findViewById(R.id.tvClockIn);
            clockOutTextView = itemView.findViewById(R.id.tvClockOut);
            hoursWorkedTextView = itemView.findViewById(R.id.tvHoursWorked);
            statusTextView = itemView.findViewById(R.id.tvStatus);
        }
    }
}