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

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.zen.R;

/**
 * Created by sajujoseph on 4/30/18.
 */

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {

    String category;
    Context mContext;
    private List<String> mSuggestions;

    final ArrayList<Object> suggestions = new ArrayList<>();


    public SuggestionsAdapter(String category, Context context){
        this.category = category;
        this.mContext = context;
        switch(category){
            case "instructor":
                suggestions.add("Name");
                suggestions.add("Office Hours");
                suggestions.add("Location");
                suggestions.add("xyz");
                suggestions.add("hjhhhh");
                break;
            default:
                break;
        }
        Log.i("RENCY","INTO ADATPER");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View suggestionsView = inflater.inflate(R.layout.suggestion_content_layout, null,false);
        viewHolder = new ViewHolder(suggestionsView);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ViewHolder vh = (ViewHolder)viewHolder;
        configureViewHolder(vh,position);


    }
    private void configureViewHolder(ViewHolder viewHolder, int position){

        String sugg = (String) suggestions.get(position);

        TextView tv1, tv2;

        tv1 = viewHolder.suggestionText;
        tv1.setText(sugg);




    }


    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView suggestionText;
        public ImageButton suggestionButton;

        public ViewHolder(View suggestionsView){
            super(suggestionsView);

            suggestionsView.findViewById(R.id.reyclerview_suggestion_list);

            Log.i("RENCY","creating view holder");
            suggestionText = (TextView)suggestionsView.findViewById(R.id.suggestion_tv);
            suggestionButton = (ImageButton)suggestionsView.findViewById(R.id.suggestion_img_btn);
        }

    }
}
