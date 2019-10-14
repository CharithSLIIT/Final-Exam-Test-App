package com.e.finalexamtestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.finalexamtestapp.Database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText editText_username, editText_password;

    DatabaseHelper databaseHelper;

    int stars = 0;
    String gender = null;
    String user_type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        editText_username = findViewById(R.id.main_username);
        editText_password = findViewById(R.id.main_password);

        //Toolbar
        //Developers Link
        //https://developer.android.com/training/appbar/setting-up
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Boo");
        setSupportActionBar(myToolbar);


        //Spinner
        //Assigning values in the Strings.xml
        //Developers Link
        //https://developer.android.com/guide/topics/ui/controls/spinner
        Spinner spinner = (Spinner) findViewById(R.id.main_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    //Toolbar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //Checkboxes
    //Developers Link
    //https://developer.android.com/guide/topics/ui/controls/checkbox?hl=en
    public void assignStars(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.main_1_star:
            {
                if (checked)
                    stars = 1;
                break;
            }

            case R.id.main_2_stars:
            {
                if (checked)
                    stars = 2;
                break;
            }


            case R.id.main_3_stars:
            {
                if (checked)
                    stars = 3;
                break;
            }

             default:
             {
                 stars = 0;
                 break;
             }
        }
    }

    //Radio Buttons
    //Use Inside a radio group, if you want to align radio buttons horizontally change radio group's orientation into 'horizontal'
    //Default = 'vertical'
    //Developers Link
    //https://developer.android.com/guide/topics/ui/controls/radiobutton?hl=en
    public void assignGender(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.main_radio_male:
            {
                if (checked)
                    gender = "Male";
                    break;
            }

            case R.id.main_radio_female:
            {
                if (checked)
                    gender = "Female";
                    break;
            }

            default:
            {
                gender = null;
                break;
            }
        }
    }


    //Spinner tapping action
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        user_type = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addData(View view) {
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();

        //Check whether the values are empty
        if(TextUtils.isEmpty(username))
        {
            editText_username.setError("Please fill");
            return;
        }

        else if(TextUtils.isEmpty(password))
        {
            editText_password.setError("Please fill");
            return;
        }

        else if(TextUtils.isEmpty(gender))
        {
            Toast.makeText(this, "Please select Gender", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(TextUtils.isEmpty(user_type))
        {
            Toast.makeText(this, "Please select User Type", Toast.LENGTH_SHORT).show();
            return;
        }

        else if(stars == 0)
        {
            Toast.makeText(this, "Please select Star Level", Toast.LENGTH_SHORT).show();
            return;
        }

        //Add data
        boolean result = databaseHelper.addData(username, password, gender, user_type, stars);

        //Display Alert Box
        //Developers Link
        //https://developer.android.com/guide/topics/ui/dialogs
        if (result)
        {
            // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Data was added successfully!")
                    .setTitle("Success");

            //To set a button
            //Max 3 buttons : setNegativeButton(), setPositiveButton(), setNeutralButton()
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setCancelable(true);

            // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            builder.show();
        }

        else
        {
            // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Can't add data!")
                    .setTitle("Error");

            //To set a button
            //Max 3 buttons : setNegativeButton(), setPositiveButton(), setNeutralButton()
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setCancelable(true);

            // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            builder.show();
        }
    }

    //Add the material dependency
    //Developers Link
    //https://developer.android.com/training/snackbar/showing
    public void showSnackbar(View view) {
        Snackbar.make(findViewById(R.id.main_activity), "Snackbar Message", Snackbar.LENGTH_SHORT).show();
    }
}
