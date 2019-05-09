package com.example.moodymusic;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VocalMoodDetermination.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VocalMoodDetermination#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VocalMoodDetermination extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ImageButton mic;
    private StreamPlayer player = new StreamPlayer();
    private MicrophoneHelper microphoneHelper;
    private MicrophoneInputStream capture;
    private MediaRecorder recorder = null;
    protected String mood;
    private boolean listening = false;
    private Thread newUIThread;
    final Handler threadHandler = new Handler();

    private static final String ARG_PARAM1 = "param1";

    private String mParam1 = Integer.toString(R.id.etUsername);

    private OnFragmentInteractionListener mListener;

    public VocalMoodDetermination() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment VocalMoodDetermination.
     */
    // TODO: Rename and change types and number of parameters
    public static VocalMoodDetermination newInstance(String param1) {
        VocalMoodDetermination fragment = new VocalMoodDetermination();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_vocal_mood_determination, container, false);
        TextView textView = viewer.findViewById(R.id.text_vocal_fragment);
        mic = viewer.findViewById(R.id.recordVoice);
        return textView;
    }

    private void enableMicButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mic.setEnabled(true);
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        if(Thread.currentThread() != newUIThread){
            threadHandler.post(runnable);
        } else{
            runnable.run();
        }
    }

    private SpeechToText initSpeechToTextService() {
        IamOptions options = new IamOptions.Builder()
                .apiKey(getString(R.string.api_key))
                .build();
        SpeechToText service = new SpeechToText(options);
        service.setEndPoint(getString(R.string.STT_URL));
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

    public void onStartRecording(Uri uri) {
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
                                SpeechToText speechService = initSpeechToTextService();
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
    }

        private void showError(Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            // Update the icon background
            mic.setBackgroundColor(Color.LTGRAY);
        }

        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private class MicrophoneRecognizeDelegate extends BaseRecognizeCallback implements RecognizeCallback {
        @Override
        public void onTranscription(com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults speechResults) {
            System.out.println(speechResults);
            if (speechResults.getResults() != null && !speechResults.getResults().isEmpty()) {
                String text = speechResults.getResults().get(0).getAlternatives().get(0).getTranscript();
                mood = text;
            }
        }
    }
}
