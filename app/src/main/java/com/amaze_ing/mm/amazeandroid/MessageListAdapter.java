package com.amaze_ing.mm.amazeandroid;
/**
 * exe 4
 * @author Michael Vassernis 319582888 vaserm3
 * @author Max Anisimov 322068487 anisimm
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

import java.util.List;

/**
 * The message list adapter.
 */
public class MessageListAdapter extends ArrayAdapter {

    private Context mContext;
    private int id;
    private List<Message> items ;

    /**
     * Instantiates a new Message list adapter.
     *
     * @param context            the context
     * @param textViewResourceId the text view resource id
     * @param list               the list
     */
    public MessageListAdapter(Context context, int textViewResourceId , List<Message> list )
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
    }

    /**
     * Gets the view.
     * @param position view position in list.
     * @param v the view.
     * @param parent view group.
     * @return the view in the given position in the list.
     */
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

    /**
     * generate a random dark color.
     * @return the color.
     */
    private int generateRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(185), rnd.nextInt(185), rnd.nextInt(185));
    }

    /**
     * sets the imageView to a have an image.
     * @param imageView the users image in the message.
     * @param imageName the image's name.
     */
    private void setSenderImage(ImageView imageView, String imageName){
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier(imageName, "drawable",
                                                        context.getPackageName());
        imageView.setImageResource(id);
    }
}
