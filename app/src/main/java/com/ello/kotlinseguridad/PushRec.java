package com.ello.kotlinseguridad;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.ello.kotlinseguridad.Activ.Login.Login;
import com.ello.kotlinseguridad.BIN.BIN;
import com.ello.kotlinseguridad.ParseObj.Usuario;
import com.ello.twelveseconds.Formulario;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PushRec extends BroadcastReceiver {

    Context mContext;
    private String mTitle;
    private String mText;
    private static String channelId="ch_event";
    private CharSequence CANAL_EVENTOSR="Formularios";
    private String mId;


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("onReceive","push");
        mContext = context;
        JSONObject extras;
        try {
            extras = new JSONObject(intent.getStringExtra(ParsePushBroadcastReceiver.KEY_PUSH_DATA));

            String nombre = extras.getString(Usuario.Companion.getField_nom());
            String nombre_formul = extras.getString(Formulario.Companion.getField_nombre());
            if(BIN.Companion.ES_ADMIN()){CrearNotSimple(nombre,nombre_formul);}
            else{Log.e("Notificacion", "No enviada pq no eres administras");}



        } catch (JSONException e) {
            Log.e("Error catch", "catch push");
            e.printStackTrace();
        }
    }



        private void CrearNotSimple(String nombre_persona, String nombre_formul) {

        Log.e("CrearNotSimple","push");
        mTitle="Envío de formulario";
        mText= nombre_persona+" ha respondido el formulario "+nombre_formul + ".";

        int notificationId = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher))
                .setContentTitle(mTitle)
                .setContentText(mText)
                .setAutoCancel(true)
                .setContentIntent(getEventScreenIntent())
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(path);

        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                NotificationChannel channel = new NotificationChannel(channelId,
                        CANAL_EVENTOSR,
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                builder.setChannelId(channelId);         }
            notificationManager.notify(notificationId,builder.build());
        }
        else
        {
            Log.e("Android V","Not supported this notification");
        }





        }

    private PendingIntent getEventScreenIntent() {
        Intent notifyIntent = new Intent(mContext, Login.class);
        return  PendingIntent.getActivity(mContext, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }



}
