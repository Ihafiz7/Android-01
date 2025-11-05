package com.hafiz.android_01.dbUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hafiz.android_01.R;
import com.hafiz.android_01.entity.Product;
import com.hafiz.android_01.ui.ProductAddActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    private ProductUti productUti;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.productUti = new ProductUti(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.txtName.setText(product.getName());
        holder.txtEmail.setText(product.getEmail());
        holder.txtPrice.setText("Price: " +product.getPrice());
        holder.txtQuantity.setText("Quntity: " + product.getQuantity());

        if (product.getImage() != null && !product.getImage().isEmpty()){
            holder.imgProduct.setImageURI(Uri.parse(product.getImage()));
        }else {
            holder.imgProduct.setImageResource(R.drawable.images);
        }

        holder.btnDelete.setOnClickListener(v -> {
            int rows = productUti.delete(product.getId());
            if(rows > 0){
                productList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, productList.size());
                Toast.makeText(context, "Product delete!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Product delete failed!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductAddActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtEmail, txtPrice,txtQuantity;
        ImageView imgProduct;
        ImageButton btnDelete, btnEdit;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}


