package rbhat.saiyajin.listitems;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Set;

import rbhat.saiyajin.db.BrowseListData;
import rbhat.saiyajin.db.ExerSet;
import rbhat.saiyajinlog.R;

/**
 * Created by rbhat on 15/1/15.
 */
public class BrowseListViewAdapter extends BaseAdapter {

    private Activity parentActivity;
    private List<BrowseListData> workouts;
    public BrowseListViewAdapter(Activity parentActivity, List<BrowseListData> workouts) {
        this.parentActivity = parentActivity;
        this.workouts = workouts;
    }

    public void onDateWorkoutChanged(List<BrowseListData> workouts) {
        this.workouts = workouts;
        notifyDataSetChanged();;
    }

    @Override
    public int getCount() {
        if(workouts != null)
            return workouts.size();
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

        View listitem = parentActivity.getLayoutInflater().inflate(R.layout.layout_browse_item, null);
        TextView browseName = (TextView) listitem.findViewById(R.id.browse_exer_name);
        TextView workoutsText = (TextView) listitem.findViewById(R.id.browse_workouts_text);

        BrowseListData listData = workouts.get(position);


        browseName.setText(listData.name);
        List<ExerSet> exerSets = listData.exerSets;
        StringBuilder setBuilder = new StringBuilder();
        for(int i = 0; i<exerSets.size(); i++) {
            ExerSet exerSet = exerSets.get(i);
            setBuilder.append(exerSet.weight);
            setBuilder.append("(");
            setBuilder.append(exerSet.reps);
            setBuilder.append(")");
            if(i != exerSets.size()-1)
                setBuilder.append(", ");
        }

        workoutsText.setText(setBuilder.toString());


        return listitem;

    }
}
