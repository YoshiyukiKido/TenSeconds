package com.example.tensecondgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import java.text.SimpleDateFormat;
import android.widget.Button;

/**
 * AppName  :TenSecondsGame
 * Author   :Yoshiyuki
 * Date     :Nov. 25, 2021
 */
public class MainActivity extends AppCompatActivity {

    private TextView messageTextView;
    private TextView timerTextView;
    private SimpleDateFormat timerFormat = new SimpleDateFormat("ss.S秒");
    private Handler timerHandler = new Handler();

    private int count, interval;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.messageTextView = findViewById(R.id.message_text);
        this.timerTextView = findViewById(R.id.timer_text);
        this.timerTextView.setText(this.timerFormat.format(0));
        this.count = 0;
        this.interval = 100;

        this.startButton = findViewById(R.id.start_button);
        this.stopButton = findViewById(R.id.stop_button);
        this.stopButton.setEnabled(false);

        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.post(run);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                messageTextView.setText(getString(R.string.run_message));
            }
        });

        this.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.removeCallbacks(run);
                if (count == 10) {
                    messageTextView.setText(getString(R.string.clear_message));
                } else {
                    messageTextView.setText(getString(R.string.retry_message));
                }
                timerTextView.setText(timerFormat.format(count * interval));
                timerTextView.setVisibility(View.VISIBLE);
                count = 0;

                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        });
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            count++;
            Log.d("Runnable", "count:[" + count + "]");
            if (count > 30) {
                timerTextView.setVisibility(View.INVISIBLE);
            }
            timerTextView.setText(timerFormat.format(count * interval));
            timerHandler.postDelayed(this,interval);
        }
    };
}