package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;


    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,true),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,false),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

    }

    private void checkAnswer(Boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        }else{
            if(userPressedTrue == answerIsTrue){
                messageResId = R.string.correct_toast;
            }else{
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "oncreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(false);
            }
        });

//        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
//        mPrevButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCurrentIndex = (mCurrentIndex - 1)% mQuestionBank.length;
//                updateQuestion();
//            }
//        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)% mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }

        });


        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)% mQuestionBank.length;
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i =CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(i,REQUEST_CODE_CHEAT);

            }
        });

        if(savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }
        updateQuestion();


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){

        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }


}