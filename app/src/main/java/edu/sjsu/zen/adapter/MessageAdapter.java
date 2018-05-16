package edu.sjsu.zen.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.sjsu.zen.models.Category;
import edu.sjsu.zen.models.MessageQuery;
import edu.sjsu.zen.models.MessageResponse;
import edu.sjsu.zen.R;
import edu.sjsu.zen.utils.ScreenUtils;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = MessageAdapter.class.getSimpleName();
    private final int suggestionItemSpacing;
    final ArrayList<Object> suggestions = new ArrayList<>();
    private final ArrayList<Object> messagesList;
    //private final int USER1 = 0, USER2 = 1;
    private final Context context;
    private final LayoutInflater layoutInflater;
    public MessageAdapter(ArrayList<Object> messagesList,Context context){
        this.messagesList = messagesList;
        this.context = context;
        suggestionItemSpacing = ScreenUtils.convertDpToPixel(8, context);
        layoutInflater = LayoutInflater.from(context);
        Log.i(TAG,"INTO ADATPER");

    }

    @Override
    public int getItemCount(){
        Log.i(TAG,"SIZE "+String.valueOf(messagesList.size()));
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position){
        Log.d(TAG, "");
        if(messagesList.get(position) instanceof MessageQuery){
            return 0;
        } else if (messagesList.get(position) instanceof MessageResponse) {
            Category category = ((MessageResponse) messagesList.get(position)).getCategory();
            if(category == Category.INSTRUCTOR_CONTACT)
                {
                return 2;
            }
            else  {
                return 1;
            }
        }

        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        switch (viewType) {
            case 0:
            // Inflate the custom layout
                View userMessageView = layoutInflater.inflate
                        (R.layout.item_message_user,
                                viewGroup, false);
            //ViewHolder viewHolder1 = new ViewHolder1(userMessageView);
                return new ViewHolder1(userMessageView);

            case 1:
                View chatbotMessageView = layoutInflater.inflate
                        (R.layout.item_message_chatbot,
                                viewGroup, false);
                return new ViewHolder2(chatbotMessageView);

            case 2:
                View instructorContactView =  layoutInflater.inflate
                    (R.layout.contact,
                            viewGroup, false);
                return new ViewHolder3(instructorContactView);
    }
    return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        switch(viewHolder.getItemViewType()){
            case 0:
                ViewHolder1 vh1 = (ViewHolder1)viewHolder;
                MessageQuery queryFromUser = (MessageQuery) messagesList.get(position);
                vh1.bindData(queryFromUser.getQuery());
                break;
            case 1:
                ViewHolder2 vh2 = (ViewHolder2)viewHolder;
                Log.i(TAG, String.valueOf(position));
                MessageResponse responseFromChatBot = (MessageResponse) messagesList.get(position);
                vh2.bindData(responseFromChatBot);
                break;
            case 2:
                ViewHolder3 vh3 = (ViewHolder3)viewHolder;
                MessageResponse contactInResponse = (MessageResponse) messagesList.get(position);
                vh3.bindData(contactInResponse);
                break;
        }
    }

    public class ViewHolder1 extends ViewHolder{
        private TextView messageText;

        private ViewHolder1(View itemView){
            super(itemView);
            Log.i(TAG,"creating view holder");
            messageText = (TextView) itemView.findViewById(R.id.user_text_message_body);
        }

        public void bindData(String userText) {
            messageText.setText(userText);
        }
    }

    public class ViewHolder2 extends ViewHolder{
        private ImageView chatbotImage;
        private TextView messageText;
        private RecyclerView suggestionRecyclerView;
        private SuggestionsAdapter suggestionsAdapter;


        ViewHolder2(View itemView){
            super(itemView);
            Log.i(TAG,"creating view holder");
            chatbotImage = (ImageView) itemView.findViewById(R.id.chatbot_imageView);
            messageText = (TextView) itemView.findViewById(R.id.chatbot_text_message_body);
            suggestionRecyclerView = (RecyclerView)itemView.findViewById(R.id.reyclerview_suggestion_list);
            suggestionRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            suggestionsAdapter = new SuggestionsAdapter(context);
            setSpaceBetweenSuggestions();
            suggestionRecyclerView.setAdapter(suggestionsAdapter);
        }

        private void setSpaceBetweenSuggestions() {
            RecyclerView.ItemDecoration decoration = new ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                    outRect.right = suggestionItemSpacing;
                }
            };

            suggestionRecyclerView.addItemDecoration(decoration);
        }

        void bindData(MessageResponse messageResponse) {
            if (messageResponse.getCategory() == Category.UNKNOWN) {
                messageText.setText("Sorry I don't understand, but I am learning!");
                suggestionRecyclerView.setVisibility(GONE);
            } else {
                suggestionRecyclerView.setVisibility(VISIBLE);
                messageText.setText(messageResponse.getDisplayText());
                suggestionsAdapter.setData(messageResponse);
                suggestionsAdapter.notifyDataSetChanged();
            }
        }
    }

    public class ViewHolder3 extends ViewHolder{
        private ImageView emailImageView;
        private ImageView phnImageView;
        private TextView emailAdressTextView;
        private TextView phnNoTextView;
        private RecyclerView suggestionsRecyclerView;
        private SuggestionsAdapter suggestionAdapter;
        private  LinearLayoutManager layoutManager = new LinearLayoutManager(context);

        ViewHolder3(View itemView){
            super(itemView);
            phnImageView = (ImageView) itemView.findViewById(R.id.phn_icon);
            emailImageView = (ImageView)itemView.findViewById(R.id.email_icon);
            phnNoTextView = (TextView) itemView.findViewById(R.id.phn_no);
            emailAdressTextView = (TextView)itemView.findViewById(R.id.email_address);
            suggestionsRecyclerView = (RecyclerView)itemView.findViewById(R.id.reyclerview_suggestion_list);

            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            suggestionsRecyclerView.setLayoutManager(layoutManager);
            //suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            suggestionAdapter = new SuggestionsAdapter(context);
            setSpaceBetweenSuggestions();
            suggestionsRecyclerView.setAdapter(suggestionAdapter);
        }

        private void setSpaceBetweenSuggestions() {
            RecyclerView.ItemDecoration decoration = new ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                    outRect.right = suggestionItemSpacing;
                }
            };

            suggestionsRecyclerView.addItemDecoration(decoration);
        }

        void bindData(MessageResponse messageResponse) {
            emailAdressTextView.setText(messageResponse.getDisplayEmailAddress());
            phnNoTextView.setText(messageResponse.getDisplayPhnNo());
            suggestionAdapter.setData(messageResponse);
            suggestionAdapter.notifyDataSetChanged();

        }

    }



}

