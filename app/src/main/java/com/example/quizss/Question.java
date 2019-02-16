package com.example.quizss;


public class Question{
    private boolean mAnswerTrue;
    private int mTextResId;
    private int mHintResId;

    public Question (int textResId, boolean answerTrue, int hintResId){
        mAnswerTrue = answerTrue;
        mTextResId = textResId;
        mHintResId = hintResId;
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

    public int getHintResId(){return mHintResId;}

    public void setHintResId(int hintResId){mHintResId = hintResId;}
}
