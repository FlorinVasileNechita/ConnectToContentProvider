package domain.connecttocontentprovider;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import domain.connecttocontentprovider.model.Note;


public class MainActivity extends FragmentActivity {

    private static final String AUTHORITY = "training.exercise_week1.contentProvider";

    private static final String BASE_PATH = "notes";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    ContentProviderClient contentProviderClient;

    String[] projection = {"_id", "subject", "content"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentProviderClient = getContentResolver().acquireContentProviderClient(CONTENT_URI);

        Button log = (Button) findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(MainActivity.class.getName(), "start");
                getAllNotes();
                Log.v(MainActivity.class.getName(), "end");
            }
        });

        final EditText subjectEditText = (EditText) findViewById(R.id.subjectEditText);
        final EditText contentEditText = (EditText) findViewById(R.id.contentEditText);

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subjectEditText.getText().toString().isEmpty() || contentEditText.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Subject or Content - empty", Toast.LENGTH_LONG).show();
                } else {
                    insertNote(new Note(subjectEditText.getText().toString(), contentEditText.getText().toString()));
                }
            }
        });

    }

    private void getAllNotes() {
        Cursor cursor;
        try {
            cursor = contentProviderClient.query(CONTENT_URI, projection, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                Note receivedNote;
                Log.v(MainActivity.class.getName(), "_______NOTES TABLE_______");
                while (cursor.moveToNext()) {
                    receivedNote = new Note(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
                    Log.v(MainActivity.class.getName(), receivedNote.getId() + " " + receivedNote.getSubject() + " " + receivedNote.getContent());
                }
                cursor.close();
                Log.v(MainActivity.class.getName(), "_________________________");
            } else {
                Log.v(MainActivity.class.getName(), "Cursor is null or empty");
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.getName(), e.toString());
        }

    }

    private void insertNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("subject", note.getSubject());
        contentValues.put("content", note.getContent());
        try {
            contentProviderClient.insert(CONTENT_URI, contentValues);
        } catch (Exception e) {
            Log.e(MainActivity.class.getName(), e.toString());
        }
    }


}
