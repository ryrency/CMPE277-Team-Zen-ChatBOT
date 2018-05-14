package edu.sjsu.zen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.zen.R;
import edu.sjsu.zen.models.MessageQuery;
import edu.sjsu.zen.models.MessageResponse;
import edu.sjsu.zen.models.Suggestion;
import edu.sjsu.zen.ui.ChatRoom;


public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    private final List<Suggestion> suggestions = new ArrayList<>();
    private final Context context;
    private final LayoutInflater layoutInflater;
    private MessageResponse messageResponse;

    SuggestionsAdapter(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(MessageResponse messageResponse) {
        this.messageResponse = messageResponse;
        this.suggestions.clear();
        this.suggestions.addAll(messageResponse.getValidSuggestions());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View suggestionsView = layoutInflater.inflate(R.layout.suggestion_content_layout, null,false);
        return new ViewHolder(suggestionsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Suggestion suggestion = suggestions.get(position);
        viewHolder.bindData(suggestion);
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView suggestionText;
        private final ImageView suggestionIcon;

        ViewHolder(View suggestionsView){
            super(suggestionsView);
            suggestionIcon = (ImageView) suggestionsView.findViewById(R.id.suggestion_icons);
            suggestionText = (TextView)suggestionsView.findViewById(R.id.suggestion_tv);
        }

        void bindData(final Suggestion suggestion) {
            suggestionText.setText(suggestion.getName());
            if (suggestion.getIconResId() == 0) {
                suggestionIcon.setVisibility(View.GONE);
            } else {
                suggestionIcon.setImageResource(suggestion.getIconResId());
                suggestionIcon.setVisibility(View.VISIBLE);
            }

            suggestionText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context instanceof ChatRoom) {
                        ((ChatRoom)context).onSuggestionClicked(suggestion, messageResponse);
                    }
                }
            });
        }
    }
}


