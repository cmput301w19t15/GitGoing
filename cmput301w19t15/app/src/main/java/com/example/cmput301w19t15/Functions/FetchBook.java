package com.example.cmput301w19t15.Functions;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// @reuse: https://github.com/google-developer-training/android-fundamentals/tree/master/WhoWroteIt
public class FetchBook extends AsyncTask<String,Void,String> {

    private EditText mAuthorInput;
    private EditText mTitleInput;

    private static final String base_url = "https://www.googleapis.com/books/v1/volumes?";
    private static final String query = "q=isbn:";

    public FetchBook(EditText authorInput, EditText titleInput, EditText isbnInput){
        this.mAuthorInput = authorInput;
        this.mTitleInput = titleInput;
    }
    @Override
    protected String doInBackground(String... strings) {

        String isbn = strings[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            String builtUrl = base_url+query+isbn;
            URL requestURL = new URL(builtUrl);

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line +"\n");
            }
            if(buffer.length() == 0){
                return null;
            }
            bookJSONString = buffer.toString();
        } catch (Exception e) {
            Log.d("testing","error:" + e.toString());
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return bookJSONString;
        }
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;

            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() || (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title").replace("\"","");
                    authors = volumeInfo.getString("authors").replace("[","").replace("\"","").replace("]","");
                } catch (Exception e){
                    e.printStackTrace();
                }
                // Move to the next item.
                i++;
            }
            // If both are found, display the result.
            if (title != null && authors != null){
                mTitleInput.setText(title);
                mAuthorInput.setText(authors);
            }
        } catch (Exception e){
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            e.printStackTrace();
        }

    }
}
