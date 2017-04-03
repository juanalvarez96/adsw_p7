package es.upm.dit.adsw.ejercicio7adsw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Contenido RSS
 */
public class RssContent {

    public static final List<EntryRss> ITEMS = new ArrayList<>();
    public static final Map<String, EntryRss> ITEM_MAP = new HashMap<>();

    public static void addEntryRss(EntryRss entry){
        ITEMS.add(entry);
        ITEM_MAP.put(entry.id, entry);
    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }
    /**
     * Noticia Rss
     */
    public static class EntryRss implements Serializable{
        private static final long serialVersionUID = 7526472295622776147L;
        public final String id;
        public final String title;
        public final String description;
        public final String published;
        public final String link;

        public EntryRss(String id, String title, String description, String published, String link) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.published = published;
            this.link = link;
        }
        @Override
        public String toString() {
            return title;
        }
    }

}


