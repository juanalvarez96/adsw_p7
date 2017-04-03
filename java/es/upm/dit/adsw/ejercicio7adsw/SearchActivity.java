package es.upm.dit.adsw.ejercicio7adsw;


import android.content.Intent;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getName();
    private List<String> optionList;
    private List<String> urlList;
    private ProgressBar progressBar;
    private Spinner spinner;
    private Button button;
    private String url;
    private String words;
    boolean blank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final EditText source = (EditText) findViewById(R.id.Source);
        optionList = new ArrayList<>();
        urlList = new ArrayList<>();
        button = (Button) findViewById(R.id.button);
        optionList.add("El Pais");
        urlList.add("http://ep00.epimg.net/rss/elpais/portada.xml");
        optionList.add("SlashDat");
        urlList.add("http://dat.etsit.upm.es/?q=/rss/all");
        optionList.add("NY Times - Technology");
        urlList.add("http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml");
        optionList.add("Xataka Android");
        urlList.add("http://feeds.weblogssl.com/xatakandroid");
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, optionList);
         // adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);
        int position = spinner.getSelectedItemPosition();
        Log.d(TAG," "+ position);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = urlList.get(spinner.getSelectedItemPosition());
                words=source.getText().toString();
                if(words.length()==0) {
                    blank = true;
                }
                else{
                    blank=false;
                }
                RssContent.clear();
                new RetrieveRssTask().execute();

            }
        });
    }

    private class RetrieveRssTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);


        }

        @Override
        protected String doInBackground(String... params) {
                FeedDownloader downloader = new FeedDownloader();
                List<RssContent.EntryRss> listEntries;
                try {
                    listEntries = downloader.loadXmlFromNetwork(url);

                    List<RssContent.EntryRss> listMatched = new ArrayList<>();
                    if (blank)
                        listMatched = listEntries;
                    if (words != null) {
                        for (RssContent.EntryRss entry : listEntries) {
                            if (isMatched(entry.title, words)) {
                                listMatched.add(entry);
                            }
                        }
                        for (RssContent.EntryRss entry : listMatched)
                            RssContent.addEntryRss(entry);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                return null;
            }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPostExecute(String end) {
            progressBar.setVisibility(View.GONE);
            Log.d(TAG,"prueba");
            Log.d(TAG,RssContent.ITEMS.toString());
            Intent i = new Intent(SearchActivity.this,ListViewActivity.class);
            startActivity(i);
        }
    }

    private boolean isMatched(String title, String words) {
       Scanner sc=new Scanner(title);
        sc.useDelimiter("[^\\p{javaLowerCase}\\p{javaUpperCase}]+");
        Log.d(TAG,title);
        words=words.toLowerCase();
        while (sc.hasNext()){
            String word = sc.next().toLowerCase();
            if(word.equals(words)) {
                sc.close();
                return true;
            }
        }
        sc.close();
        return false;
    }


}