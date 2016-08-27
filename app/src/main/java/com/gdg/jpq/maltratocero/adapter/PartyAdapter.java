package com.gdg.jpq.maltratocero.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import com.gdg.jpq.maltratocero.R;
import com.gdg.jpq.maltratocero.model.Party;

public class PartyAdapter extends ArrayAdapter<Party> {
    private final Activity context;
    private final List<Party> Parties;

    static class ViewHolder{
        public TextView nombre;
    }

    public PartyAdapter(Activity context, List<Party> data) {
        super(context, R.layout.party_element_list, data);
        this.context = context;
        this.Parties = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = convertView;
        // reuse views
        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.party_element_list, null);
            //configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.nombre = (TextView) rowView.findViewById(R.id.text_view_partyName);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Party p = Parties.get(position);
        holder.nombre.setText(p.getNombre());

        return rowView;

    }
}
