package sivianes.foursquaresearch.ui.search.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import sivianes.foursquaresearch.R;
import sivianes.foursquaresearch.ui.search.presenter.SearchPresenter;
import sivianes.foursquaresearch.ui.search.presenter.SearchPresenterImpl;

/**
 * Created by Javier on 10/05/2016.
 */
public class SearchActivity extends AppCompatActivity {
    private EditText searchText;
    private Button searchButton;
    private SearchPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        solveDependencies();
        init();
    }

    private void solveDependencies() {
        presenter = new SearchPresenterImpl(this.getApplicationContext());
    }

    private void init() {
        searchText = (EditText) findViewById(R.id.search_text_venue);
        searchButton = (Button) findViewById(R.id.seatch_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getText() != null || searchText.getText().length() != 0){
                    presenter.searchByName(searchText.getText().toString());
                }else{
                    //TODO: Add view with toast
                }

            }
        });
    }
}
