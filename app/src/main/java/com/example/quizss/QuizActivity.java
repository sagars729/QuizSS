package com.example.quizss;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mSkipButton;
    private TextView mQuestionTextView;
    private Question [] mQuestionBank = new Question[20];

    private int mCurrentIndex = -1;
    private boolean mLock = false;

    private void updateQuestion() {
        if(mCurrentIndex < mQuestionBank.length - 1) {
            mCurrentIndex++;
            mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        }else mLock = true;
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = R.string.incorrect_toast;
        if(userPressedTrue == answerIsTrue) messageResId = R.string.correct_toast;
        Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mSkipButton = (Button) findViewById(R.id.skip_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        for(int i = 0; i < mQuestionBank.length; i+=1){
            String qid = "q" + i;
            String aid = "a" + i;
            mQuestionBank[i] = new Question(getResources().getIdentifier(qid,"string",getPackageName()),getResources().getBoolean(getResources().getIdentifier(aid,"bool",getPackageName())));
        }
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
    }

}
