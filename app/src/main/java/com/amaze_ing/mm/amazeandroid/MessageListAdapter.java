package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
            // get fields
            TextView messageContent = (TextView) mView.findViewById(R.id.message_content);
            ImageView messageSenderImage = (ImageView) mView.findViewById(R.id.message_sender_image);
            TextView messageSenderName = (TextView) mView.findViewById(R.id.message_sender_name);
            TextView messageTime = (TextView) mView.findViewById(R.id.message_time);

            // set text color for content, time and username
            messageContent.setTextColor(Color.BLACK);
            messageTime.setTextColor(Color.GRAY);
            messageSenderName.setTextColor(this.generateRandomColor());

            // set text and image
            messageContent.setText(message.getContent());
            this.setSenderImage(messageSenderImage, message.getImage());
            messageSenderName.setText(message.getSender());
            messageTime.setText(message.getTime());

            // set background color for text fields
            int color = Color.argb( 255, 245, 245, 245 );
            messageContent.setBackgroundColor( color );
            messageSenderName.setBackgroundColor( color );
            messageTime.setBackgroundColor( color );
            messageSenderImage.setBackgroundColor( color );
        }
        return mView;
    }

    private int generateRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void setSenderImage(ImageView imageView, String imageName){
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(imageName, "drawable",
                                                        context.getPackageName());
        imageView.setImageResource(id);
    }
}
