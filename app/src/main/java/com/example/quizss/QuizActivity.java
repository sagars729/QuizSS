package com.example.quizss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mSkipButton;
    private Button mResetButton;
    private Button mHintButton;

    private TextView mQuestionTextView;
    private TextView mQuestionHeader;
    private TextView mPointsTextView;

    private Question [] mQuestionBank = new Question[20];

    private int mCurrentIndex = -1;
    private double mScore = 0;
    private boolean mLock = false;

    private static final int HINT_ACTIVITY_REQUEST_CODE = 0;

    private void updateQuestion() {
        if(mCurrentIndex < mQuestionBank.length - 1) {
            mCurrentIndex++;
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
            mQuestionHeader.setText("Question " + mCurrentIndex);
            mPointsTextView.setText("Points: " + mScore);
        }else{
            mPointsTextView.setText("Points: " + mScore);
            mLock = true;
        }
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

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mSkipButton = (Button) findViewById(R.id.skip_button);
        mResetButton = (Button) findViewById(R.id.reset_button);
        mHintButton = (Button) findViewById(R.id.hint_button);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionHeader = (TextView) findViewById(R.id.question_header);
        mPointsTextView = (TextView) findViewById(R.id.points_text_view);

        for(int i = 0; i < mQuestionBank.length; i+=1)
            mQuestionBank[i] = new Question(
                    getResources().getIdentifier("q" + i,"string",getPackageName()),
                    getResources().getBoolean(getResources().getIdentifier("a" + i,"bool",getPackageName()))
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
                updateQuestion();
            }
        });
        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, HintActivity.class);
                i.putExtra("ANSWER",mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(i,HINT_ACTIVITY_REQUEST_CODE);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == HINT_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                mScore+=data.getDoubleExtra("PENALTY",0);
            }
        }
    }

}
