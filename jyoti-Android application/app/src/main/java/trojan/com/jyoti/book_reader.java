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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class book_reader extends AppCompatActivity implements TextToSpeech.OnInitListener{

    public RelativeLayout rl;
    public int count = 0,read=0;
    public TextView txt0,txt1,txt2;
    private TextToSpeech tts;
    private DatabaseReference mDb;
    static String post;
    static String book_String;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);
        rl = (RelativeLayout)findViewById(R.id.fullscreen5);
        txt0 = (TextView)findViewById(R.id.pt5);
        txt1 = (TextView)findViewById(R.id.pt6);
        txt2 = (TextView)findViewById(R.id.pt7);
        final TextView tvarr[] = new TextView[3];
        tvarr[0] = txt0;
        tvarr[1] = txt1;
        tvarr[2] = txt2;
        mDb = FirebaseDatabase.getInstance().getReference();

        tts = new TextToSpeech(this, this);

        rl.setOnTouchListener(new OnSwipeTouchListener(book_reader.this) {
            public void onSwipeTop() {
                //Toast.makeText(book_reader.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(contacts.this, "right", Toast.LENGTH_SHORT).show();
                //Intent i;
                String data = tvarr[(count-1)%3].getText().toString();
                //Toast.makeText(book_reader.this, data, Toast.LENGTH_SHORT).show();
                //String book_value = mDb.child("books").child(data).get
                //mDb.child("book").setValue("");
                attachDatabaseReadListener(data);
                read++;
                if(read%2==0){
                    speakOut("book's ready on hardware");
                }
            }
            public void onSwipeLeft() {
                //Toast.makeText(book_reader.this, "left", Toast.LENGTH_SHORT).show();
                mDb.child("book").setValue("");
                Intent i2 = new Intent(book_reader.this,MainActivity.class);
                startActivity(i2);
            }
            public void onSwipeBottom() {
                //Toast.makeText(book_reader.this, "bottom"+count, Toast.LENGTH_SHORT).show();
                if(count == 0){
                    tvarr[count].setTextColor(Color.YELLOW);}
                else{
                    tvarr[(count-1)%3].setTextColor(Color.WHITE);
                    tvarr[count%3].setTextColor(Color.YELLOW);
                }
                String data = tvarr[count%3].getText().toString();
                speakOut(data);
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
                speakOut("navigate book by swiping down and select by double right swipe");
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
    public void attachDatabaseReadListener(final String s){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                post = dataSnapshot.child("books").child(s).getValue(String.class);
                //txt1.setText(post);


                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        mDb.child("book").setValue(post);
        mDb.addValueEventListener(postListener);
    }
}
