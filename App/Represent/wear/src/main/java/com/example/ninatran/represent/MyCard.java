package com.example.ninatran.represent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCard extends CardFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "Title";
    private static final String PARTY = "Party";
    private static final String PICTUREID = "PictureID";

    // TODO: Rename and change types of parameters
    private String mTitle;
    private String mParty;
    private int mPic;


    private OnFragmentInteractionListener mListener;

    public MyCard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCard.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCard newInstance(String title, String party, int pictureID) {
        MyCard fragment = new MyCard();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(PARTY, party);
        args.putInt(PICTUREID, pictureID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
            mParty = getArguments().getString(PARTY);
            mPic = getArguments().getInt(PICTUREID);
        }
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout;
        if (mTitle.equals("Vote")) {
            layout = inflater.inflate(R.layout.vote_main, container, false);
        } else {
            layout = inflater.inflate(R.layout.fragment_my_card, container, false);
            TextView title = (TextView) layout.findViewById(R.id.title);
            TextView party = (TextView) layout.findViewById(R.id.party);
            ImageView img = (ImageView) layout.findViewById(R.id.picId);

            title.setText(mTitle);
            party.setText(mParty);
            img.setImageResource(mPic);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent trial = new Intent(getActivity(), WatchToPhoneService.class);
//                    startActivity(trial);
                    trial.putExtra("REP_NAME", mTitle);
                    getActivity().startService(trial);
                }
            });
        }
        return layout;
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
