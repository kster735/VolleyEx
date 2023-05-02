package gr.uniwa.volleyex;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private MainActivity activity;
    private TextView customerText;

    public MyViewHolder(@NonNull View itemView, MainActivity activity) {
        super(itemView);
        this.activity = activity;
        customerText = (TextView) itemView.findViewById(R.id.customerText);
    }

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public TextView getCustomerText() {
        return customerText;
    }

    public void setCustomerText(TextView customerText) {
        this.customerText = customerText;
    }
}
