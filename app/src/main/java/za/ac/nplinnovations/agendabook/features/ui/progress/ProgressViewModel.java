package za.ac.nplinnovations.agendabook.features.ui.progress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgressViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProgressViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No progress found yet. Please add new tasks to view progress.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}