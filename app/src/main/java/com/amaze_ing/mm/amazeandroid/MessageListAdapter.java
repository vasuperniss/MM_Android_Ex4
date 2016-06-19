package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Random;

import java.util.List;

/**
 * Created by Max on 19/06/2016.
 */
public class MessageListAdapter extends ArrayAdapter {

    private Context mContext;
    private int id;
    private List<Message> items ;

    public MessageListAdapter(Context context, int textViewResourceId , List<Message> list )
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        Message message = items.get(position);

        if(message != null )
        {
            TextView messageContent = (TextView) mView.findViewById(R.id.message_content);
            TextView messageSender = (TextView) mView.findViewById(R.id.message_sender);
            TextView messageTime = (TextView) mView.findViewById(R.id.message_time);

            messageContent.setTextColor(Color.BLACK);
            messageSender.setTextColor(generateRandomColor());
            messageTime.setTextColor(Color.GRAY);

            messageContent.setText(message.getContent());
            messageSender.setText(message.getSender());
            messageTime.setText(message.getTime());

            int color = Color.argb( 255, 245, 245, 245 );
            messageContent.setBackgroundColor( color );
            messageSender.setBackgroundColor( color );
            messageTime.setBackgroundColor( color );

        }
        return mView;
    }

    private int generateRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
