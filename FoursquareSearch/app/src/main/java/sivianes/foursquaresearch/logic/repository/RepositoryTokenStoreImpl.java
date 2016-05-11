package sivianes.foursquaresearch.logic.repository;

import android.content.Context;

import sivianes.foursquaresearch.framework.data.Preferences;

/**
 * Created by Javier on 10/05/2016.
 */
public class RepositoryTokenStoreImpl implements RepositoryTokenStore {

    private final Context context;

    public RepositoryTokenStoreImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveToken(String token) {
        Preferences.setRegistrationToken(context, token);
    }

    @Override
    public String getToken() {
        return Preferences.getRegistrationToken(context);
    }
}
