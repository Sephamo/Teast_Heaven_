package com.example.thegardenofeatn;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {

    private List<ReservationModel> reservations;
    private Context context;
    private OnReservationChangedListener listener;

    public interface OnReservationChangedListener {
        void onReservationChanged(int position, ReservationModel reservation);
    }

    public ReservationAdapter(List<ReservationModel> reservations, Context context, OnReservationChangedListener listener) {
        this.reservations = new ArrayList<>(reservations); // Create new list to avoid reference issues
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ReservationModel reservation = reservations.get(position);
        holder.bind(reservation);

        holder.btnEdit.setOnClickListener(v -> {
            showEditDialog(holder.getAdapterPosition(), reservation);
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void addReservation(ReservationModel reservation) {
        reservations.add(reservation);
        notifyItemInserted(reservations.size() - 1);
    }

    public void updateReservation(int position, ReservationModel reservation) {
        if (position >= 0 && position < reservations.size()) {
            reservations.set(position, reservation);
            notifyItemChanged(position);
        }
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime, tvGuests, tvTable, tvStatus;
        Button btnEdit;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvGuests = itemView.findViewById(R.id.tv_guests);
            tvTable = itemView.findViewById(R.id.tv_table);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }

        public void bind(ReservationModel reservation) {
            tvName.setText(reservation.getCustomerName());
            tvTime.setText(reservation.getReservationTime());
            tvGuests.setText(reservation.getGuestNo());
            tvTable.setText(reservation.getTableNo());
            tvStatus.setText(reservation.getStatus());
            updateStatusBackground(reservation.getStatus());
        }

        private void updateStatusBackground(String status) {
            int backgroundResId;
            switch (status.toLowerCase()) {
                case "confirmed":
                    backgroundResId = R.drawable.bg_status_confirmed;
                    break;
                case "pending":
                    backgroundResId = R.drawable.bg_status_pending;
                    break;
                case "cancelled":
                    backgroundResId = R.drawable.bg_status_cancelled;
                    break;
                default:
                    backgroundResId = R.drawable.bg_status_default;
            }
            tvStatus.setBackgroundResource(backgroundResId);
        }
    }

    private void showEditDialog(int position, ReservationModel reservation) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_reservations);
        dialog.setCancelable(false);

        // Initialize views
        EditText etName = dialog.findViewById(R.id.etName);
        Spinner spinnerTime = dialog.findViewById(R.id.spinnerTime);
        Spinner spinnerGuestNo = dialog.findViewById(R.id.spinnerGuestNo);
        Spinner spinnerTableNo = dialog.findViewById(R.id.spinnerTableNo);
        Spinner spinnerStatus = dialog.findViewById(R.id.spinnerStatus);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        ImageButton btnClose = dialog.findViewById(R.id.btnClose);

        // Set current values
        etName.setText(reservation.getCustomerName());
        setSpinnerSelection(spinnerTime, reservation.getReservationTime());
        setSpinnerSelection(spinnerGuestNo, reservation.getGuestNo());
        setSpinnerSelection(spinnerTableNo, reservation.getTableNo());
        setSpinnerSelection(spinnerStatus, reservation.getStatus());

        // Save button click
        btnSave.setOnClickListener(v -> {
            try {
                ReservationModel updatedReservation = new ReservationModel(
                        etName.getText().toString(),
                        spinnerTime.getSelectedItem().toString(),
                        spinnerGuestNo.getSelectedItem().toString(),
                        spinnerTableNo.getSelectedItem().toString(),
                        spinnerStatus.getSelectedItem().toString()
                );

                if (listener != null) {
                    listener.onReservationChanged(position, updatedReservation);
                }
                dialog.dismiss();
            } catch (Exception e) {
                Log.e("ReservationAdapter", "Error saving reservation", e);
            }
        });

        // Cancel/Close buttons
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        if (spinner != null && value != null) {
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }
}