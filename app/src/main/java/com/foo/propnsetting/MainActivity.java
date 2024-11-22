package com.foo.propnsetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.foo.propnsetting.databinding.ActivityMainBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        profileImageView = binding.profileImageView;

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        ImageView toSetting = binding.settingButton;

        toSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, appsetting.class);
                startActivity(intent);
            }
        });
    }

    private void loadProfileImage() {

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String imageFileName = sharedPreferences.getString("profile_image_uri", null);

        if (imageFileName != null) {

            File profileImageFile = new File(getFilesDir(), imageFileName);

            if (profileImageFile.exists()) {
                Uri imageUri = Uri.fromFile(profileImageFile);


                Glide.with(this)
                        .load(imageUri)
                        .circleCrop()
                        .placeholder(R.drawable.default_profile)
                        .into(profileImageView);
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void loadImage(String url) {
        Glide.with(this)
               .load(url)
                .placeholder(R.drawable.default_profile)
                .circleCrop()
                .into(binding.profileImageView);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            saveImageToInternalStorage(imageUri);
            Glide.with(this)
                    .load(imageUri)
                    .circleCrop()
                    .placeholder(R.drawable.default_profile)
                    .into(profileImageView);
                }
    }

    private void saveImageToInternalStorage(Uri imageUri){
        try {
            // Get the InputStream of the image URI
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            // Create a FileOutputStream to save the image
            FileOutputStream fileOutputStream = openFileOutput("profile_image.jpg", MODE_PRIVATE);

            // Copy the input stream (image) to the output stream (internal storage)
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            // Close the streams
            fileOutputStream.close();
            inputStream.close();

            // Save the file name or URI in SharedPreferences for later retrieval
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profile_image_uri", "profile_image.jpg"); // Save the filename
            editor.apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}