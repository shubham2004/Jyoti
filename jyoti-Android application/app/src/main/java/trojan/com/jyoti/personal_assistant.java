package trojan.com.jyoti;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class personal_assistant extends AppCompatActivity implements TextToSpeech.OnInitListener{

    public RelativeLayout rl;
    public int count = 0;
    public TextView txt0,txt1,txt2;
    private TextToSpeech tts;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_assistant);
        rl = (RelativeLayout)findViewById(R.id.fullscreen3);
        txt0 = (TextView)findViewById(R.id.me_bol);
        txt1 = (TextView)findViewById(R.id.tu_bol);
        mDb = FirebaseDatabase.getInstance().getReference();


        tts = new TextToSpeech(this, this);

        rl.setOnTouchListener(new OnSwipeTouchListener(personal_assistant.this) {
            public void onSwipeTop() {
                //Toast.makeText(personal_assistant.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(contacts.this, "right", Toast.LENGTH_SHORT).show();
                getSpeechInput();
                mDb.child("answer").child("output").setValue("");

            }
            public void onSwipeLeft() {
               // Toast.makeText(personal_assistant.this, "left", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(personal_assistant.this,MainActivity.class);
                startActivity(i2);
                finish();
            }
            public void onSwipeBottom() {
                //Toast.makeText(personal_assistant.this, "bottom"+count, Toast.LENGTH_SHORT).show();

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
                    txt0.setText(result.get(0));
                    DatabaseReference mDb = FirebaseDatabase.getInstance().getReference();
                    mDb.child("question").setValue(result.get(0));
                    attachDatabaseReadListener();

                }
                break;
        }
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
                speakOut("You are in personal assistant mode.Swipe right to talk to personal assistant.");
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
    public void attachDatabaseReadListener(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String post = dataSnapshot.child("answer").child("output").getValue(String.class);
                txt1.setText(post);
                speakOut(post);
                //DatabaseReference mDb1 = FirebaseDatabase.getInstance().getReference();
               // mDb1.child("answer").child("output").setValue("");
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        mDb.addValueEventListener(postListener);
    }
}
