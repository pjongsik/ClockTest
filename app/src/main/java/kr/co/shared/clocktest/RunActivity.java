package kr.co.shared.clocktest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import kr.co.shared.clocktest.data.RoutineData;
import kr.co.shared.clocktest.util.SoundManager;

public class RunActivity extends AppCompatActivity {

    Fragment readyFragment;

    TextView txtWorkout;
    TextView txtRest;
    TextView txtSets;
    Button btnStart;
    Button btnStop;
    RoutineData data;

    int workOutTime = 0;
    int restTime = 0;
    int setCount = 0;
    Timer timer;
    TimerTask timerTask;

    // sound
    SoundManager soundManager;
    SoundPool soundPool;

    // progress
    private ProgressBar workoutProgressBar;
    private ProgressBar restProgressBar;
    private int workoutProgressStatus = 0;
    private int restProgressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        init();
    }

    void init() {
         getSupportActionBar().hide();

         readyFragment = new ReadyFragment();
         txtWorkout = findViewById(R.id.txt_workout);
         txtRest = findViewById(R.id.txt_rest);
         txtSets = findViewById(R.id.txt_sets);
         btnStart = findViewById(R.id.btn_start);
         btnStop = findViewById(R.id.btn_stop);

         workoutProgressBar = findViewById(R.id.workout_progress_bar);
         restProgressBar = findViewById(R.id.rest_progress_bar);

         setTimer();
         getData();
         displayTime();
         setEvent();
         initSound();
     }

    private void initSound() {
        soundPool = new SoundPool.Builder().build();
        soundManager = new SoundManager(this, soundPool);
        soundManager.addSound(0, R.raw.surprise);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // 화면 방향이 변경되었을 때 실행할 코드를 여기에 추가
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }

        setContentView(R.layout.activity_run);
        init();
    }


    public void playSound() {
        soundManager.playSound(0);
    }

    public void closeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 전달받은 fragment를 지워라
        transaction.remove(fragment);
        transaction.commit();

        // ready 닫고 시작
        btnStart.setEnabled(true);
        btnStop.setEnabled(true);
        startTimer();
    }


    private void displayReadyFragment() {
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);

        //화면에 보여지는 fragment를 추가하거나 바꿀 수 있는 객체를 만든다.
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //첫번째로 보여지는 fragment는 firstFragment로 설정한다.
        transaction.add(R.id.container, readyFragment);

        //fragment의 변경사항을 반영시킨다.
        transaction.commit();
    }

    private void setTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("pjs", "run------------");
                Log.d("pjs", "workOutTime : " + String.valueOf(workOutTime));
                Log.d("pjs", "restTime : " + String.valueOf(restTime));
                Log.d("pjs", "setCount : " + String.valueOf(setCount));

                // 반복실행할 구문
                if (workOutTime > 0) {
                    workOutTime = workOutTime - 1;

                } else {
                    if (restTime > 0) {
                        restTime = restTime - 1;

                        if (restTime == 0) {
                            setCount = setCount - 1;

                            if (setCount == 0) {
                                Log.d("pjs", "모두 종료!!");
                                timer.cancel();
                            }
                            else {
                                // timer 리셋
                                workOutTime = data.getWork_time();
                                restTime = data.getRest_time();
                            }
                        }
                    }
                }
                handler.post(() -> displayTime());
            }
        };

    }

    private void displayTime() {
        txtWorkout.setText( String.valueOf(workOutTime));
        txtRest.setText(String.valueOf(restTime));
        txtSets.setText(String.valueOf(setCount));

        //
        int totalWorkoutTime = data.getWork_time();
        workoutProgressStatus = totalWorkoutTime - workOutTime;
        workoutProgressBar.setProgress(workoutProgressStatus);
        Log.d("pjs", "displayTime^^ workoutProgressStatus : " + String.valueOf(workoutProgressStatus));


        int totalRestTime = data.getRest_time();
        restProgressStatus = totalRestTime - restTime;
        restProgressBar.setProgress(restProgressStatus);
        Log.d("pjs", "displayTime^^ restProgressStatus : " + String.valueOf(restProgressStatus));
    }

    void startTimer() {

        Log.d("pjs", "timer.purge() : " + String.valueOf(timer.purge()));

        if (timer.purge() == 0) {
            setTimer();
        }
        timer.schedule(timerTask, 0, 1000);
    }

    private  boolean isPause = false;

    private void setEvent() {
        btnStart.setOnClickListener(view -> {
            Log.d("pjs", "start ~!!");

            if (btnStart.getText().equals("START")) {
                if (isPause == false) {
                    displayReadyFragment();
                } else {
                    startTimer();
                    isPause = false;
                }
                btnStart.setText("PAUSE");
            }
            else {
                isPause = true;
                timer.cancel();
                btnStart.setText("START");
            }
        });

        btnStop.setOnClickListener(view -> {
            Log.d("pjs", "stop ~!!");
            timer.cancel();
            btnStart.setText("START");
            isPause = false;
            getData();
        });
    }

    private void getData() {
        Intent secondIntent = getIntent();
        data = (RoutineData)secondIntent.getSerializableExtra("routine");
        Log.d("pjs", data.toString());

        workOutTime = data.getWork_time();
        restTime = data.getRest_time();
        setCount = data.getSet_count();

        //
        workoutProgressBar.setMax(data.getWork_time());
        restProgressBar.setMax(data.getRest_time());

        // 초기화
        workoutProgressStatus = 0;
        restProgressStatus = 0;
        displayTime();
    }
}
