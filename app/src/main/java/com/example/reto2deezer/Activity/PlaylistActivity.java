package com.example.reto2deezer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.example.reto2deezer.Model.CancionAdapter;
import com.example.reto2deezer.R;
import com.squareup.picasso.Picasso;

public class PlaylistActivity extends AppCompatActivity {

    private ImageView iv_flecha_to_main;
    private ImageView iv_playlist;
    private TextView tv_nombre_playlist;
    private TextView tv_descripcion;
    private TextView tv_numero_canciones;
    private ListView lv_canciones;
    private CancionAdapter adaptadorTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Bundle bundle = getIntent().getExtras();
        long idPlaylistRecived = 0;
        String idAdaptadorPlaylistRecived = "";
        long idFromTrack = 0;
        if(bundle!=null){
            idPlaylistRecived = bundle.getLong("playlist");
            idAdaptadorPlaylistRecived = bundle.getString("adaptadorPlaylist");
            idFromTrack = bundle.getLong("idFromTrack");
        }
        final long aux = idPlaylistRecived;
        final String auxAdapPlaylist = idAdaptadorPlaylistRecived;
        final long auxIdPlayReceived = idFromTrack;

        iv_playlist = findViewById(R.id.imageCancion);
        tv_nombre_playlist = findViewById(R.id.textNombrePL);
        tv_descripcion = findViewById(R.id.textDescripcion);
        tv_numero_canciones = findViewById(R.id.textNumCanciones);
        lv_canciones = findViewById(R.id.ListCanciones);
        adaptadorTrack = new CancionAdapter(this);

        lv_canciones.setAdapter(adaptadorTrack);
        adaptadorTrack.notifyDataSetChanged();

        final DeezerConnect deezerConnect = new DeezerConnect(this, MainActivity.AplicationID);

        RequestListener jsonListener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                adaptadorTrack.limpiarTrack();
                Playlist playlist = (Playlist) result;
                // Llenar las variables
                Picasso.get().load(playlist.getPictureUrl()).into(iv_playlist);
                tv_nombre_playlist.setText("Nombre: " + playlist.getTitle());
                tv_descripcion.setText("Descripción: " + playlist.getDescription().toString());
                tv_numero_canciones.setText("#Canciones: " + playlist.getTracks().size());
                // do something with the albums
                for (int i = 0; i < playlist.getTracks().size(); i++)
                    adaptadorTrack.agregarTrack(playlist.getTracks().get(i));

            }

            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };

        // create the request
        DeezerRequest request = null;
        if (auxIdPlayReceived==0){
            request = DeezerRequestFactory.requestPlaylist(idPlaylistRecived);
        }else{
            request = DeezerRequestFactory.requestPlaylist(auxIdPlayReceived);
        }
        request.setId("myRequest");
        deezerConnect.requestAsync(request, jsonListener);

        lv_canciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), CancionActivity.class);
                long idTrack = Long.parseLong(adaptadorTrack.getArrayTracks().get(position).getId() + "");
                long idAdaptadorTrack = 0;
                if (auxIdPlayReceived==0) {
                    idAdaptadorTrack = aux;
                } else {
                    idAdaptadorTrack = auxIdPlayReceived;
                }
                i.putExtra("track", idTrack);
                i.putExtra("playlist",idAdaptadorTrack);
                i.putExtra("oldAdaptadorPlaylist",auxAdapPlaylist);
                startActivity(i);
                finish();
            }
        });

        iv_flecha_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("oldAdaptadorPlaylist",auxAdapPlaylist);
                startActivity(i);
                finish();
            }
        });
    }

}
