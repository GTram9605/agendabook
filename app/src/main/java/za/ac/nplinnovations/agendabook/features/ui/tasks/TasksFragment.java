package za.ac.nplinnovations.agendabook.features.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.nplinnovations.agendabook.R;
import za.ac.nplinnovations.agendabook.adapters.TaskAdapter;

public class TasksFragment extends Fragment {

    private TasksViewModel tasksViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksViewModel =
                ViewModelProviders.of(this).get(TasksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        tasksViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final RecyclerView recycler = root.findViewById(R.id.recyclerTasks);
        recycler.setAdapter(new TaskAdapter(tasksViewModel.getTasks(getContext()), getLayoutInflater()));

        if (tasksViewModel.getTasks(getContext()).getValue().size() > 0) {
            textView.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        } else {
            recycler.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        return root;
    }
}