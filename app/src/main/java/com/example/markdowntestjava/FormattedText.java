package com.example.markdowntestjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class FormattedText extends AppCompatActivity {

    @Override
    /* to create the set location markup I have opted to use 9 hardcoded floating text boxes,
    I could have done something more clever and dynamic, but as the task set out that only these 9
    will be used, hardcoding this was simpler and faster.  */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formatted_text);

        // get the raw markdown from the previous activity
        Intent intent = getIntent();
        String rawMarkdown = intent.getStringExtra(MarkdownText.EXTRA_MESSAGE);
        // split off all the locations then add a 0,0 default location
        String[] splitLocation = splitLocation("<0,0>"+rawMarkdown);

        // hardcoded set text for all the TextViews
        TextView viewFormattedText0_0 = (TextView) findViewById(R.id.view_formatted_text_0_0);
        TextView viewFormattedText0_0_5 = (TextView) findViewById(R.id.view_formatted_text_0_0_5);
        TextView viewFormattedText0_1 = (TextView) findViewById(R.id.view_formatted_text_0_1);
        TextView viewFormattedText0_5_0 = (TextView) findViewById(R.id.view_formatted_text_0_5_0);
        TextView viewFormattedText0_5_0_5 = (TextView) findViewById(R.id.view_formatted_text_0_5_0_5);
        TextView viewFormattedText0_5_1 = (TextView) findViewById(R.id.view_formatted_text_0_5_1);
        TextView viewFormattedText1_0 = (TextView) findViewById(R.id.view_formatted_text_1_0);
        TextView viewFormattedText1_0_5 = (TextView) findViewById(R.id.view_formatted_text_1_0_5);
        TextView viewFormattedText1_1 = (TextView) findViewById(R.id.view_formatted_text_1_1);

        // To handle all the other markdown formats we use html
        viewFormattedText0_0.setText(Html.fromHtml(formatMarkdown(splitLocation[0])));
        viewFormattedText0_0_5.setText(Html.fromHtml(formatMarkdown(splitLocation[1])));
        viewFormattedText0_1.setText(Html.fromHtml(formatMarkdown(splitLocation[2])));
        viewFormattedText0_5_0.setText(Html.fromHtml(formatMarkdown(splitLocation[3])));
        viewFormattedText0_5_0_5.setText(Html.fromHtml(formatMarkdown(splitLocation[4])));
        viewFormattedText0_5_1.setText(Html.fromHtml(formatMarkdown(splitLocation[5])));
        viewFormattedText1_0.setText(Html.fromHtml(formatMarkdown(splitLocation[6])));
        viewFormattedText1_0_5.setText(Html.fromHtml(formatMarkdown(splitLocation[7])));
        viewFormattedText1_1.setText(Html.fromHtml(formatMarkdown(splitLocation[8])));

    }

    /* splitLocation takes a markdown string, and splits off <x,y> tags to the correct place*/
    private String[] splitLocation(String rawMarkdown) {
        String[] stringLocations = {"","","","","","","","",""};
        // using a non-capturing regex group we split the string along the tags while keeping delimiters
        String[] splitStrings = rawMarkdown.split("(?=<(0|0\\.5|1),(0|0\\.5|1)>)");

        // then for each split string
        for (String str : splitStrings){
            // we find what x tag it was
            int index = 0;
            if(str.substring(0, 3).equals("<0,"));
            else if(str.substring(0, 3).equals("<1,")){
                index += 2;
            }
            else index += 1;

            // and find what y tag it was
            int firstComma = str.indexOf(',');
            if(str.substring(firstComma, firstComma+3).equals(",0>"));
            else if(str.substring(firstComma, firstComma+3).equals(",1>")){
                index += 6;
            }
            else index += 3;

            // then sort it accordingly
            stringLocations[index] = stringLocations[index] + str.substring(str.indexOf(">")+1);
        }

        return stringLocations;
    }

    /* formatMarkdown takes markdown strings and returns html tags in place of the markdown ones*/
    private String formatMarkdown(String rawMarkdown) {

        boolean boldParity = true;

        // very inefficient as as this rescans text known to not have markdown, but simple to program.
        while(rawMarkdown.contains("**")){
            if (boldParity){
                rawMarkdown = rawMarkdown.replaceFirst("\\*\\*","<b>");
                boldParity = false;
            }
            else{
                rawMarkdown = rawMarkdown.replaceFirst("\\*\\*","</b>");
                boldParity = true;
            }
        }
        // note: we only need to convert bold and italics as line breaks (<br>) are already html compliant
        boolean italicParity = true;
        while(rawMarkdown.contains("_")){
            if (italicParity){
                rawMarkdown = rawMarkdown.replaceFirst("_","<i>");
                italicParity = false;
            }
            else{
                rawMarkdown = rawMarkdown.replaceFirst("_","</i>");
                italicParity = true;
            }
        }
        return rawMarkdown;
    }

    /* simple exit method for the edit text button*/
    public void editText(View view) {
        finish();
    }
}