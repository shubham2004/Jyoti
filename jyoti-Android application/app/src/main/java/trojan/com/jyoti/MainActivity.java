package trojan.com.jyoti;

import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.graphics.Color.RED;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    public RelativeLayout rl;
    public int count = 0;
    public TextView txt0,txt1,txt2,txt3,txt4;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl = (RelativeLayout)findViewById(R.id.fullscreen);
        txt0 = (TextView)findViewById(R.id.textView0);
        txt1 = (TextView)findViewById(R.id.textView1);
        txt2 = (TextView)findViewById(R.id.textView2);
        txt3 = (TextView)findViewById(R.id.textView3);
        //txt4 = (TextView)findViewById(R.id.textView4);
        final TextView tvarr[] = new TextView[4];
        tvarr[0] = txt0;
        tvarr[1] = txt1;
        tvarr[2] = txt2;
        tvarr[3] = txt3;
        //tvarr[4] = txt4;

        tts = new TextToSpeech(this, this);

        rl.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                //Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                Intent i;
                switch((count-1)%4){
                    case 0:
                        i = new Intent(MainActivity.this, contacts.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(MainActivity.this, personal_assistant.class);
                        startActivity(i);
                        break;
                    case 2:
                        i = new Intent(MainActivity.this, braille_speak.class);
                        startActivity(i);
                        break;
                    case 3:
                        i = new Intent(MainActivity.this,book_reader.class);
                        startActivity(i);
                        break;
                    case 4:
                        i = new Intent(MainActivity.this,alarm.class);
                        startActivity(i);
                        break;
                }
            }
            public void onSwipeLeft() {
                //Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
            public void onSwipeBottom() {
                //Toast.makeText(MainActivity.this, "bottom"+count, Toast.LENGTH_SHORT).show();
                if(count == 0){
                    tvarr[count].setTextColor(Color.YELLOW);}
                else{
                    tvarr[(count-1)%4].setTextColor(Color.WHITE);
                    tvarr[count%4].setTextColor(Color.YELLOW);
                }
                speakOut(tvarr[count%4].getText().toString());
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
                speakOut("Hello, Welcome to jyoti. You are in Menu Mode. navigate by swiping down and swipe right to select");
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
