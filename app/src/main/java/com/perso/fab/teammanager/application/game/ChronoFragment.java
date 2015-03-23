package com.perso.fab.teammanager.application.game;

import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import com.perso.fab.teammanager.R;
import com.perso.fab.teammanager.enums.NotificationCounterState;

import java.util.Timer;
import java.util.TimerTask;

public class ChronoFragment extends Fragment {

    private Chronometer chrono;

    /**
     * The time period of the counter before notification of the end, in ms
     */
    private final static int GAME_PERIOD = 1000 * 10 * 1;
    /**
     * Last period of the the chrono has been paused
     */
    private long timeStopped = 0;
    /**
     * Managing the state of the chronometer
     */
    private NotificationCounterState currentState = NotificationCounterState.RESET;
    /**
     * Send notification after the time delay
     */
    private TimerTask task;
    /**
     * One shot timer to notify after the time delay
     */
    private Timer timer = new Timer();
    /*
     *Components of the view
     */
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonReset;

    public static ChronoFragment newInstance() {
        ChronoFragment fragment = new ChronoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ChronoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chronometer, container, false);

        //Get the components
        chrono = (Chronometer) view.findViewById(R.id.chronometer1);
        buttonStart = ((Button) view.findViewById(R.id.button_start));
        buttonStop = ((Button) view.findViewById(R.id.button_stop));
        buttonReset = ((Button) view.findViewById(R.id.button_reset));

        //Setup starting state
        buttonStop.setEnabled(false);
        buttonReset.setEnabled(false);

        //Setup listeners
        setUpListeners(view);

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Attach the listeners to the action buttons
     * @param view
     */
    private void setUpListeners(View view) {

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometer(v);
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometer(v);
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartChronometer(v);
            }
        });
    }

    //Managing the state of the chronometer
    private void startChronometer(View view) {
        if (currentState == NotificationCounterState.RESET) {
            manageVibrator(NotificationCounterState.START);
        } else if (currentState == NotificationCounterState.PAUSE) {
            manageVibrator(NotificationCounterState.CONTINUE);
        }
    }

    private void stopChronometer(View view) {
        if (currentState == NotificationCounterState.START
                || currentState == NotificationCounterState.CONTINUE) {
            manageVibrator(NotificationCounterState.PAUSE);
        }
    }

    private void restartChronometer(View view) {
        if (currentState != NotificationCounterState.RESET) {
            manageVibrator(NotificationCounterState.RESET);
        }
    }

    /**
     * Acting on the chronometer state
     * @param state : the state of the chronometer
     *              @see NotificationCounterState
     */
    private void manageVibrator(NotificationCounterState state) {

        switch (state) {
            case START:
                //Set & start timer and task
                task = new TimerTask() {
                    @Override
                    public void run() {
                        ((Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE)).vibrate(1000);
                    }
                };

                timer = new Timer();
                timer.schedule(task, GAME_PERIOD);

                //Set & start the chronometer
                timeStopped = 0;
                chrono.setBase(SystemClock.elapsedRealtime() - timeStopped);
                chrono.start();

                //Update control state
                buttonStop.setEnabled(true);
                buttonStart.setEnabled(false);
                buttonReset.setEnabled(true);
                break;

            case PAUSE:
                //Stop the task
                task.cancel();

                //Register the time and stop chrono
                timeStopped = SystemClock.elapsedRealtime() - chrono.getBase();
                chrono.stop();

                //Update control state
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                break;

            case CONTINUE:
                //Reset task and schedule timer
                task = new TimerTask() {
                    @Override
                    public void run() {
                        ((Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE)).vibrate(1000);
                    }
                };
                timer.schedule(task, GAME_PERIOD - timeStopped);

                //Reset chrono whit the time stopped and start
                chrono.setBase(SystemClock.elapsedRealtime() - timeStopped);
                chrono.start();

                //Update control state
                buttonStop.setEnabled(true);
                buttonStart.setEnabled(false);
                break;

            case RESET:
                //Stop the tasks and timer
                task.cancel();
                timer.cancel();

                //Stop the chronometer
                chrono.stop();

                //Update control state
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonReset.setEnabled(false);
                break;

            default:
        }

        currentState = state;
    }
}
