package trojan.com.jyoti;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class alarm extends AppCompatActivity implements TextToSpeech.OnInitListener{

    public RelativeLayout rl;
    public int count = 0,hours=0,mins=0;
    public TextView txt0,txt1,txt2,txt3;
    private TextToSpeech tts;
    Date dat  = new Date();//initializes to now
    Calendar cal_alarm = Calendar.getInstance();
    Calendar cal_now = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        cal_now.setTime(dat);
        cal_alarm.setTime(dat);

        rl = (RelativeLayout)findViewById(R.id.fullscreen6);
        txt0 = (TextView)findViewById(R.id.textView0);
        txt1 = (TextView)findViewById(R.id.textView1);
        txt2 = (TextView)findViewById(R.id.textView3);
        txt3 = (TextView)findViewById(R.id.textView4);
        final TextView tvarr[] = new TextView[4];
        tvarr[0] = txt0;
        tvarr[1] = txt1;
        tvarr[2] = txt2;
        tvarr[3] = txt3;

        tts = new TextToSpeech(this, this);

        rl.setOnTouchListener(new OnSwipeTouchListener(alarm.this) {
            public void onSwipeTop() {
                Toast.makeText(alarm.this, "top", Toast.LENGTH_SHORT).show();
                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }
                Ringtone ringtone = RingtoneManager.getRingtone(alarm.this, alarmUri);
                ringtone.play();
            }
            public void onSwipeRight() {
                Toast.makeText(alarm.this, "right", Toast.LENGTH_SHORT).show();
                Intent i;
                switch((count-1)%4){
                    case 0:
                        if(hours == 23)
                            hours =0;
                        else
                            hours++;
                        if(hours<10)
                            txt0.setText("0"+hours);
                        else
                            txt0.setText(Integer.toString(hours));
                        speakOut(Integer.toString(hours));
                        break;
                    case 1:
                        if(mins == 55)
                            mins =0;
                        else
                            mins += 1;
                        if(mins<10)
                            txt1.setText("0"+mins);
                        else
                            txt1.setText(Integer.toString(mins));
                        speakOut(Integer.toString(mins));
                        break;
                    case 2:
                        //i = new Intent(alarm.this, braille_speak.class);
                        //startActivity(i);
                        cal_alarm.set(Calendar.HOUR_OF_DAY,hours);//set the alarm time
                        cal_alarm.set(Calendar.MINUTE, mins);
                        cal_alarm.set(Calendar.SECOND,0);
                        if(cal_alarm.before(cal_now)){//if its in the past increment
                            cal_alarm.add(Calendar.DATE,1);
                        }
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Intent myIntent = new Intent(alarm.this, AlarmReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarm.this, 0, myIntent, 0);

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
                        break;
                    case 3:
                        speakOut("Go back to Main menu");
                        i = new Intent(alarm.this, MainActivity.class);
                        startActivity(i);
                }
            }
            public void onSwipeLeft() {
                Toast.makeText(alarm.this, "left", Toast.LENGTH_SHORT).show();
                switch((count-1)%4){
                    case 0:
                        if(hours == 0)
                            hours = 23;
                        else
                            hours--;
                        if(hours<10)
                            txt0.setText("0"+hours);
                        else
                            txt0.setText(Integer.toString(hours));
                        speakOut(Integer.toString(hours));
                        break;
                    case 1:
                        if(mins == 0)
                            mins = 55;
                        else
                            mins -= 5;
                        if(mins<10)
                            txt1.setText("0"+mins);
                        else
                            txt1.setText(Integer.toString(mins));
                        speakOut(Integer.toString(mins));
                        break;
                }

            }
            public void onSwipeBottom() {
                Toast.makeText(alarm.this, "bottom"+count, Toast.LENGTH_SHORT).show();
                if(count == 0){
                    tvarr[count].setTextColor(Color.YELLOW);
                    speakOut("set hours");
                }
                else{
                    tvarr[(count-1)%4].setTextColor(Color.WHITE);
                    tvarr[count%4].setTextColor(Color.YELLOW);
                    switch((count)%4){
                        case 0:
                            speakOut("set hours");
                            break;
                        case 1:
                            speakOut("set minutes");
                            break;
                        case 2:
                            speakOut(tvarr[count%4].getText().toString());
                            break;
                        case 3:
                            speakOut(tvarr[count%4].getText().toString());
                            break;
                    }
                }

                ++count;
            }

        });
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                //btnSpeak.setEnabled(true);
                speakOut("You are in Alarm Mode.");
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    private void speakOut(String s) {

        String text = s;

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
