package sivianes.foursquaresearch.ui.main.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import sivianes.foursquaresearch.R;
import sivianes.foursquaresearch.ui.main.presenter.MainPresenter;
import sivianes.foursquaresearch.ui.main.presenter.MainPresenterImpl;
import sivianes.foursquaresearch.ui.search.view.SearchActivity;

public class MainActivity extends AppCompatActivity implements MainView {


    private Button loginButton;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        solveDependencies();
        init();
    }

    private void solveDependencies() {
        // TODO: Wish I had enought time to integrate Dagger
        presenter = new MainPresenterImpl(this);
    }

    private void init() {
        loginButton = (Button) findViewById(R.id.main_button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        presenter.performLogin();
    }

    @Override
    public void startActivityIntentWithResult(Intent intent, int request_code) {
        startActivityForResult(intent, request_code);
    }

    @Override
    public void showToast(String messsage) {
        Toast.makeText(getApplicationContext(), messsage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveToSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_FSQ_CONNECT:
                presenter.onCompleteConnect(resultCode, data);
                break;

            case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
                presenter.onCompleteTokenExchange(resultCode, data);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
