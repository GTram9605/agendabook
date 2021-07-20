package za.ac.nplinnovations.agendabook.database.doa;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import za.ac.nplinnovations.agendabook.database.entities.Task;

@Dao
public interface TaskDOA {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE is_complete = 0 ")
    List<Task> loadUncompletedTasks();

    @Query("SELECT * FROM task WHERE title LIKE :searchtitle LIMIT 1")
    Task findByTitle(String searchtitle);

    @Insert
    void insertAll(Task... tasks);

    @Update
    void markAsCompleted(Task task);

    @Update
    void editTask(Task task);

    @Delete
    void delete(Task task);

}
