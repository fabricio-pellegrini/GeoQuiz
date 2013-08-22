package com.pellegrini.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.pellegrini.geoquiz.model.TrueFalse;

public class QuizActivity extends Activity {

    private static final String ACT_TAG = "QuizActivity";
    private static final String CURRENT_KEY = "currentIndex";
    private static final String CHEATER_KEY = "isCheater";


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private ImageButton mNextButton;
    private ImageButton mPreviewButton;

    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton = (Button) findViewById(R.id.button_true);
        mFalseButton = (Button) findViewById(R.id.button_false);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mNextButton = (ImageButton) findViewById(R.id.button_next);
        mPreviewButton = (ImageButton) findViewById(R.id.button_preview);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(CURRENT_KEY, 0);
            mIsCheater = savedInstanceState.getBoolean(CHEATER_KEY, false);
        }

        updateQuestion();

        mTrueButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);

                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue );

                startActivityForResult(i, R.layout.activity_cheat);
            }
        });

        mNextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;

                mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
                updateQuestion();
            }
        });

        mPreviewButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Se 0 então mCurrentIndex = mQuestionBank.length
                if(mCurrentIndex == 0)
                    mCurrentIndex = mQuestionBank.length;
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;


                mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
                updateQuestion();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(ACT_TAG, "onStart() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(ACT_TAG, "onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(ACT_TAG, "onResume() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(ACT_TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(ACT_TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId = 0;
        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        } else if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        } else {
          messageResId =  R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(ACT_TAG, "onSaveInstanceState() called");
        savedInstanceState.putInt(CURRENT_KEY, mCurrentIndex);
        savedInstanceState.putBoolean(CHEATER_KEY, mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null){
            return;
        }

        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);

        mQuestionBank[mCurrentIndex].setCheater(mIsCheater);
    }

}
