package com.learning.annawang.wrapline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private static final int MAX_LINE_WIDTH = 20;
    private EditText mEtInput;
    private TextView mTvShowInput, mTvShowCutInput;
    private Button mBtnAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtInput = (EditText) this.findViewById(R.id.et_input);
        mTvShowInput = (TextView) this.findViewById(R.id.tv_show_input);
        mTvShowCutInput = (TextView) this.findViewById(R.id.tv_show_cut_input);

        mBtnAction = (Button) this.findViewById(R.id.btn_action);

        mEtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String text = mEtInput.getText().toString();
                mTvShowInput.setText(text);
                return false;
            }
        });

        mBtnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEtInput.getText().toString();

                text.substring(0, MAX_LINE_WIDTH);

                String newLine = "";
                char maxLineChar, preChar, nextChar;
                int i = 0;
                StringBuilder sb = new StringBuilder();
                if (text.length() <= MAX_LINE_WIDTH) {
                    sb.append(text);
                } else {
                    do {
                        maxLineChar = text.charAt(MAX_LINE_WIDTH - 1);
                        preChar = text.charAt(MAX_LINE_WIDTH - 2);
                        nextChar = text.charAt(MAX_LINE_WIDTH);
                        if (' ' == maxLineChar) {
                            newLine = text.substring(0, MAX_LINE_WIDTH);
                            text = text.substring(MAX_LINE_WIDTH);
                        } else {

                            if (' ' == preChar) {
                                newLine = text.substring(0, MAX_LINE_WIDTH - 1);
                                text = text.substring(MAX_LINE_WIDTH - 1);
                            } else if (' ' == nextChar) {
                                newLine = text.substring(0, MAX_LINE_WIDTH);
                                // skip the space in the new line
                                text = text.substring(MAX_LINE_WIDTH + 1);
                            } else if (' ' != preChar && ' ' != nextChar) {
                                // keep worlds, and wrap lines.
                                int index = text.substring(0, MAX_LINE_WIDTH).lastIndexOf(" ");
                                newLine = text.substring(0, index + 1);
                                text = text.substring(index + 1);
                            }
                        }
                        sb.append(newLine + "\r\n");
                        Log.d(TAG, "new line =" + newLine + "\r\n" + "text =" + text);
                    } while (!TextUtils.isEmpty(text) && text.length() > MAX_LINE_WIDTH);
                }


                mTvShowCutInput.setText(sb.toString());
            }
        });
    }
}
