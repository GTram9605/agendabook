package za.ac.nplinnovations.agendabook.features;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import za.ac.nplinnovations.agendabook.R;
import za.ac.nplinnovations.agendabook.database.doa.AppDatabase;
import za.ac.nplinnovations.agendabook.database.entities.Task;
import za.ac.nplinnovations.agendabook.notifcations.AlertReceiver2;

public class FeaturesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void onClickAddTask(final View view) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_new_task);

        Button btnAdd = dialog.findViewById(R.id.btnAdd),
                cancleButton = dialog.findViewById(R.id.btnCancel);
        final EditText etTitle = dialog.findViewById(R.id.etTitle),
                etDescription = dialog.findViewById(R.id.etDescription),
                etDate = dialog.findViewById(R.id.etDate);

        final ImageView ivDteCalender = dialog.findViewById(R.id.ivDteCalender);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/YYYY"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                etDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        ivDteCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (fieldsAreValid(etTitle, etDescription, etDate)) {
                    addNewTaskToCalender(new Task(etTitle.getText().toString(),
                            etDescription.getText().toString(),
                            etDate.getText().toString()));
                    dialog.dismiss();
                }
            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addNewTaskToCalender(Task task) {
        new AddTaskAsync(getBaseContext(), task).execute();
    }

    private boolean fieldsAreValid(EditText etTitle, EditText etDescription, EditText etDate) {
        if (etTitle.getText().toString() == null ||
                etTitle.getText().toString().length() < 3) {
            etTitle.setError("Input valid title, 3 characters min.");
        } else if (etDescription.getText().toString() == null ||
                etDescription.getText().toString().length() < 3) {
            etDescription.setError("Input valid description.");
        } else if (etDate.getText().toString() == null ||
                etDate.getText().toString().length() < 3
                || !etDate.getText().toString().contains("/")) {
            etDate.setError("Input valid date.");
        } else {
            return true;
        }

        return false;
    }

    private class AddTaskAsync extends AsyncTask<Void, Void, Task> {
        private WeakReference<Context> weakReference;
        private Task task;


        public AddTaskAsync(Context activity, Task task) {
            this.weakReference = new WeakReference<>(activity);
            this.task = task;
        }

        @Override
        protected Task doInBackground(Void... voids) {
            AppDatabase db = Room.databaseBuilder(weakReference.get(),
                    AppDatabase.class, "agendabook_db").build();

            db.taskDao().insertAll(task);

            return task;
        }

        @Override
        protected void onPostExecute(Task task) {
            Toast.makeText(weakReference.get(), "Task added successfully.", Toast.LENGTH_SHORT).show();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            long appointmentInMilliseconds = 0;
            try {
                appointmentInMilliseconds = sdf.parse(task.getDue_date() + " 15:04:30").getTime();
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(weakReference.get(), AlertReceiver2.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(weakReference.get(), 5, intent, 0);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, appointmentInMilliseconds, pendingIntent);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, appointmentInMilliseconds, AlarmManager.INTERVAL_DAY * 7, pendingIntent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
