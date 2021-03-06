package com.example.quizss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mSkipButton;
    private Button mResetButton;
    private Button mHintButton;
    private Button mLeadButton;

    private TextView mQuestionTextView;
    private TextView mQuestionHeader;
    private TextView mPointsTextView;
    private TextView mHighScoreTextView;

    private Question [] mQuestionBank = new Question[20];

    private int mCurrentIndex = -1;
    private double mScore = 0;
    private boolean mLock = false;
    private ProgressBar mProgressBar;
    private boolean mSent = false;

    private static final int HINT_ACTIVITY_REQUEST_CODE = 0;
    private static final int RANK_ACTIVITY_REQUEST_CODE = 1;

    private SharedPreferences mSharedPreferences;
    private double mHighScore = 0;

    private void updateHighScore(){
        if(mScore > mHighScore){
            mHighScore = mScore;
            mSharedPreferences.edit().putFloat("HighScore", (float) mScore).apply();
            mHighScoreTextView.setText("Local High Score: " + mHighScore);
        }
    }
    private void updateQuestion() {
        if(mCurrentIndex < mQuestionBank.length - 1) {
            mCurrentIndex++;
            mProgressBar.setProgress(mCurrentIndex);
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
            mQuestionHeader.setText("Question " + mCurrentIndex);
        }else{
            mLock = true;
            mProgressBar.setProgress(mQuestionBank.length);
            updateHighScore();
        }
        mPointsTextView.setText("Points: " + mScore);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = R.string.incorrect_toast;
        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            mScore++;
        }else mScore--;
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //getActionBar().setTitle(R.string.action_bar);
        getSupportActionBar().setTitle(R.string.action_bar);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mSkipButton = (Button) findViewById(R.id.skip_button);
        mResetButton = (Button) findViewById(R.id.reset_button);
        mHintButton = (Button) findViewById(R.id.hint_button);
        mLeadButton = (Button) findViewById(R.id.lead_button);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionHeader = (TextView) findViewById(R.id.question_header);
        mPointsTextView = (TextView) findViewById(R.id.points_text_view);
        mHighScoreTextView = (TextView) findViewById(R.id.high_score_text_view);

        mSharedPreferences = this.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
        mHighScore = mSharedPreferences.getFloat("HighScore", 0.0f);
        mHighScoreTextView.setText("Local High Score: " + mHighScore);

        mProgressBar = (ProgressBar) findViewById(R.id.determinateBar);
        mProgressBar.setMax(mQuestionBank.length);

        for(int i = 0; i < mQuestionBank.length; i+=1)
            mQuestionBank[i] = new Question(
                    getResources().getIdentifier("q" + i,"string",getPackageName()),
                    getResources().getBoolean(getResources().getIdentifier("a" + i,"bool",getPackageName())),
                    getResources().getIdentifier("h"+i,"raw",getPackageName())
            );
        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mLock){
                    checkAnswer(true);
                    updateQuestion();
                } else Toast.makeText(QuizActivity.this, R.string.locked_toast, Toast.LENGTH_SHORT).show();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mLock) {
                    checkAnswer(false);
                    updateQuestion();
                } else Toast.makeText(QuizActivity.this, R.string.locked_toast, Toast.LENGTH_SHORT).show();
            }
        });
        mSkipButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                if(!mLock) updateQuestion();
                else Toast.makeText(QuizActivity.this, R.string.locked_toast, Toast.LENGTH_SHORT).show();
           }
        });
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLock = false;
                mCurrentIndex = -1;
                mScore = 0;
                mSent = false;
                updateQuestion();
            }
        });
        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, HintActivity.class);
                i.putExtra("HINT",mQuestionBank[mCurrentIndex].getHintResId());
                startActivityForResult(i,HINT_ACTIVITY_REQUEST_CODE);
            }
        });
        mLeadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, LeadActivity.class);
                i.putExtra("SCORE",mScore);
                i.putExtra("SENT",mSent);
                i.putExtra("LOCK",mLock);
                startActivityForResult(i,RANK_ACTIVITY_REQUEST_CODE);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == HINT_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                mScore+=data.getDoubleExtra("PENALTY",0);
            }
        }else if(requestCode == RANK_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                mSent = data.getBooleanExtra("SENT",false);
            }
        }

    }

}
