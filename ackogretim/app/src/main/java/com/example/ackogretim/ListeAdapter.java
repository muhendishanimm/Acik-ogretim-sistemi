package com.example.ackogretim;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class ListeAdapter extends ArrayAdapter<Sorular> {
    private List<Sorular> liste;
    private Activity activity;
    private LayoutInflater inflater;

    public class ViewHolder {
        public TextView soru;
    }

    public ListeAdapter(@NonNull Context context, @NonNull List<Sorular> objects) {
        super(context,R.layout.liste_layout,objects);
        liste = objects;
    }

    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Sorular getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return liste.get(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.liste_layout, null);
        }

        Sorular m = getItem(position);
        if (m != null) {
            TextView soru = (v.findViewById(R.id.textView31));

            if(soru != null){
                soru.setText(m.getSoru());

            }
        }
        return v;

    }
}
