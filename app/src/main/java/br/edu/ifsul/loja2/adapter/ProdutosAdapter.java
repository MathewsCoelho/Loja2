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
import br.edu.ifsul.loja2.model.Produto;

public class ProdutosAdapter extends ArrayAdapter<Produto> {

    private final Context context;

    public ProdutosAdapter(@NonNull Context context, @NonNull List<Produto> produtos) {
        super(context, 0, produtos);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_produto_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Produto produto = getItem(position);
        holder.tvNome.setText(produto.getNome());
        holder.tvEstoque.setText(produto.getQuantidade().toString());
        holder.tvValor.setText(NumberFormat.getCurrencyInstance().format(produto.getValor()));
        return convertView;
    }

    private class ViewHolder{
        final TextView tvNome;
        final TextView tvEstoque;
        final TextView tvValor;
        final ImageView imvFoto;
        //final ProgressBar pbFotoDoProduto;

        public ViewHolder(View view){
            tvNome = view.findViewById(R.id.tvNomeProdutoAdapter);
            tvEstoque = view.findViewById(R.id.tvEstoqueProdutoAdapater);
            tvValor = view.findViewById(R.id.tvValorProdutoItemAdapter);
            imvFoto = view.findViewById(R.id.imvFotoProdutoAdapter);
            //pbFotoDoProduto = view.findViewById(R.id.pb_foto_produtos_adapter);
        }
    }
}
