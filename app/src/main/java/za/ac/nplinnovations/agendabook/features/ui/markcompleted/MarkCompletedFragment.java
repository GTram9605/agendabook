package za.ac.nplinnovations.agendabook.features.ui.markcompleted;

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
import za.ac.nplinnovations.agendabook.adapters.IncompleteTaskAdapter;

public class MarkCompletedFragment extends Fragment {

    private MarkCompletedViewModel markCompletedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        markCompletedViewModel =
                ViewModelProviders.of(this).get(MarkCompletedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mark_completed, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        markCompletedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final RecyclerView recycler = root.findViewById(R.id.recyclerTasks);
        recycler.setAdapter(new IncompleteTaskAdapter(markCompletedViewModel.getTasks(getContext()), getLayoutInflater()));

        if (markCompletedViewModel.getTasks(getContext()).getValue().size() > 0) {
            textView.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        } else {
            recycler.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        return root;
    }
}