package com.fmu.lgbth.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.UsefullyPhone;

import java.util.List;

public class UsefullyPhonesAdapter extends BaseAdapter {
    private final List<UsefullyPhone> usefullyPhoneList;
    private final Context context;

    public UsefullyPhonesAdapter(List<UsefullyPhone> usefullyPhonesGiven, Context contextGiven) {
        this.context = contextGiven;
        this.usefullyPhoneList = usefullyPhonesGiven;
    }

    @Override
    public int getCount() {
        return usefullyPhoneList.size();
    }

    @Override
    public Object getItem(int position) {
        return usefullyPhoneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View aView = LayoutInflater.from(context).inflate(R.layout.phone_card, parent, false);
        UsefullyPhone uPhone = usefullyPhoneList.get(position);

        TextView title = aView.findViewById(R.id.phone_card_title);
        title.setText(uPhone.getTitle());

        TextView number = aView.findViewById(R.id.phone_card_phone_number);
        number.setText(uPhone.getNumber());

        TextView description = aView.findViewById(R.id.phone_card_description);
        description.setText(uPhone.getDescription());

        return aView;
    }
}
