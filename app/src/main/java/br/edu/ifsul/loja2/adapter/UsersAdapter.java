package br.edu.ifsul.loja2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsul.loja2.R;
import br.edu.ifsul.loja2.model.User;

public class UsersAdapter extends ArrayAdapter<User> {

    private final Context context;

    public UsersAdapter(@NonNull Context context, @NonNull List<User> users){
        super(context, 0, users);
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.user_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        holder.tvNome.setText(user.getNome().concat(" " + user.getSobrenome()));
        holder.tvFuncaoUser.setText(user.getFuncao());
        holder.tvProgressBar.setVisibility(View.GONE);

        return convertView;
    }

    private class ViewHolder{
        final TextView tvNome;
        final TextView tvFuncaoUser;
        final ProgressBar tvProgressBar;

        public ViewHolder(View view){
            tvNome = view.findViewById(R.id.tvNomeUser);
            tvFuncaoUser = view.findViewById(R.id.tvFuncaoUser);
            tvProgressBar = view.findViewById(R.id.pb_foto_user);
        }
    }

}