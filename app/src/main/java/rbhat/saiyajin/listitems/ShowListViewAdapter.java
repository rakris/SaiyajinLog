package rbhat.saiyajin.listitems;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rbhat.saiyajin.db.ShowExerData;
import rbhat.saiyajinlog.R;

/**
 * Created by rbhat on 9/1/15.
 */
public class ShowListViewAdapter extends BaseAdapter {

    private Activity parentActivity = null;
    private List<ShowExerData> showExers = null;
    public ShowListViewAdapter(Activity parentActivity, List<ShowExerData> showExers) {
        this.parentActivity = parentActivity;
        this.showExers = showExers;
    }

    public void setShowExers(List<ShowExerData> showExers) {
        this.showExers = showExers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(showExers != null)
            return showExers.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShowExerData data = this.showExers.get(position);

        View view = parentActivity.getLayoutInflater().inflate(R.layout.layout_listview_item, null);
        TextView dateText = (TextView) view.findViewById(R.id.showitem_date);
        dateText.setText(data.dateString);
        TextView weightText = (TextView) view.findViewById(R.id.showitem_weight);
        weightText.setText(String.valueOf(data.weight)+" lbs");
        TextView repsText = (TextView) view.findViewById(R.id.showitem_reps);
        repsText.setText(String.valueOf(data.reps)+" reps");

        return view;
    }
}
