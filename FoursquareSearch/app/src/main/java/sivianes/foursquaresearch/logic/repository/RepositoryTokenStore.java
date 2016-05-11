package sivianes.foursquaresearch.logic.repository;

/**
 * Created by Javier on 10/05/2016.
 */
public interface RepositoryTokenStore {
    void saveToken(String token);
    String getToken();
}
