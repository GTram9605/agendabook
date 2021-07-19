package za.ac.nplinnovations.agendabook.features.ui.markcompleted;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.List;

import za.ac.nplinnovations.agendabook.database.doa.AppDatabase;
import za.ac.nplinnovations.agendabook.database.entities.Task;

public class MarkCompletedViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Task>> mTasks;

    public MarkCompletedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("No incomplete tasks yet, add new tasks in order to mark them as complete.");

        mTasks = new MutableLiveData<List<Task>>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Task>> getTasks(Context c) {
        AppDatabase db = Room.databaseBuilder(c,
                AppDatabase.class, "agendabook_db").allowMainThreadQueries().build();

        mTasks.setValue(db.taskDao().loadUncompletedTasks());

        return mTasks;
    }
}