package com.example.moodymusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

import androidx.annotation.NonNull;


public class VocalMoodDetection {

    private ImageButton mic;
    private StreamPlayer player = new StreamPlayer();
    private MicrophoneHelper microphoneHelper;
    private String username = new String("test");
    private String password = new String("test2");

    private MicrophoneInputStream capture;
    private MediaRecorder recorder = null;
    private static final String URL = "https://stream.watsonplatform.net/speech-to-text/api";




    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    SpeechToText speechService = new SpeechToText(username, password);

    private void recordMessage() {
        boolean listening = false;
        if(!listening) {
            capture = new MicrophoneInputStream(true);
            new Thread(new Runnable() {
                @Override public void run() {
                    try {
                        speechService.recognizeUsingWebSocket(capture, getRecognizeOptions(), new MicrophoneRecognizeDelegate());
                    } catch (Exception e) {
                        showError(e);
                    }
                }
            }).start();
            listening = true;
            Toast.makeText(MainActivity.this,"Listening....Click to Stop", Toast.LENGTH_LONG).show();
        } else {
            try {
                capture.close();
                listening = false;
                Toast.makeText(MainActivity.this,"Stopped Listening....Click to Start", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        capture = new MicrophoneInputStream(true);
        speechService.recognizeUsingWebSocket(new MicrophoneInputStream(),
                getRecognizeOptions(), new BaseRecognizeCallback() {
                    @Override
                    public void onTranscription(SpeechResults speechResults){
                        String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                        System.out.println(text);
                    }

                    @Override
                    public void onError(Exception e) {
                    }

                    @Override public void onDisconnected() {
                    }

                });
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void recordVoice(){
        SpeechToText speechService = new SpeechToText();
        microphoneHelper = new MicrophoneHelper(this);

    }



}
