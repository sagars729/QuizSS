package com.example.quizss;


public class Question{
    private boolean mAnswerTrue;
    private int mTextResId;

    public Question (int textResid, boolean answerTrue){
        mAnswerTrue = answerTrue;
        mTextResId = textResid;
    }

    public boolean isAnswerTrue(){
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue){
        mAnswerTrue = answerTrue;
    }

    public int getTextResId(){
        return mTextResId;
    }

    public void setTextResId(int textResId){
        mTextResId = textResId;
    }
}
