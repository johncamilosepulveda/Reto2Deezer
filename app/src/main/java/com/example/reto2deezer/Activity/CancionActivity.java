package com.example.reto2deezer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.example.reto2deezer.R;
import com.squareup.picasso.Picasso;


public class CancionActivity extends AppCompatActivity {

        private ImageView iv_cancion;
        private TextView tv_nombre;
        private TextView tv_artista;
        private TextView tv_duracion;
        private Button btn_escuchar;
        private String webString;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cancion);

            long idTrackRecived = 0;
            long idPlaylistReceived = 0;
            String idAdaptadorPlaylistRecived = "";
            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                idTrackRecived = bundle.getLong("track");
                idPlaylistReceived = bundle.getLong("playlist");
                idAdaptadorPlaylistRecived = bundle.getString("adaptadorPlaylist");
            }
            final long aux = idPlaylistReceived;
            final String aux2 = idAdaptadorPlaylistRecived;

            iv_cancion = findViewById(R.id.imageSong);
            tv_nombre = findViewById(R.id.textNombre);
            tv_artista = findViewById(R.id.textArtista);
            tv_duracion = findViewById(R.id.textDuracion);
            btn_escuchar = findViewById(R.id.butEscuchar);

            final DeezerConnect deezerConnect = new DeezerConnect(this, MainActivity.AplicationID);

            // the request listener
            RequestListener jsonListener = new JsonRequestListener() {

                public void onResult(Object result, Object requestId) {
                    Track track = (Track) result;

                    webString = track.getPreviewUrl();
                    // Llenar las variables
                    Picasso.get().load(track.getArtist().getSmallImageUrl()).into(iv_cancion);
                    tv_nombre.setText("Nombre: " + track.getTitle());
                    tv_artista.setText("Artista: " + track.getArtist().getName());
                    int durationMinutes = 0;
                    int durationSeconds = 0;
                    int i = 0;
                    int total = track.getDuration();
                    while (total >= 60) {
                        total -= 60;
                        i++;
                    }
                    durationMinutes = i;
                    durationSeconds = track.getDuration() - (60 * i);
                    if (durationMinutes < 10 && durationSeconds >= 10) {
                        tv_duracion.setText("Duración: 0" + durationMinutes + ":" + durationSeconds);
                    } else if (durationSeconds < 10 && durationMinutes >= 10) {
                        tv_duracion.setText("Duración: " + durationMinutes + ":0" + durationSeconds);
                    } else if (durationMinutes < 10 && durationSeconds < 10) {
                        tv_duracion.setText("Duración: 0" + durationMinutes + ":0" + durationSeconds);
                    } else {
                        tv_duracion.setText("Duración: " + durationMinutes + ":" + durationSeconds);

                    }
                }

                public void onUnparsedResult(String requestResponse, Object requestId) {
                }

                public void onException(Exception e, Object requestId) {
                }
            };

            DeezerRequest request = DeezerRequestFactory.requestTrack(idTrackRecived);

            request.setId("myRequest");

            deezerConnect.requestAsync(request, jsonListener);

            final long finalIdTrackRecived = idTrackRecived;
            btn_escuchar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] arrayWeb = webString.split("//");

                    Uri web = Uri.parse(arrayWeb[0] + "//" + arrayWeb[1]);
                    Intent i = new Intent(Intent.ACTION_VIEW, web);
                    Intent chooser = Intent.createChooser(i, "Continuar con:");
                    if (i.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooser);
                    }

                }
            });

        }

}
