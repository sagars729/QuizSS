package com.example.quizss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HintActivity extends AppCompatActivity {
    private Button mPlayHint;
    private Button mGoBack;
    private double mPenalty = 0;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        getSupportActionBar().setTitle(R.string.action_bar);

        mPlayHint = (Button) findViewById(R.id.play_hint_button);
        mGoBack = (Button) findViewById(R.id.go_back_button);
        int hintResId = getIntent().getIntExtra("HINT",R.raw.h1);
        mMediaPlayer = MediaPlayer.create(HintActivity.this, hintResId);

        mPlayHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPenalty = -.5;
                mMediaPlayer.start();
            }
        });

        mGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaPlayer.release();
                mMediaPlayer = null;
                Intent i = new Intent();
                i.putExtra("PENALTY",mPenalty);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mMediaPlayer.release();
        mMediaPlayer = null;
        Intent i = new Intent();
        i.putExtra("PENALTY",mPenalty);
        setResult(RESULT_OK,i);
        finish();
    }
}
