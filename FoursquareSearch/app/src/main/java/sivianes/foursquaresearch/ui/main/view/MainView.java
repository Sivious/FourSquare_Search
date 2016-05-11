package sivianes.foursquaresearch.ui.main.view;

import android.content.Intent;

/**
 * Created by Javier on 10/05/2016.
 */
public interface MainView {
    int REQUEST_CODE_FSQ_CONNECT = 200;
    int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;

    void startActivityIntentWithResult(Intent intent, int request_code);
    void showToast(String error);

    void moveToSearchActivity();
}
