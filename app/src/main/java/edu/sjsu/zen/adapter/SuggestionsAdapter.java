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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.zen.R;
import edu.sjsu.zen.models.MessageQuery;
import edu.sjsu.zen.models.Suggestion;
import edu.sjsu.zen.ui.ChatRoom;


public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    private final List<Suggestion> suggestions = new ArrayList<>();
    private final Context context;
    private final LayoutInflater layoutInflater;

    SuggestionsAdapter(Context context){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions.clear();
        this.suggestions.addAll(suggestions);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View suggestionsView = layoutInflater.inflate(R.layout.suggestion_content_layout, null,false);
        return new ViewHolder(suggestionsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        configureViewHolder(viewHolder,position);
        viewHolder.setItem(suggestions.get(position).getName());
    }

    private void configureViewHolder(ViewHolder viewHolder, int position){
        try {
            Suggestion suggestion = suggestions.get(position);
            TextView suggestionTextView;
            suggestionTextView = viewHolder.suggestionText;
            suggestionTextView.setText(suggestion.getName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView suggestionText;
        private String categorySelected;

        ViewHolder(View suggestionsView){
            super(suggestionsView);
            suggestionText = (TextView)suggestionsView.findViewById(R.id.suggestion_tv);
            suggestionText.setOnClickListener(this);
        }

        void setItem(String item) {
            categorySelected = item;
        }
        private String getItem() {
            return categorySelected;
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(context, "onClick "+ categorySelected, Toast.LENGTH_SHORT).show();
            if (context instanceof ChatRoom) {
                ((ChatRoom) context).messageFromUser(categorySelected);

            }
        }
    }
}


