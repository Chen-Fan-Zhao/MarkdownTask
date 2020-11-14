package com.example.markdowntestjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MarkdownText extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "raw_markdown";

    @Override
    /* onCreate is subclassed to reload the text from disk after the app has been unloaded */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown_text);
        // get the markdown text box object and set the text to what was saved in SharedPreferences
        EditText editMarkdownText = (EditText) findViewById(R.id.edit_markdown_text);
        SharedPreferences sharedPref = MarkdownText.this.getPreferences(Context.MODE_PRIVATE);
        editMarkdownText.setText(sharedPref.getString("rawMarkdown",""));
    }

    /* this method is linked to a button listener which sends the markdown to another activity and saves the markdown to disk*/
    public void formatText(View view) {
        // get SharedPreferences to save into
        SharedPreferences sharedPref = MarkdownText.this.getPreferences(Context.MODE_PRIVATE);
        EditText editMarkdownText = (EditText) findViewById(R.id.edit_markdown_text);

        // get the raw markdown from to text box
        String rawMarkdown = editMarkdownText.getText().toString();

        // save the raw markdown
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("rawMarkdown", rawMarkdown);
        editor.apply();

        // send the raw markdown to another activity to parse and display
        Intent intent = new Intent(this, FormattedText.class);
        intent.putExtra(EXTRA_MESSAGE, rawMarkdown);
        startActivity(intent);

    }
}

