package za.ac.nplinnovations.agendabook.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.nplinnovations.agendabook.R;
import za.ac.nplinnovations.agendabook.database.entities.Task;

public class IncompleteTaskAdapter extends RecyclerView.Adapter<IncompleteTaskAdapter.ViewHolder> {
    private LiveData<List<Task>> mData;
    private LayoutInflater mInflater;
//    private ItemClickListener  mClickListener;

    public IncompleteTaskAdapter(LiveData<List<Task>> mData,
                                 LayoutInflater mInflater) {
        this.mData = mData;
        this.mInflater = mInflater;
    }

    @NonNull
    @Override
    public IncompleteTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.incomplete_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncompleteTaskAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(mData.getValue().get(position).title);
        holder.tvDescription.setText(mData.getValue().get(position).description);
        holder.tvDueDate.setText(mData.getValue().get(position).due_date);
    }

    @Override
    public int getItemCount() {
        return mData.getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvDueDate;
        TextView tvDescription;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDueDate = itemView.findViewById(R.id.tvDate);
            tvDescription = itemView.findViewById(R.id.tvDescription);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
