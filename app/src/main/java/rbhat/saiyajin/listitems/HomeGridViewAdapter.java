package rbhat.saiyajin.listitems;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import rbhat.saiyajinlog.R;

/**
 * Created by rbhat on 3/1/15.
 */
public class HomeGridViewAdapter extends BaseAdapter {

    public static final String[] HOME_ITEMS = new String[] {
      "New", "Show", "Browse", "Charts"
    };

    private Context context;
    public HomeGridViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return HOME_ITEMS.length;
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

        Button button = new Button(this.context);
        button.setText(HOME_ITEMS[position]);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.activity_label_textsize));
        button.setFocusable(false);
        button.setFocusableInTouchMode(false);
        button.setClickable(false);
        button.setLayoutParams(new GridView.LayoutParams(200, 200));


        return button;

    }
}
