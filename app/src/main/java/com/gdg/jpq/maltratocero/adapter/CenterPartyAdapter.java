package com.gdg.jpq.maltratocero.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.gdg.jpq.maltratocero.R;
import com.gdg.jpq.maltratocero.model.CenterParty;

public class CenterPartyAdapter extends ArrayAdapter<CenterParty>{
    private final Activity context;
    private final List<CenterParty> centros;

    static class ViewHolder {
        public TextView nombre;
        public TextView tipoinstitucion;
        public TextView direccion;
    }

    public CenterPartyAdapter(Activity context, List<CenterParty> data) {
        super(context, R.layout.centerparty_element_list, data);
        this.context = context;
        this.centros = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.centerparty_element_list, parent, false);
            //configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.nombre = (TextView) rowView.findViewById(R.id.text_view_centerPartyName);
            viewHolder.tipoinstitucion = (TextView) rowView.findViewById(R.id.text_view_centerPartyType);
            viewHolder.direccion = (TextView) rowView.findViewById(R.id.text_view_centerPartyAddress);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        CenterParty c = centros.get(position);
        holder.nombre.setText(c.getNombre());
        holder.tipoinstitucion.setText(c.getTipoinstitucion());
        holder.direccion.setText(c.getDireccion());
        return rowView;

    }
}