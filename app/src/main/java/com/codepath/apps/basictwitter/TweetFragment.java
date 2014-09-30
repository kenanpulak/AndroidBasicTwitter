package com.codepath.apps.basictwitter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TweetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TweetFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TweetFragment extends DialogFragment {
    private TextView mCharCount;
    private EditText mTweetMessage;

    public interface TweetFragmentListener {
        void onFinishTweetFragment(String message);
    }

    public TweetFragment() {
        // Empty constructor required for DialogFragment
    }

    public static TweetFragment newInstance(String title) {
        TweetFragment frag = new TweetFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tweet, container);

        String title = getArguments().getString("title", "New Tweet");
        getDialog().setTitle(title);

        Button sendButton = (Button) view.findViewById(R.id.btnSend);
        mCharCount = (TextView) view.findViewById(R.id.tvCharCount);
        mTweetMessage = (EditText) view.findViewById(R.id.etTweetMessage);

        mTweetMessage.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                mCharCount.setText(String.valueOf(140 - s.length()));
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                TweetFragmentListener listener = (TweetFragmentListener) getActivity();
                listener.onFinishTweetFragment(mTweetMessage.getText().toString());
                dismiss();
            }
        });

        return view;
    }
}
