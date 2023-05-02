package gr.uniwa.volleyex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MyAdapter";
    private ArrayList<Customer> customers;
    private MainActivity activity;

    public MyAdapter(ArrayList<Customer> customers, MainActivity activity) {
        this.customers = customers;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer, parent, false);
        MyViewHolder holder = new MyViewHolder(view, activity);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder item = (MyViewHolder) holder;
        Customer current = customers.get(position);
        item.getCustomerText().setText(current.toString());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void removeItem(int position) {
        customers.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Customer item, int position) {
        customers.add(position, item);
        notifyItemInserted(position);
    }

}
