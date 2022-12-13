package ctorcru.upv.techcommit_3a.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ctorcru.upv.techcommit_3a.R;
/**
 * @brief Esta clase se encarga de montar el objeto que generará el recyclerview de la activity Mi_Perfil
 * Autor: Enrique Ferre Carbonell
 * Archivo: RecyclerAdapter.java
 * Para mas info acerca de esta clase:
 * https://developer.android.com/guide/topics/ui/layout/recyclerview?gclid=CjwKCAiAv9ucBhBXEiwA6N8nYJvaTzcFOJJ3kVN4flgx27HHJtVKOjNJyyU0wTh1R5LgXW98t3L5tBoCiSwQAvD_BwE&gclsrc=aw.ds
 **/
public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //Atributos
    private static final int TYPE =1;
    private final Context context;
    private final List<Object> listRecyclerItem;

    //el constructor de la clase
    public RecyclerAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    //aqui es donde se asignan los parametros del recyclerview
    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView name;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.nombresensor);
        }
    }

    //creamos las nuevas vistas
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE:
            default:
                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_item, viewGroup,false);
                return new ItemViewHolder(layoutView);
        }

    }
    //reemplazamos los contenidos de la vista
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, int i) {
        int viewType = getItemViewType(i);
        switch (viewType){
            case TYPE:
            default:
                ItemViewHolder itemViewHolder= (ItemViewHolder) viewholder;
                Sensores sensores=(Sensores) listRecyclerItem.get(i);
                itemViewHolder.name.setText(sensores.getNombre());
        }
    }
    // Devolvemos el tamanyo del dataset
    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
