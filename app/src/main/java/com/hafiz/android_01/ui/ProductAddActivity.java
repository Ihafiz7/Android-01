package com.hafiz.android_01.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.hafiz.android_01.R;
import com.hafiz.android_01.dbUtil.ProductUti;
import com.hafiz.android_01.entity.Product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductAddActivity extends AppCompatActivity {

    private EditText editTextId, editTextName, editTextEmail, editTextPrice, editTextQuantity;
    private Button buttonSave, buttonCancel;
    private ProductUti productUti;

    private Product product;

    private ImageView imageView;

    private Uri selectedImageUri;

    private Uri cameraImageUri;

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private ActivityResultLauncher<String> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        buttonSave = findViewById(R.id.buttonSave);
        imageView = findViewById(R.id.imageView);

        productUti = new ProductUti(this);

        // Permission launcher
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) openCamera();
                    else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                });

        SharedPreferences sharedPreferences = getSharedPreferences("LoginReff", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("username"); //remove only one field
////        editor.clear();//delete the whole thing
//        editor.apply();

        String username = sharedPreferences.getString("username", "guest");
        int password = sharedPreferences.getInt("password", 1234);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
//        Toast.makeText(this, "User Name: " + username + " Password: " + password + " isLoggedIn: " + isLoggedIn
//        , Toast.LENGTH_LONG).show();

        Toast.makeText(this, "User Name: " + username + " Password: " + password + " isLoggedIn: " + isLoggedIn
                , Toast.LENGTH_LONG).show();





        // Image picker (for both gallery or camera result)
//        imagePickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK && result.getData() !=
//                            null && result.getData().getData() != null) {
//                        selectedImageUri = result.getData().getData();
//                        imgProduct.setImageURI(selectedImageUri);
//                    } else if (result.getResultCode() == RESULT_OK && cameraImageUri != null) {
//                        selectedImageUri = cameraImageUri;
//                        imgProduct.setImageURI(selectedImageUri);
//                    }
//                });
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        selectedImageUri = result.getData().getData();

                        // Persist permission
                        final int takeFlags = result.getData().getFlags() &
                                (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        imageView.setImageURI(selectedImageUri);
                    } else if (result.getResultCode() == RESULT_OK && cameraImageUri != null) {
                        selectedImageUri = cameraImageUri;
                        imageView.setImageURI(selectedImageUri);
                    }
                });

        // 📸 Image click dialog
        imageView.setOnClickListener(v -> showImageSourceDialog());

        // Check if editing
        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        if (productId != -1) {
            product = productUti.getProductById(productId);
            if (product != null) {
                editTextName.setText(product.getName());
                editTextEmail.setText(product.getEmail());
                editTextPrice.setText(String.valueOf(product.getPrice()));
                editTextQuantity.setText(String.valueOf(product.getQuantity()));
                if (product.getImage() != null) {
                    selectedImageUri = Uri.parse(product.getImage());
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }

        buttonSave.setOnClickListener(v -> saveProduct(this));
    }
    private void showImageSourceDialog() {
        String[] options = {"Camera", "Gallery"};
        new AlertDialog.Builder(this)
                .setTitle("Select Image Source")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Camera
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA);
                        }
                    } else {
                        // Gallery
                        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        galleryIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        galleryIntent.setType("image/*");
                        imagePickerLauncher.launch(galleryIntent);
                    }
                })
                .show();
    }

    // 🔘 Dialog to choose Camera or Gallery
//    private void showImageSourceDialog() {
//        String[] options = {"Camera", "Gallery"};
//        new AlertDialog.Builder(this)
//                .setTitle("Select Image Source")
//                .setItems(options, (dialog, which) -> {
//                    if (which == 0) {
//                        // Camera
//                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                                == PackageManager.PERMISSION_GRANTED) {
//                            openCamera();
//                        } else {
//                            permissionLauncher.launch(Manifest.permission.CAMERA);
//                        }
//                    } else {
//                        // Gallery
//                        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//                        galleryIntent.setType("image/*");
//                        imagePickerLauncher.launch(galleryIntent);
//                    }
//                })
//                .show();
//    }

    // 📸 Open Camera
    private void openCamera() {
        try {
            File photoFile = createImageFile();
            cameraImageUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    photoFile
            );
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            imagePickerLauncher.launch(cameraIntent);
        } catch (IOException e) {
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp;
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(fileName, ".jpg", storageDir);
    }

    // 💾 Save Product
    private void saveProduct(ProductAddActivity con) {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String qtyStr = editTextQuantity.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        int qty = Integer.parseInt(qtyStr);

        if (product == null) {
            product = new Product(0, name, email, price, qty,
                    selectedImageUri != null ? selectedImageUri.toString() : null);

            long id = productUti.insert(product);

            if (id > 0) Toast.makeText(this, "Product Added!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Insert Failed!", Toast.LENGTH_SHORT).show();
        } else {
            product.setName(name);
            product.setEmail(email);
            product.setPrice(price);
            product.setQuantity(qty);
            product.setImage(selectedImageUri != null ? selectedImageUri.toString() : null);

            int rows = productUti.update(product);
            if (rows > 0) Toast.makeText(this, "Product Updated!", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Update Failed!", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(con, ProductListActivity.class);
        startActivity(intent);
        finish();
    }

}