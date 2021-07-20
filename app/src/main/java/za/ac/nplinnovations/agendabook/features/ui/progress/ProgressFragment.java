package za.ac.nplinnovations.agendabook.features.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import za.ac.nplinnovations.agendabook.R;


public class ProgressFragment extends Fragment {

    private ProgressViewModel progressViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        progressViewModel =
                ViewModelProviders.of(this).get(ProgressViewModel.class);
        View root = inflater.inflate(R.layout.fragment_progress, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        progressViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final ProgressBar pbComplete = root.findViewById(R.id.pbComplete),
                pbIncomplete = root.findViewById(R.id.pbIncomplete),
                pbTotal = root.findViewById(R.id.pbTotal);

        final TableLayout tblProgress = root.findViewById(R.id.tblProgress);

        if (progressViewModel.getTasks(getContext()).getValue().size() > 0) {
            textView.setVisibility(View.GONE);
            tblProgress.setVisibility(View.VISIBLE);

            int countComplete = 0;
            int countIncomplete = 0;
            int total = 0;

            for (int i = 0; i < progressViewModel.getTasks(getContext()).getValue().size(); i++) {
                if (progressViewModel.getTasks(getContext()).getValue().get(i).isComplete)
                    countComplete++;
                else
                    countIncomplete++;

                total++;
            }

            pbTotal.setProgress(((total / total) * 100), true);
            pbComplete.setProgress(((countComplete / total) * 100), true);
            pbIncomplete.setProgress(((countIncomplete / total) * 100), true);

        } else {
            tblProgress.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        return root;
    }

}