package game;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class GameView_Stopwatch extends Thread
{
    long startTime;
    JLabel timeLabel;
    GameView_Stopwatch(JLabel timeLabel)
    {
        this.timeLabel = timeLabel;
        setName( "Game stopwatch" );
    }
    @Override
    public void run()
    {
        startTime = System.currentTimeMillis();

        while(!Thread.interrupted())
        {
            timeLabel.setText(getTime());
            try
            {
                sleep(1000);
            } catch(InterruptedException e) {
                return;
            }
        }
    }

    public String getTime()
    {
        long timePassed = System.currentTimeMillis() - startTime;

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timePassed);
        long seconds =
                TimeUnit.MILLISECONDS.toSeconds(timePassed)
                        - TimeUnit.MINUTES.toSeconds(minutes);

        String stopwatchTime = String.format("Time: %02d:%02d", minutes, seconds);
        return stopwatchTime;
    }
}
