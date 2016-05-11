package sivianes.foursquaresearch.ui.main.presenter;

import android.content.Intent;

/**
 * Created by Javier on 10/05/2016.
 */
public interface MainPresenter {


    void performLogin();

    void onCompleteConnect(int resultCode, Intent data);

    void onCompleteTokenExchange(int resultCode, Intent data);
}
