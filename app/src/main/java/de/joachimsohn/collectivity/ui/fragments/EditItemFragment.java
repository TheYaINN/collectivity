package de.joachimsohn.collectivity.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.Condition;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.ui.ItemBuilder;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;

import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.COLLECTION;

public class EditItemFragment extends Fragment {

    private EditText tfName;
    private Spinner spinnerAmount;
    private EditText tfDescription;
    private EditText tfValue;
    private Spinner spinnerCondition;
    private CalendarView cvCalendarBuyDate;
    private EditText tfPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_add, container, false);
        setHasOptionsMenu(true);
        tfName = view.findViewById(R.id.textfield_name);
        spinnerAmount = view.findViewById(R.id.spinner_amount);
        tfDescription = view.findViewById(R.id.textfield_description);
        tfValue = view.findViewById(R.id.textfield_value);
        cvCalendarBuyDate = view.findViewById(R.id.calendarview_buy_date);
        spinnerCondition = view.findViewById(R.id.spinner_condition);
        spinnerCondition.setAdapter(new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, Condition.values()));
        tfPosition = view.findViewById(R.id.textfield_position);

        //TODO: bind data

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return NavigationHelper.navigateUp(getActivity(), new ItemFragment(), false);
            case R.id.action_save:
                if (hasInsertedData()) {
                    return NavigationHelper.navigateUp(getActivity(), new ItemFragment(), false);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean hasInsertedData() {
        String name = tfName.getText().toString().trim();
        String ean = ""; //TODO
        Bitmap icon = null; //TODO
        Condition condition = (Condition) spinnerCondition.getSelectedItem();
        Calendar buyDate = Calendar.getInstance();
        buyDate.setTimeInMillis(cvCalendarBuyDate.getDate());
        String position = tfPosition.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getContext(), R.string.missing_name, Toast.LENGTH_LONG).show();
            return false;
        }
        if (position.isEmpty()) {
            Toast.makeText(getContext(), R.string.missing_position, Toast.LENGTH_LONG).show();
            return false;
        }
        DataBaseConnector.getInstance().insert(
                new ItemBuilder(name, condition, position, CacheManager.getManager().getIdForCacheLevel(COLLECTION))
                        .addAmount((String) spinnerAmount.getSelectedItem())
                        .addDescription(tfDescription.getText().toString().trim())
                        .addEAN(ean)
                        .addIcon(icon)
                        .addValue(tfValue.getText().toString().trim())
                        .addBuyDate(buyDate)
                        .build());
        return true;
    }

}
