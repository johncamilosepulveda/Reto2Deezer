package com.example.reto2deezer.Model;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Track;
import com.example.reto2deezer.Activity.PlaylistActivity;
import com.example.reto2deezer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CancionAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Track> arrayTracks;

    public CancionAdapter(Activity activity){
        this.activity = activity;
        arrayTracks = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return arrayTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(activity);
        View viewPlay = inflater.inflate(R.layout.row_canciones, null, false);
        // Inicializo componentes del view
        TextView tv_nombre_cancion = viewPlay.findViewById(R.id.textNombre);
        TextView tv_artista_cancion = viewPlay.findViewById(R.id.textArtista);
        TextView tv_ano_lanzamiento = viewPlay.findViewById(R.id.textNumCanciones);
        ImageView iv_image = viewPlay.findViewById(R.id.imageSong);
        // Lleno los componentes del view
        tv_nombre_cancion.setText("Nombre Canción: "+arrayTracks.get(position).getTitle());
        tv_artista_cancion.setText("Artista Canción: "+arrayTracks.get(position).getArtist().getName());
        tv_ano_lanzamiento.setText("Rank: "+arrayTracks.get(position).getRank());

        Log.e("ERROR",""+iv_image);

        Picasso.get().load(arrayTracks.get(position).getAlbum().getSmallImageUrl()).into(iv_image);
        return viewPlay;
    }

    public void agregarTrack(Track track){
        arrayTracks.add(track);
        notifyDataSetChanged();
    }

    public void limpiarTrack() {
        if (arrayTracks.size() != 0 && arrayTracks != null) {
            arrayTracks.clear();
        }
        notifyDataSetChanged();
    }

    public ArrayList<Track> getArrayTracks() {
        return arrayTracks;
    }

    public void setArrayTracks(ArrayList<Track> arrayTracks) {
        this.arrayTracks = arrayTracks;
    }
}
