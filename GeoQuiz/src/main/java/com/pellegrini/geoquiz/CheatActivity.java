package com.pellegrini.geoquiz;

/**
 * Created by Fabricio on 17/08/13.
 */

import  android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends  Activity {

    private static final String ACT_TAG = "QuizActivity";
    private static final String CHEATER_KEY = "isCheater";

    public static final String EXTRA_ANSWER_IS_TRUE = "com.pellegrini.geoquiz.CheatActivity.EXTRA_ANSWER_IS_TRUE";
    public static final String EXTRA_ANSWER_SHOWN = "com.pellegrini.geoquiz.CheatActivity.EXTRA_ANSWER_SHOWN";

    private boolean mAnswerIsTrue;
    private boolean mIsCheater;


    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null){
            mIsCheater = savedInstanceState.getBoolean(CHEATER_KEY, false);
        }

        setAnswerShownResult(mIsCheater);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.button_show_answer);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIsCheater = true;
                setAnswerShownResult(true);
            }
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown){
        if(isAnswerShown){
            if(mAnswerIsTrue){
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(ACT_TAG, "onSaveInstanceState() called");

        savedInstanceState.putBoolean(CHEATER_KEY, mIsCheater);
    }

}
