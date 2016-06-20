package com.amaze_ing.mm.amazeandroid;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {
    private EditText messageField;
    private ListView messageListView;
    private MessageListAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        this.messageField = (EditText) findViewById(R.id.message_text);
        this.messageListView = (ListView) findViewById(R.id.message_list);
        initMessageList();

        // set up on click
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_send_message);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sendMessage(v);
            }
        });
    }

    public void sendMessage(View view){
        // get message content
        String messageContent = this.messageField.getText().toString();

        if(!messageContent.isEmpty()){
            // get message sender
            String messageSender = getResources().getString(R.string.current_user_name);
            String messageTime;

            // get time
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            messageTime = sdf.format(new Date());

            // TODO: save user pic
            this.messageList.add(new Message(messageContent, messageSender, 1, messageTime));
            this.messageAdapter.notifyDataSetChanged();
            this.messageField.setText("");

            // send message to server
        }
    }

    private void initMessageList(){
        this.messageList = new ArrayList<Message>();

        // fetch messages from server
        this.messageList.add(new Message("Hello","Devi",1,"9:30"));
        this.messageList.add(new Message("Greetings","Kvothe",2,"15:21"));
        this.messageList.add(new Message("Hello there","Simmon",3,"16:59"));
        this.messageList.add(new Message("Sup","Dan",4,"18:14"));

        this.messageAdapter = new MessageListAdapter(MessagingActivity.this, R.layout.message_list_item,
                                                                    this.messageList);
        this.messageListView.setAdapter(this.messageAdapter);
    }
}
