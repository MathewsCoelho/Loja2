package br.edu.ifsul.loja2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.model.Cliente;

public class ClientesAdapter extends ArrayAdapter<Cliente> {

    private final Context context;

    public ClientesAdapter(@NonNull Context context, @NonNull List<Cliente> clientes) {
        super(context, 0, clientes);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.cliente_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Cliente cliente = getItem(position);
        holder.tvNome.setText(cliente.getNome().concat(" ").concat(cliente.getSobrenome()));
        holder.tvCpf.setText(cliente.getCpf());
        return convertView;
    }

    private class ViewHolder{
        final TextView tvNome;
        final TextView tvCpf;
        final ImageView imvFoto;
        //final ProgressBar pbFotoDoCliente;

        public ViewHolder(View view){
            tvNome = view.findViewById(R.id.tvNomeClienteAdapter);
            tvCpf = view.findViewById(R.id.tvCpfAdapter);
            imvFoto = view.findViewById(R.id.imvFotoClienteAdapter);
        }
    }
}
