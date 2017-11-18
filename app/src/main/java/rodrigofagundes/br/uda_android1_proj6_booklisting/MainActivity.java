package rodrigofagundes.br.uda_android1_proj6_booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private static final int BOOK_LOADER_ID = 1;
    private static final String LOG_TAG = MainActivity.class.getName();
    private ListView bookListView;
    private BookAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        bookListView = (ListView) findViewById(R.id.list);

        adapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(adapter);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        LoaderManager loaderManager = getLoaderManager();
        if (isConnected) {
            if (((EditText)findViewById(R.id.busca)).getText().toString().equals("")) {
                ((ProgressBar)findViewById(R.id.loading)).setVisibility(View.INVISIBLE);
                TextView msgView = (TextView)findViewById(R.id.empty);
                msgView.setText(getResources().getString(R.string.search_empty));
                bookListView.setEmptyView(msgView);
            } else {
                loaderManager.initLoader(BOOK_LOADER_ID, null, this);
            }
        } else {
            ((ProgressBar)findViewById(R.id.loading)).setVisibility(View.INVISIBLE);
            TextView msgView = (TextView)findViewById(R.id.empty);
            msgView.setText(getResources().getString(R.string.no_connection));
            bookListView.setEmptyView(msgView);
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, QueryUtils.getUrl(((EditText)findViewById(R.id.busca)).getText().toString()));
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        ((ProgressBar)findViewById(R.id.loading)).setVisibility(View.INVISIBLE);
        adapter.clear();
        if (((EditText)findViewById(R.id.busca)).getText().toString().equals("")) {
            TextView msgView = (TextView)findViewById(R.id.empty);
            msgView.setText(getResources().getString(R.string.search_empty));
            bookListView.setEmptyView(msgView);
        } else if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        } else {
            TextView msgView = (TextView)findViewById(R.id.empty);
            msgView.setText(getResources().getString(R.string.empty_list));
            bookListView.setEmptyView(msgView);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        ((ProgressBar)findViewById(R.id.loading)).setVisibility(View.VISIBLE);
        adapter.clear();
    }

    public void search(View view) {
        if (((EditText)findViewById(R.id.busca)).getText().toString().equals("")) {
            TextView msgView = (TextView)findViewById(R.id.empty);
            msgView.setText(getResources().getString(R.string.search_empty));
            bookListView.setEmptyView(msgView);
        } else {
            getLoaderManager().restartLoader(BOOK_LOADER_ID, null, this);
        }
    }
}
