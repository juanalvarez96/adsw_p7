package es.upm.dit.adsw.ejercicio7adsw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by juanalvarez on 16/05/16.
 */
public class DetailActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle extras=getIntent().getExtras();
        final int position=extras.getInt("clave");
        final WebView webview=(WebView)findViewById(R.id.webView);
        final TextView title=(TextView)findViewById(R.id.textView_title);
        final TextView date = (TextView)findViewById(R.id.textView2) ;
        title.setText(RssContent.ITEMS.get(position).title);
        date.setText(RssContent.ITEMS.get(position).published);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(RssContent.ITEMS.get(position).link);
        webview.setVisibility(View.VISIBLE);

    }

}
