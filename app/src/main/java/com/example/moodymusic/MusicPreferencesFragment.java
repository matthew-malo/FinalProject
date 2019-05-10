package com.example.moodymusic;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicPreferencesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicPreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicPreferencesFragment<rockBox> extends Fragment {


    private OnFragmentInteractionListener mListener;

    private static final String ARG_PARAM1 = "param1";
    private String mParam1 = Integer.toString(R.id.etUsername);
    private CheckBox rockBox,rapBox,hnrBox, popBox,countryBox,classicalBox;
    private CheckBox.OnClickListener cbListener;

    public MusicPreferencesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MusicPreferencesFragment.
     */
    public static MusicPreferencesFragment newInstance(String param1) {
        MusicPreferencesFragment fragment = new MusicPreferencesFragment();
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
        View viewer = inflater.inflate(R.layout.fragment_music_preferences, container, false);
        TextView textView = viewer.findViewById(R.id.text_music_preferences_fragment);
        rockBox = viewer.findViewById(R.id.rockBox);
        rapBox = viewer.findViewById(R.id.rapBox);
        hnrBox = viewer.findViewById(R.id.hnrBox);
        popBox = viewer.findViewById(R.id.popBox);
        countryBox = viewer.findViewById(R.id.countryBox);
        classicalBox = viewer.findViewById(R.id.classicalBox);
        final ArrayList<String> musicPreference = new ArrayList<String>();
        cbListener = (new View.OnClickListener() {
            @Override
            public void onClick(View viewer) {
                if (rockBox.isChecked())
                    musicPreference.add("rock");
                if (rapBox.isChecked())
                    musicPreference.add("rap");
                if (hnrBox.isChecked())
                    musicPreference.add("hip hop and r&b");
                if (popBox.isChecked())
                    musicPreference.add("pop");
                if (countryBox.isChecked())
                    musicPreference.add("country");
                if (classicalBox.isChecked())
                    musicPreference.add("classical");
            }
        });
        return viewer;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);

        }
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
}
