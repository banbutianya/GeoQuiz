package com.bignerdranch.android.geoquiz;

/**
 * Created by acer-1 on 2016/10/11.
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId,boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId(){
        return mTextResId;
    }

    public void setTextResId(int textResId){
        mTextResId = textResId;
    }

    public boolean isAnswerTrue(){
        return mAnswerTrue;
    }

    public void setAnswerTrue(Boolean answerTrue){
        mAnswerTrue = answerTrue;
    }
}
