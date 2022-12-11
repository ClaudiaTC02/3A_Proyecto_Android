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

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE =1;
    private final Context context;
    private final List<Object> listRecyclerItem;

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

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
