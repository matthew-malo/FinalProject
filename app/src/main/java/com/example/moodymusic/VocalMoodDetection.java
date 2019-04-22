package com.example.moodymusic;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;

import java.io.IOException;
import java.io.InputStream;

import static android.provider.Settings.System.getString;


public class VocalMoodDetection {

    private EditText input;
    private ImageButton mic;
    private Button translate;
    private ImageButton play;
    private TextView translatedText;

    private SpeechToText speechService;

    private StreamPlayer player = new StreamPlayer();
    private MicrophoneHelper microphoneHelper;

    private MicrophoneInputStream capture;
    private boolean listening = false;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        microphoneHelper = new MicrophoneHelper(this);

        speechService = initSpeechToTextService();

        input = findViewById(R.id.input);
        mic = findViewById(R.id.mic);


        input.addTextChangedListener(new EmptyTextWatcher() {
            @Override
            public void onEmpty(boolean empty) {
                if (empty) {
                    translate.setEnabled(false);
                } else {
                    translate.setEnabled(true);
                }
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listening) {
                    // Update the icon background
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mic.setBackgroundColor(Color.GREEN);
                        }
                    });
                    capture = microphoneHelper.getInputStream(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                speechService.recognizeUsingWebSocket(getRecognizeOptions(capture),
                                        new MicrophoneRecognizeDelegate());
                            } catch (Exception e) {
                                showError(e);
                            }
                        }
                    }).start();

                    listening = true;
                } else {
                    // Update the icon background
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mic.setBackgroundColor(Color.LTGRAY);
                        }
                    });
                    microphoneHelper.closeInputStream();
                    listening = false;
                }
            }
        });

        play.setEnabled(false);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SynthesisTask().execute(translatedText.getText().toString());
            }
        });
    }


    private void showTranslation(final String translation) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                translatedText.setText(translation);
            }
        });
    }

    private void showError(final Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                // Update the icon background
                mic.setBackgroundColor(Color.LTGRAY);
            }
        });
    }

    private void showMicText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                input.setText(text);
            }
        });
    }

    private void enableMicButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mic.setEnabled(true);
            }
        });
    }

    private SpeechToText initSpeechToTextService() {
        SpeechToText service = new SpeechToText();
        String username = getString(R.string.speech_text_username);
        String password = getString(R.string.speech_text_password);
        service.setUsernameAndPassword(username, password);
        service.setEndPoint(getString(R.string.speech_text_url));
        return service;
    }


    private RecognizeOptions getRecognizeOptions(InputStream captureStream) {
        return new RecognizeOptions.Builder()
                .audio(captureStream)
                .contentType(ContentType.OPUS.toString())
                .model("en-US_BroadbandModel")
                .interimResults(true)
                .inactivityTimeout(2000)
                .build();
    }

    private abstract class EmptyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        // assumes text is initially empty
        private boolean isEmpty = true;

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                isEmpty = true;
                onEmpty(true);
            } else if (isEmpty) {
                isEmpty = false;
                onEmpty(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}

        public abstract void onEmpty(boolean empty);
    }

    private class MicrophoneRecognizeDelegate extends BaseRecognizeCallback {
        @Override
        public void onTranscription(SpeechRecognitionResults speechResults) {
            System.out.println(speechResults);
            if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                showMicText(text);
            }
        }

        @Override
        public void onError(Exception e) {
            try {
                // This is critical to avoid hangs
                // (see https://github.com/watson-developer-cloud/android-sdk/issues/59)
                capture.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            showError(e);
            enableMicButton();
        }

        @Override
        public void onDisconnected() {
            enableMicButton();
        }
    }

}
