package es.upm.dit.adsw.ejercicio7adsw;

import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Clase para descargar un canal de noticias RSS
 * Adapted from http://developer.android.com/intl/es/training/basics/network-ops/xml.html
 * https://github.com/z1ux/AndroidPITRSS/blob/master/src/main/java/com/rockylabs/androidpit/rss/MainActivity.java
 */
public class FeedDownloader {
    public static final String TAG = FeedDownloader.class.getName();

    /**
     * Obtiene una lista de entradas RSS de un canal RSS.
     *
     * @param urlString url del canal RSS.
     * @return lista de entradas RSS.
     * @throws IOException            si hay problemas de conexiones
     * @throws XmlPullParserException Si hay un problema de formato
     */
    public List<RssContent.EntryRss> loadXmlFromNetwork(String urlString) throws IOException, ParseException, XmlPullParserException {
        InputStream stream = null;
        List<RssContent.EntryRss> rssItemList = null;
        try {
            stream = downloadUrl(urlString);
            Log.d(TAG, "Downloaded");

            FeedParser parser = new FeedParser();
            rssItemList = parser.parse(stream);
            // Se asegura de cerrar InputStream una vez que la aplicacion
            // ha terminado de usarlo
        } finally {
            if (stream != null)
                stream.close();
        }
        return rssItemList;
    }

    /**
     * Dada una representacion en un string de una URL, establece una conexion y obtiene
     * un input stream.
     */
    private InputStream downloadUrl(String urlString) throws IOException {
        Log.d(TAG, "downloadUrl " + urlString);
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}