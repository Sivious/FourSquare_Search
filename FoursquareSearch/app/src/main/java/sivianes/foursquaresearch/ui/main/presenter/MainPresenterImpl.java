package sivianes.foursquaresearch.ui.main.presenter;

import android.content.Intent;

import sivianes.foursquaresearch.framework.foursquare.connection.PerformFourSquareConnection;
import sivianes.foursquaresearch.framework.foursquare.connection.PerformFourSquareConnectionImpl;
import sivianes.foursquaresearch.framework.foursquare.responsehandler.HandleFoursquareConnectionResponse;
import sivianes.foursquaresearch.framework.foursquare.responsehandler.HandleFoursquareConnectionResponseImpl;
import sivianes.foursquaresearch.framework.foursquare.responsehandler.HandlerFoursquareTokenResponse;
import sivianes.foursquaresearch.framework.foursquare.responsehandler.HandlerFoursquareTokenResponseImpl;
import sivianes.foursquaresearch.logic.repository.RepositoryTokenStoreImpl;
import sivianes.foursquaresearch.ui.main.view.MainActivity;

/**
 * Created by Javier on 10/05/2016.
 */
public class MainPresenterImpl implements MainPresenter {
    private MainActivity view;
    private PerformFourSquareConnection performFourSquareConnection;
    private HandleFoursquareConnectionResponse handleFoursquareConnectionResponse;
    private HandlerFoursquareTokenResponse handlerFoursquareTokenResponse;
    private RepositoryTokenStoreImpl repositoryTokenStore;

    public MainPresenterImpl(MainActivity view) {
        this.view = view;
        performFourSquareConnection = new PerformFourSquareConnectionImpl(view.getApplicationContext());
        handleFoursquareConnectionResponse = new HandleFoursquareConnectionResponseImpl(view.getApplicationContext());
        handlerFoursquareTokenResponse = new HandlerFoursquareTokenResponseImpl();
        repositoryTokenStore = new RepositoryTokenStoreImpl(view.getApplicationContext());
    }

    @Override
    public void performLogin() {
        if (alreadyLogged()){
            view.moveToSearchActivity();
        }else{
            performFourSquareConnection.execute(new PerformFourSquareConnection.Callback() {
                @Override
                public void OnSuccess(Intent intent) {
                    System.out.println("Por aqui pasa");
                    view.startActivityIntentWithResult(intent, view.REQUEST_CODE_FSQ_CONNECT);
                }

                @Override
                public void OnFailure() {
                    System.out.println("Por aqui pasa");
                }
            });
        }
    }

    private boolean alreadyLogged() {
        return repositoryTokenStore.getToken() != null;
    }

    @Override
    public void onCompleteConnect(int resultCode, Intent data) {

        handleFoursquareConnectionResponse.execute(resultCode, data, view.getApplicationContext(), new HandleFoursquareConnectionResponse.Callback() {
            @Override
            public void OnSuccess(Intent intent) {
                //TODO: This line breaks the MVC architecture
                view.startActivityForResult(intent, view.REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
            }

            @Override
            public void OnCanceled(String error) {
                view.showToast(error);
            }

            @Override
            public void OnDenied(String error) {
                view.showToast(error);
            }

            @Override
            public void OnError(String error) {
                view.showToast(error);
            }
        });
    }

    @Override
    public void onCompleteTokenExchange(int resultCode, Intent data) {
        handlerFoursquareTokenResponse.execute(resultCode, data, new HandlerFoursquareTokenResponse.Callback() {
            @Override
            public void OnSuccess(String token) {
                repositoryTokenStore.saveToken(token);
                view.moveToSearchActivity();
            }

            @Override
            public void OnError(String message) {
                view.showToast(message);
            }
        });
    }
}
