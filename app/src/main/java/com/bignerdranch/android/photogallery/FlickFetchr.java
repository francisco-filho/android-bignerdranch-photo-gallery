package com.bignerdranch.android.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 10/10/17.
 */

public class FlickFetchr {
    private static final String TAG = FlickFetchr.class.getSimpleName();
    private static final String API_KEY = "8eff02827be92603555735001795390f";
    private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final Uri ENDPOINT = Uri.parse("https://api.flickr.com/services/rest/")
                .buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("extras", "url_s")
                .build();

    public List<GalleryItem> fetchRecentPhotos(){
        String url = buildUrl(FETCH_RECENTS_METHOD, null);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(String query){
        String url = buildUrl(SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }

    private String buildUrl(String method, String query){
        Uri.Builder uriBuilder = ENDPOINT.buildUpon()
                .appendQueryParameter("method", method);
        if (method.equals(SEARCH_METHOD)) {
            uriBuilder.appendQueryParameter("text", query);
        }
        return uriBuilder.build().toString();
    }

    public List<GalleryItem> downloadGalleryItems(String url){
        List<GalleryItem> items = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            JSONObject payload = new JSONObject(jsonString);
            parseItems(items, payload);
            Log.i(TAG, jsonString);
            return items;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void parseItems(List<GalleryItem> items, JSONObject body) throws JSONException {
        JSONObject photos = body.getJSONObject("photos");
        JSONArray photosArray = photos.getJSONArray("photo");

        for(int i = 0; i < photosArray.length(); i++) {
            JSONObject jsonObject = photosArray.getJSONObject(i);
            GalleryItem galleryItem = new GalleryItem(
                    jsonObject.getString("title"),
                    jsonObject.getString("id"));

            if (!jsonObject.has("url_s")) {
                continue;
            }
            galleryItem.setUrl(jsonObject.getString("url_s"));
            galleryItem.setOwner(jsonObject.getString("owner"));
            items.add(galleryItem);
        }
    }

    public byte[] getUrlBytes(String uri) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage());
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String uri) throws IOException {
        return new String(getUrlBytes(uri));
    }
}
