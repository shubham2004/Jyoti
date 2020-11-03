package trojan.com.jyoti;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class braille_speak extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextView textVew;
    private TextToSpeech tts;
    private RelativeLayout rl;
    DatabaseReference mDb = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_braille_speak);
        textVew = (TextView) findViewById(R.id.text1);
        rl = (RelativeLayout)findViewById(R.id.fullscreen4);
        tts = new TextToSpeech(this, this);

        rl.setOnTouchListener(new OnSwipeTouchListener(braille_speak.this) {
            public void onSwipeTop() {
                //Toast.makeText(braille_speak.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                //Toast.makeText(contacts.this, "right", Toast.LENGTH_SHORT).show();

                getSpeechInput();
            }

            public void onSwipeLeft() {
                //Toast.makeText(braille_speak.this, "left", Toast.LENGTH_SHORT).show();
                mDb.child("speech").setValue("");
                Intent i2 = new Intent(braille_speak.this, MainActivity.class);
                startActivity(i2);
            }

            public void onSwipeBottom() {
                //Toast.makeText(braille_speak.this, "bottom", Toast.LENGTH_SHORT).show();

            }

        });
    }


    public void getSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager())!=null) {
            startActivityForResult(intent, 10);
        } else{
            Toast.makeText(this,"Your Device doesn't Support Speech Input",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if (resultCode != RESULT_CANCELED && data!=null){
                    ArrayList<String> result =  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textVew.setText(result.get(0));
                    DatabaseReference mDb = FirebaseDatabase.getInstance().getReference();
                    mDb.child("speech").setValue(result.get(0).toLowerCase().replaceAll(" ", "-"));
                }
                break;
        }
    }
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                //btnSpeak.setEnabled(true);
                speakOut("You are in Braille Reading Mode. swipe right and speak");
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
