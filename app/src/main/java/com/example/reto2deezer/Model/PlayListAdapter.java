package com.example.reto2deezer.Model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.example.reto2deezer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayListAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Playlist> playlists;


    public PlayListAdapter(Activity activity) {
        this.activity = activity;
        playlists = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewPlay = inflater.inflate(R.layout.row_playlist, null, false);
        // Inicializo componentes del view
        TextView tv_nombre_lista = viewPlay.findViewById(R.id.textNombre);
        TextView tv_nombre_creador = viewPlay.findViewById(R.id.textCreador);
        TextView tv_numero_items = viewPlay.findViewById(R.id.textNumCanciones);
        ImageView iv_image = viewPlay.findViewById(R.id.imagePlaylist);
        // Lleno los componentes del view
        tv_nombre_lista.setText("Nombre Lista: " + playlists.get(i).getTitle());
        tv_nombre_creador.setText("Nombre Creador: " + playlists.get(i).getCreator().getName());
        tv_numero_items.setText("ID: " + playlists.get(i).getId());
        Picasso.get().load(playlists.get(i).getSmallImageUrl()).into(iv_image);

        return viewPlay;

    }

    public void agregarPlaylist(Playlist playlist) {
        playlists.add(playlist);
        notifyDataSetChanged();
    }

    public void limpiarPlaylist() {
        if (playlists.size() != 0 && playlists != null) {
            playlists.clear();
        }
        notifyDataSetChanged();
    }

    public ArrayList<Playlist> getArrayPlaylist() {
        return playlists;
    }

    public void setArrayPlaylist(ArrayList<Playlist> arrayPlaylist) {
        this.playlists = arrayPlaylist;
    }

}
