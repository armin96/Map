package blackd.ir.dater;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import blackd.ir.dater.Adapter.MyContactAdapter;
import blackd.ir.dater.Model.Contact;
import blackd.ir.dater.Model.ContactList;
import blackd.ir.dater.WebService.ApiService;
import blackd.ir.dater.WebService.RetroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetroActivity  extends AppCompatActivity{
    /**
     * Views
     */
    private ListView listView;
    private View parentView;

    private ArrayList<Contact> contactList;
    private MyContactAdapter adapter;


    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Array List for Binding Data from JSON to this List
         */
        contactList = new ArrayList<>();

        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        parentView = findViewById(R.id.parentLayout);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(parentView, contactList.get(position).getName() + " => " + contactList.get(position).getPhone().getHome(), Snackbar.LENGTH_LONG).show();
            }
        });

        /**
         * Just to know onClick and Printing Hello Toast in Center.
         */
        Toast toast = Toast.makeText(getApplicationContext(), R.string.string_click_to_load, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View view) {


                /**
                 * Checking Internet Connection
                 */
              // if (InternetConnection.checkConnection(getApplicationContext())) {
                    final ProgressDialog dialog;
                  //  *//**
              //   * Progress Dialog for User Interaction
                // *//*
                    dialog = new ProgressDialog(RetroActivity.this);
                    dialog.setTitle(getString(R.string.string_getting_json_title));
                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();

                    //Creating an object of our api interface
                    ApiService api = RetroClient.getApiService();

                  //  *//**
              //   * Calling JSON
                // *//*
                    Call<ContactList> call = api.getMyJSON();

                   // *//**
               //  * Enqueue Callback will be call when get response...
                 //*//*
                    call.enqueue(new Callback<ContactList>() {
                        @Override
                        public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                            //Dismiss Dialog
                            dialog.dismiss();

                            if(response.isSuccessful()) {
                         //       *//**
                // * Got Successfully
                 //*//*
                                contactList = response.body().getContacts();

                              //  *//**
               //  * Binding that List to Adapter
                 //*//*
                                adapter = new MyContactAdapter(RetroActivity.this, contactList);
                                listView.setAdapter(adapter);

                            } else {
                                Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ContactList> call, Throwable t) {
                            dialog.dismiss();
                        }
                    });

               // } else {
                 //   Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
                //}
            }
        });
    }


}
