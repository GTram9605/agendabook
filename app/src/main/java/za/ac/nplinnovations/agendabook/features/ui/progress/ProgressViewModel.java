package za.ac.nplinnovations.agendabook.features.ui.progress;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.List;

import za.ac.nplinnovations.agendabook.database.doa.AppDatabase;
import za.ac.nplinnovations.agendabook.database.entities.Task;

public class ProgressViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Task>> mTasks;

    public ProgressViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No progress found yet. Please add new tasks to view progress.");

        mTasks = new MutableLiveData<List<Task>>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Task>> getTasks(Context c) {
        AppDatabase db = Room.databaseBuilder(c,
                AppDatabase.class, "agendabook_db").allowMainThreadQueries().build();

        mTasks.setValue(db.taskDao().getAll());

        return mTasks;
    }
}