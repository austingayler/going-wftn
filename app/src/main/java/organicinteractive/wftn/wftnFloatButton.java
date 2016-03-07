package organicinteractive.wftn;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;

public class wftnFloatButton extends Service {
    private WindowManager windowManager;
    private ImageView imageView;

    public static final int S1 = R.raw.wftn_snd1;
    public static final int S2 = R.raw.wftn_snd2;
    public static final int S3 = R.raw.wftn_snd3;

    private static SoundPool soundPool;
    private static HashMap soundPoolMap;

    @Override
    public void onCreate() {
        super.onCreate();

//        Toast.makeText(getBaseContext(),"we gettin created", Toast.LENGTH_SHORT).show();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        imageView= new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.wftn_btn_sm2);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);

        soundPoolMap = new HashMap(3);
        soundPoolMap.put(S1, soundPool.load(getBaseContext(), R.raw.wftn_snd1, 1) );
        soundPoolMap.put(S2, soundPool.load(getBaseContext(), R.raw.wftn_snd2, 2) );
        soundPoolMap.put(S3, soundPool.load(getBaseContext(), R.raw.wftn_snd3, 3));

        // Setup layout parameter
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT; // Orientation
        params.x = 100; // where you want to draw this, coordinates
        params.y = 100;

        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
//                Toast.makeText(getBaseContext(),"we friggin in dis", Toast.LENGTH_SHORT).show();
//                if(soundPool == null || soundPoolMap == null){
//                    initSounds(context);
//                }

                float volume = (float) 1.0; // whatever in the range = 0.0 to 1.0

                int soundID = 1 + (int)(Math.random() * ((3 - 1) + 1));
//                Toast.makeText(getBaseContext(), Integer.toString(soundID), Toast.LENGTH_SHORT).show();
                soundPool.play(soundID, volume, volume, 1, 0, 1f);
                return true;
            }
        });



        // At it to window manager for display, it will be printed over any thing
        windowManager.addView(imageView, params);

    }

    public void onDestroy() {
        windowManager.removeView(imageView);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
