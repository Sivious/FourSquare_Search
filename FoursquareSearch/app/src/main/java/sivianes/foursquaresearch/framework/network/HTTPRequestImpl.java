package sivianes.foursquaresearch.framework.network;

import com.google.common.io.ByteStreams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Javier on 10/05/2016.
 */
public class HTTPRequestImpl implements HTTPRequest{
    public static final int STATUS_CODE_NOT_FOUND = 404;

    private static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
    private static final String BASE_URL = "https://api.foursquare.com/v2";

    public void get(String url, String params, HTTPRequest.Callback responseListener) {
        try {
            URL getUrl = new URL(getAbsoluteUrl(url));

            HttpURLConnection urlConnection = (HttpURLConnection) getUrl.openConnection();

            urlConnection.setAllowUserInteraction(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestProperty(HEADER_ACCEPT_LANGUAGE, Locale.getDefault().getLanguage());
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            responseHandler(urlConnection, responseListener);

        } catch (MalformedURLException me) {
            me.printStackTrace();
            responseListener.onNullResponse();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            responseListener.onNullResponse();
        }
    }

    private void responseHandler(HttpURLConnection urlConnection, HTTPRequest.Callback responseListener) {
        int responseCode = 0;

        try {
            responseCode = urlConnection.getResponseCode();

            switch (responseCode) {
                case HttpsURLConnection.HTTP_OK:
                    onSuccess(urlConnection, responseListener);
                    break;
                case STATUS_CODE_NOT_FOUND:
                    responseListener.onFailureServiceWithError();
                    break;
                default:
                    responseListener.onFailureServiceWithError();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
            responseListener.onFailureServiceWithError();
        }
    }

    private void onSuccess(HttpURLConnection urlConnection, HTTPRequest.Callback responseListener) {
        StringBuffer sb = new StringBuffer();

        try {
            InputStream stream = urlConnection.getInputStream();

            String response = streamToString(urlConnection.getInputStream());
            JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();


            JSONArray groups = (JSONArray) jsonObj.getJSONObject("response").getJSONArray("groups");
            int length = groups.length();

            responseListener.onSuccessWebService(response);


            /*if (length > 0) {
                for (int i = 0; i < length; i++) {
                    JSONObject group    = (JSONObject) groups.get(i);
                    JSONArray items     = (JSONArray) group.getJSONArray("items");

                    int ilength         = items.length();

                    for (int j = 0; j < ilength; j++) {
                        JSONObject item = (JSONObject) items.get(j);

                        FsqVenue venue  = new FsqVenue();

                        venue.id        = item.getString("id");
                        venue.name      = item.getString("name");

                        JSONObject location = (JSONObject) item.getJSONObject("location");

                        Location loc    = new Location(LocationManager.GPS_PROVIDER);

                        loc.setLatitude(Double.valueOf(location.getString("lat")));
                        loc.setLongitude(Double.valueOf(location.getString("lng")));

                        venue.location  = loc;
                        venue.address   = location.getString("address");
                        venue.distance  = location.getInt("distance");
                        venue.herenow   = item.getJSONObject("hereNow").getInt("count");
                        venue.type      = group.getString("type");

                        venueList.add(venue);
                    }
                }
            }*/















            /*InputStreamReader isReader = new InputStreamReader(stream);

            BufferedReader br = new BufferedReader(isReader);
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            responseListener.onSuccessWebService(sb.toString());

            br.close();
            urlConnection.disconnect();*/

        } catch (IOException e) {
            responseListener.onFailureServiceWithError();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String streamToString(InputStream inputStream) {
        String total = null;
        try {
            total = new String(ByteStreams.toByteArray(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
            String empty_string = "";
            return empty_string;
        }

        //TODO: Delete this
        /*BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return total;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
