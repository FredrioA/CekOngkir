package com.example.cekongkir.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cekongkir.R;
import com.example.cekongkir.data.model.city.DataCity;
import com.example.cekongkir.ui.adapter.SearchCityAdapter;
import com.example.cekongkir.ui.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends AppCompatActivity {

    List<DataCity> dataList = new ArrayList<>();
    List<DataCity> output = new ArrayList<>();
    SearchViewModel viewModel;
    SearchCityAdapter adapter;

    EditText searchView;
    SwipeRefreshLayout refreshCity;
    RecyclerView rvCity;
    ImageView ivBarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        initViews();
        initRecyclerview();
        initSearch();

        ivBarBack.setOnClickListener(view -> finish());
    }

    private void initViews() {
        viewModel   = new ViewModelProvider(this).get(SearchViewModel.class);
        searchView  = findViewById(R.id.search_keyword);
        refreshCity = findViewById(R.id.refreshCity);
        rvCity      = findViewById(R.id.rvCity);
        ivBarBack   = findViewById(R.id.ivBarBack);
        adapter     = new SearchCityAdapter(this, dataList);
    }

    private void initRecyclerview() {
        rvCity.setLayoutManager(new LinearLayoutManager(this));
        rvCity.setAdapter(adapter);
    }

    private void initSearch() {
        searchView.requestFocus();
        List<DataCity> output2 = new ArrayList<>();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        setupData();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                for(int i = 0; i < output.size(); i++){
                    if(output.get(i).getCityName().equalsIgnoreCase(s.toString())){
                        output2.add(output.get(i));
                    }
                }
                adapter.setData(output2);
            }
        });

    }

    private void setupData() {
        viewModel.getCity().observe(SearchCityActivity.this, results -> {
            if(results != null) {
                output.addAll(results);
                adapter.setData(output);
            }
        });
    }

}
