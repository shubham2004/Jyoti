package trojan.com.jyoti;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class contacts extends AppCompatActivity implements TextToSpeech.OnInitListener{

    public RelativeLayout rl;
    public int count = 0;
    public TextView txt0,txt1,txt2;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        rl = (RelativeLayout)findViewById(R.id.fullscreen2);
        txt0 = (TextView)findViewById(R.id.Father);
        txt1 = (TextView)findViewById(R.id.Mother);
        txt2 = (TextView)findViewById(R.id.brother);
        final TextView tvarr[] = new TextView[3];
        tvarr[0] = txt0;
        tvarr[1] = txt1;
        tvarr[2] = txt2;

        tts = new TextToSpeech(this, this);

        rl.setOnTouchListener(new OnSwipeTouchListener(contacts.this) {
            public void onSwipeTop() {
                //Toast.makeText(contacts.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(contacts.this, "right", Toast.LENGTH_SHORT).show();
                Intent i;
                String data = tvarr[(count-1)%3].getText().toString();
                String num = data.substring(data.length()-10,data.length());
                //String num1 = data.substring(0,data.length()-10);
                //speakOut("calling "+num1);
               // Toast.makeText(contacts.this, num, Toast.LENGTH_SHORT).show();
                i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+ num));
                startActivity(i);

            }
            public void onSwipeLeft() {
                //Toast.makeText(contacts.this, "left", Toast.LENGTH_SHORT).show();

                Intent i2 = new Intent(contacts.this,MainActivity.class);
                startActivity(i2);
            }
            public void onSwipeBottom() {
                //Toast.makeText(contacts.this, "bottom"+count, Toast.LENGTH_SHORT).show();
                if(count == 0){
                    tvarr[count].setTextColor(Color.YELLOW);}
                else{
                    tvarr[(count-1)%3].setTextColor(Color.WHITE);
                    tvarr[count%3].setTextColor(Color.YELLOW);
                }
                String data = tvarr[count%3].getText().toString();
                String num1 = data.substring(0,data.length()-10);
                speakOut(num1);
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
                speakOut("You are in Contacts. navigate by swiping down and swipe right to select");
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
