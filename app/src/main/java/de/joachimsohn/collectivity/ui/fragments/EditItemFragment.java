package de.joachimsohn.collectivity.ui.fragments;

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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.Condition;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.Tag;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;

import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.ITEM;

public class EditItemFragment extends Fragment {

    private EditText tfName;
    private Spinner spinnerAmount;
    private EditText tfDescription;
    private EditText tfValue;
    private Spinner spinnerCondition;
    private CalendarView cvCalendarBuyDate;
    private EditText tfPosition;
    private EditText tfTags;

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
        tfTags = view.findViewById(R.id.textfield_tags);

        CacheManager.getManager().getItemCache().observe(requireActivity(), stl -> {
            Optional<Item> currentItem = stl.stream().filter(i -> i.getId() == CacheManager.getManager().getIdForCacheLevel(ITEM)).findFirst();
            if (currentItem.isPresent()) {
                tfName.setText(currentItem.get().getName());
                spinnerAmount.setSelection(currentItem.get().getAmount() - 1);
                tfDescription.setText(currentItem.get().getDescription());
                tfValue.setText((currentItem.get().getValue() != null ? String.valueOf(currentItem.get().getValue()) : ""));
                cvCalendarBuyDate.setDate(currentItem.get().getBuyDate().getTimeInMillis());
                spinnerCondition.setSelection(currentItem.get().getCondition().ordinal());
                tfPosition.setText(currentItem.get().getPosition());
            }
        });
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
        Item item = CacheManager.getManager().getItemCache().getValue().stream().filter(i -> i.getId() == CacheManager.getManager().getIdForCacheLevel(ITEM)).findFirst().get();
        item.setName(name);
        item.setCondition(condition);
        item.setPosition(position);
        item.setBuyDate(buyDate);
        item.setAmount(spinnerAmount.getSelectedItemPosition() + 1);
        item.setDescription(tfDescription.getText().toString());
        item.setValue(!tfValue.getText().toString().isEmpty() ? BigDecimal.valueOf(Double.parseDouble(String.format("%s", tfValue.getText().toString()))) : BigDecimal.ZERO);
        item.setTags(Arrays.stream(tfTags.getText().toString().split(",")).map(Tag::new).collect(Collectors.toList()));
        DataBaseConnector.getInstance().update(item);
        return true;
    }

}
