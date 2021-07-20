package za.ac.nplinnovations.agendabook.database.doa;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import za.ac.nplinnovations.agendabook.database.entities.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDOA taskDao();

}
