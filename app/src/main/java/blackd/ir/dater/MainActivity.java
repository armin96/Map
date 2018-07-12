package blackd.ir.dater;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import blackd.ir.dater.Adapter.MyContactAdapter;
import blackd.ir.dater.Model.Contact;
import blackd.ir.dater.Model.Keys;
import blackd.ir.dater.WebService.JSONParser;

public class MainActivity  extends AppCompatActivity{
    /**
     * Views
     */
    private ListView listView;
    private View parentView;

    private ArrayList<Contact> contactList;
    private ArrayAdapter<String> adapter;


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

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
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

                new GetDataTask().execute();
                /**
                 * Checking Internet Connection
                 */
    /*            if (InternetConnection.checkConnection(getApplicationContext())) {
                    final ProgressDialog dialog;
                    *//**
                     * Progress Dialog for User Interaction
                     *//*
                    dialog = new ProgressDialog(MainActivity.this);
                    dialog.setTitle(getString(R.string.string_getting_json_title));
                    dialog.setMessage(getString(R.string.string_getting_json_message));
                    dialog.show();

                    //Creating an object of our api interface
                    ApiService api = RetroClient.getApiService();

                    *//**
                     * Calling JSON
                     *//*
                    Call<ContactList> call = api.getMyJSON();

                    *//**
                     * Enqueue Callback will be call when get response...
                     *//*
                    call.enqueue(new Callback<ContactList>() {
                        @Override
                        public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                            //Dismiss Dialog
                            dialog.dismiss();

                            if(response.isSuccessful()) {
                                *//**
                                 * Got Successfully
                                 *//*
                                contactList = response.body().getContacts();

                                *//**
                                 * Binding that List to Adapter
                                 *//*
                                adapter = new MyContactAdapter(MainActivity.this, contactList);
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

                } else {
                    Snackbar.make(parentView, R.string.string_internet_connection_not_available, Snackbar.LENGTH_LONG).show();
                }*/
            }
        });
    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("I am getting your JSON");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    /**
                     * Check Length...
                     */
                    if (jsonObject.length() > 0) {

                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);

                        /**
                         * Check Length of Array...
                         */
                        int lenArray = array.length();
                        if (lenArray > 0) {
                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                String name = innerObject.getString(Keys.KEY_NAME);

                                /**
                                 * Getting Object from Object "phone"
                                 */
                                JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                list.add(name + " - " + phone);
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if (list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
