package com.example.csci3130_g2_quick_cash.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csci3130_g2_quick_cash.R;
import com.example.csci3130_g2_quick_cash.RecyclerView.EmployeeRecyclerViewInterface;
import com.example.csci3130_g2_quick_cash.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class SuggestedEmployeeAdapter extends RecyclerView.Adapter<SuggestedEmployeeAdapter.MyViewHolder> {
    private EmployeeRecyclerViewInterface employeeRecyclerViewInterface;

    private final Context context;
    private final List<Employee> listOfEmployees;

    public SuggestedEmployeeAdapter(Context context) {
        this.context = context;
        this.listOfEmployees = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_suggested_employee_list, parent, false);
        return new MyViewHolder(view, employeeRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Employee emp = listOfEmployees.get(position);
        holder.fullName.setText(emp.getFullName());
//        holder.rating.setText("5");
        holder.email.setText(emp.getEmail());
        holder.employeeId = emp.getEmail();
    }

    @Override
    public int getItemCount() {
        return listOfEmployees.size();
    }

    public void addToList(List<Employee> list) {
        this.listOfEmployees.clear();
        this.listOfEmployees.addAll(list);
        this.notifyDataSetChanged();
    }

    public void addItemToList(Employee item) {
        this.listOfEmployees.add(item);
        this.notifyItemChanged(getItemCount());
    }

    public void setEmployeeRecyclerViewInterface(EmployeeRecyclerViewInterface employeeRecyclerViewInterface) {
        this.employeeRecyclerViewInterface = employeeRecyclerViewInterface;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;
        TextView rating;
        TextView email;
        String employeeId;

        public MyViewHolder(@NonNull View itemView, EmployeeRecyclerViewInterface employeeRecyclerViewInterface) {
            super(itemView);
            fullName = itemView.findViewById(R.id.suggestedEmployeeFullName);
            email = itemView.findViewById(R.id.suggestedEmployeeEmail);
            employeeId = "";
//            itemView.setOnClickListener(view -> {
//                if (employeeRecyclerViewInterface != null) {
//                    int pos = getAdapterPosition();
//                    if (pos != RecyclerView.NO_POSITION) {
//                        employeeRecyclerViewInterface.onEmployeeClick(this.employeeId);
//                    }
//                }
//            });
        }
    }
}
