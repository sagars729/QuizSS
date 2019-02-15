package com.example.quizss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HintActivity extends AppCompatActivity {
    private Button mShowAnswer;
    private Button mGoBack;
    private TextView mAnswerTextView;
    private boolean mAnswerIsTrue;
    private double mPenalty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mGoBack = (Button) findViewById(R.id.go_back_button);
        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mAnswerIsTrue = getIntent().getBooleanExtra("ANSWER", false);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPenalty = -.5;
                if(mAnswerIsTrue) mAnswerTextView.setText(R.string.true_button);
                else mAnswerTextView.setText(R.string.false_button);
            }
        });

        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("PENALTY",mPenalty);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}
