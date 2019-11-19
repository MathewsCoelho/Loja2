package br.edu.ifsul.loja2.setup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja2.model.Cliente;
import br.edu.ifsul.loja2.model.ItemPedido;
import br.edu.ifsul.loja2.model.Produto;
import br.edu.ifsul.loja2.model.User;

public class AppSetup {
    public static User user = null;
    public static List<Produto> listProdutos = new ArrayList<>();
    public static List<Cliente> listClientes = new ArrayList<>();
    public static Cliente cliente = null;
    public static ArrayList<ItemPedido> carrinho = new ArrayList<>();
}
