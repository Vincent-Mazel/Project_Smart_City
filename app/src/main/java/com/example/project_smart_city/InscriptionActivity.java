package com.example.project_smart_city;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_smart_city.ui.ChoicesFragment.ChoiceFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{

    private DatePickerDialog pickerDialog;
    private TextView textViewBirthday;
    private static ScrollView scrollView;
    private Boolean isChoiceOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inscription);

        scrollView = findViewById(R.id.inscription_scrollView);
        textViewBirthday = findViewById(R.id.inscription_birthdayText);
        textViewBirthday.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog


            pickerDialog = new DatePickerDialog(this, R.style.DatePickerDialogTheme,
                    (view, year1, monthOfYear, dayOfMonth) -> textViewBirthday.setText(dayOfMonth + "/" + monthOfYear + "/" + year1), year, month, day);
            pickerDialog.show();
        });

        Spinner spinner = findViewById(R.id.spinner_sexe_inscription);
        String[] arraySpinner = new String[]{
                "Homme",
                "Femme",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.incription_buttonValidate);
        button.setText("Faîtes vos choix");
        button.setOnClickListener(view -> {
            try {
                if(addUser()){
                    if(!isChoiceOpen){
                        ChoiceFragment choiceFragment = new ChoiceFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction
                                .add(R.id.inscription_layout, choiceFragment)
                                .addToBackStack(null)
                                .commit();
                        isChoiceOpen = true;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
    }

    public static ScrollView getScrollView(){
        return scrollView;
    }

    @Override
    public void onClick(View view) {

    }

    public Boolean addUser() throws ParseException {
        TextView text_name = findViewById(R.id.inscription_prenom);
        TextView text_surname = findViewById(R.id.inscription_nom);
        TextView text_email = findViewById(R.id.inscription_adrmail);
        TextView text_password = findViewById(R.id.inscription_password);
        TextView text_passwordConfirm = findViewById(R.id.inscription_confirm_password);
        TextView text_pseudo = findViewById(R.id.inscription_pseudo);
        TextView text_size = findViewById(R.id.inscription_size);
        TextView text_weight = findViewById(R.id.inscription_weight);
        TextView text_birthday = findViewById(R.id.inscription_birthdayText);


        Spinner spinner_sex = findViewById(R.id.spinner_sexe_inscription);

        String name = text_name.getText().toString();
        String surname = text_surname.getText().toString();
        String email = text_email.getText().toString();
        String password = text_password.getText().toString();
        String pseudo = text_pseudo.getText().toString();
        String passwordConfirm = text_passwordConfirm.getText().toString();
        String birthday = text_birthday.getText().toString();
        String sex = spinner_sex.getSelectedItem().toString();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = simpleDateFormat.parse(birthday);


        Date min = new Date("January 1, 2007");


        System.out.println("\n"
                +min + "\n" + date
);

        // Test if fields are'nt empty
        if(name.equals("") || email.equals("") || surname.equals("") || password.equals("") || pseudo.equals("") || text_weight.getText().toString().equals("") ||text_size.getText().toString().equals("") || birthday.equals("Birthday")){
            Toast.makeText(this, "Please fill every fields.",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {

            if(password.equals(passwordConfirm)) {
                // Check is the email is valid
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(this, "You email is not valid.", Toast.LENGTH_SHORT).show();
                    text_email.setError("not valid");
                    return false;
                }
                else {
                    // Check if you're 13 or older
                    if(date.before(min)){
                        DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);
                        int size = Integer.parseInt(text_size.getText().toString());
                        int weight = Integer.parseInt(text_weight.getText().toString());

                        if(dbHandler.findHandler(email) == null){
                            User user = new User (pseudo,name,surname,email,sex,birthday,password,size,weight);
                            dbHandler.addHandler(user);
                            return true;
                        }
                        else {
                            Toast.makeText(this, "This email is already used sorry.", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    else {
                        Toast.makeText(this, "You are too young too access Smart City sorry.", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }

            }
            else {
                Toast.makeText(this, "Passwords are not the same, correct them please.", Toast.LENGTH_SHORT).show();
                text_password.setError("retry");
                text_password.setText("");
                text_passwordConfirm.setText("");
                return false;
            }
        }
    }
}
