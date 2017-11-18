package rodrigofagundes.br.uda_android1_proj6_booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by rmfagundes on 14/11/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public android.view.View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item,
                    parent, false);
        }

        Book currentBook = getItem(position);

        TextView titleView = (TextView)listItemView.findViewById(R.id.titulo);
        titleView.setText(currentBook.getTitulo());

        String autores = "";
        for (int i = 0; i < currentBook.getAutores().length; i++) {
            if (i > 0) {
                autores = autores + "\n";
            }
            autores = autores + currentBook.getAutores()[i];
        }
        TextView authorsView = (TextView)listItemView.findViewById(R.id.autores);
        authorsView.setText(autores);

        return listItemView;
    }
}
