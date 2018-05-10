package edu.sjsu.zen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.sjsu.zen.R;
import edu.sjsu.zen.models.Category;
import edu.sjsu.zen.models.MessageQuery;
import edu.sjsu.zen.models.Suggestion;
import edu.sjsu.zen.ui.ChatRoom;


public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    private List<Suggestion> suggestions;
    private Context context;
    private List<String> mSuggestions;
    private RecyclerView mRecyclerView;



    SuggestionsAdapter(List<Suggestion> suggestions, Context context){
        this.suggestions = suggestions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View suggestionsView = inflater.inflate(R.layout.suggestion_content_layout, null,false);
        viewHolder = new ViewHolder(suggestionsView);

        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ViewHolder vh = (ViewHolder)viewHolder;
        configureViewHolder(vh,position);
        Toast.makeText(context, String.valueOf(suggestions.get(position)), Toast.LENGTH_SHORT).show();
        vh.setItem(String.valueOf(suggestions.get(position)));
    }

    private void configureViewHolder(ViewHolder viewHolder, int position){

        Suggestion suggestion = suggestions.get(position);
        TextView suggestionTextView;
        suggestionTextView = viewHolder.suggestionText;
        suggestionTextView.setText(suggestion.getName());
    }




    @Override
    public int getItemCount() {
        return suggestions.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView suggestionText;
        public ImageButton suggestionButton;
        String category_selected;

        public ViewHolder(View suggestionsView){
            super(suggestionsView);

            mRecyclerView = suggestionsView.findViewById(R.id.reyclerview_suggestion_list);
            suggestionText = (TextView)suggestionsView.findViewById(R.id.suggestion_tv);

            //suggestionButton = (ImageButton)suggestionsView.findViewById(R.id.suggestion_img_btn);
            suggestionsView.setTag(suggestionsView);
            Toast.makeText(context, "onClick called", Toast.LENGTH_SHORT).show();

            suggestionText.setOnClickListener(this); // bind the listener

        }

        public void setItem(String item) {
            category_selected = item;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "onClick "+ category_selected, Toast.LENGTH_SHORT).show();
            MessageQuery mq = new MessageQuery(category_selected);
            if (context instanceof ChatRoom)
                ((ChatRoom)context).messageFromUser(category_selected.replace("_"," "));

        }
        }
    }


