package com.example.testaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "TestAudio";
    private static TextView txtVw;
    private static Button doGoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doGoodButton = (Button) findViewById(R.id.doGoodBtn);
        doGoodButton.setOnClickListener(mGood);


    }


    public void printText(String str) {
        txtVw.setText(txtVw.getText() + "\r\n" + str);
    }

    public void clrText() {
        txtVw.setText("");
    }


    private View.OnClickListener mGood = new View.OnClickListener() {
        public void onClick(View v) {
            short[] buffer = new short[100000];
            int frameRate = 44100;
            //int deviceID;
            //int samplesPerFrame;
            AudioTrack audioTrack;
            int minBufferSize;
            int bufferSize;

            Log.d(TAG,"DO GOOD button clicked");

            minBufferSize =
                    AudioTrack.getMinBufferSize(
                            frameRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
            Log.d(TAG,"Audio minBufferSize = " + minBufferSize);
            bufferSize = (3 * (minBufferSize / 2)) & ~3;
            Log.d(TAG,"Audio bufferSize = " + bufferSize);
            audioTrack =
                    new AudioTrack(
                            AudioManager.STREAM_MUSIC,
                            frameRate,
                            AudioFormat.CHANNEL_OUT_STEREO,
                            AudioFormat.ENCODING_PCM_16BIT,
                            bufferSize,
                            AudioTrack.MODE_STREAM);

            for (int i=0;i<10000;i++) {
                buffer[i] = (short) 15000;
            }

            audioTrack.write(buffer,0,buffer.length);

            audioTrack.play();

            for (int i=0;i<10000;i++) {
                buffer[i] = (short) 8000;
            }

            audioTrack.write(buffer,0,buffer.length);

            Log.d(TAG, "That's all");
        }
    };
}
