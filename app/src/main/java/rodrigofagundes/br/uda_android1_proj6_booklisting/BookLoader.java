package rodrigofagundes.br.uda_android1_proj6_booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmfagundes on 17/11/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String url;

    public BookLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        System.out.println(url);
        if (url == null) {
            return null;
        }
        String jsonString = QueryUtils.fetchBookData(url);
        if (!jsonString.equals("")) {
            return QueryUtils.extractBooks(jsonString);
        } else {
            return new ArrayList<Book>();
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
