package com.pellegrini.geoquiz.model;

/**
 * Created by Fabricio on 15/08/13.
 */
public class TrueFalse {

    private int mQuestion;

    private boolean mTrueQuestion;
    private boolean mCheater;

    public TrueFalse(int question, boolean trueQuestion)
    {
        mQuestion = question;
        mTrueQuestion = trueQuestion;
        mCheater = false;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int mQuestion) {
        this.mQuestion = mQuestion;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean mTrueQuestion) {
        this.mTrueQuestion = mTrueQuestion;
    }

    public boolean isCheater() {
        return mCheater;
    }

    public void setCheater(boolean mCheater) {
        this.mCheater = mCheater;
    }
}
