package de.joachimsohn.collectivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActitvity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_VALUE = "EXTRA_VALUE";
    public static final String EXTRA_VALUE_CURRENCY = "EXTRA_VALUE_CURRENCY";
    public static final String EXTRA_CREATION_DATE = "EXTRA_VALUE";
    public static final String EXTRA_EAN = "EXTRA_EAN";
    public static final String EXTRA_ICON = "EXTRA_ICON";
    public static final String EXTRA_BUY_DATE = "EXTRA_BUY_DATE";

    private static final int CAMERA_PIC_REQUEST = 1337;


    private EditText itemTitle;
    private EditText itemPurchaseDate;
    private EditText itemValue;
    private Spinner itemCurrencySpinner;
    private Button eanScanner;
    private TextView eanText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        findGUIElements();
    }

    private void findGUIElements() {
        itemTitle = findViewById(R.id.item_edit_title_text);
        itemValue = findViewById(R.id.item_edit_value_text);
        itemPurchaseDate = findViewById(R.id.item_edit_purchase_date_text);
        findAndSetupCurrencySpinner();
        findAndSetupEANScanner();
    }

    private void findAndSetupEANScanner() {
        eanScanner = findViewById(R.id.item_edit_ean_scanner_button);
        eanText = findViewById(R.id.item_edit_ean_scanner_text);


        eanScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implement
                eanText.setText("Not implemented yet, sorry.");
            }
        });
    }

    private void findAndSetupCurrencySpinner() {
        itemCurrencySpinner = findViewById(R.id.item_edit_currency_spinner);
      /*  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCurrencySpinner.setAdapter(adapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.fast_item_overview_navigationbar, menu);
        return true;
    }


    //TODO: implement completely
    private void saveItem() {
        String title = itemTitle.getText().toString();
        String value = itemValue.getText().toString();
        String currency = itemCurrencySpinner.getSelectedItem().toString();
        String purchasedDate = itemPurchaseDate.getText().toString();


        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_VALUE, value);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* if (item.getItemId() == R.id.save_item) {
            saveItem();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
