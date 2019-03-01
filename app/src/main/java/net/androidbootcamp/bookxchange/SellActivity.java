package net.androidbootcamp.bookxchange;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

public class SellActivity extends AppCompatActivity
{
    private EditText txtISBN, txtTitle, txtEdition, txtAuthor, txtPrice;
    private Spinner spConditon;
    private String condition;
    private Button btnTakePic, btnUploadPic, btnCreatePost;
    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;
    private Boolean isPhotoTaken = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        txtISBN = findViewById(R.id.txtISBN);
        txtTitle = findViewById(R.id.txtBookTitle);
        txtEdition = findViewById(R.id.txtEditon);
        txtAuthor = findViewById(R.id.txtAuthor);
        spConditon = findViewById(R.id.spinnerCondition);
        txtPrice = findViewById(R.id.txtPrice);
        btnTakePic = findViewById(R.id.btnTakePic);
        btnUploadPic = findViewById(R.id.btnAddPic);
        btnCreatePost = findViewById(R.id.btnCreatePost);
        imageView = findViewById(R.id.imgBookPhoto);

        btnTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPhotoTaken = true;
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, PICK_IMAGE_REQUEST);
            }
        });

        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPhotoTaken = false;
                chooseImage();
            }
        });

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: WHAT HAPPENS WHEN YOU CREATE POST???
                Book book = new Book();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(!isPhotoTaken) {
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK  && data != null && data.getData() != null ) {
                filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(isPhotoTaken) {
            if(this.PICK_IMAGE_REQUEST == requestCode && resultCode == RESULT_OK){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                Intent intent = new Intent(this, AccountActivity.class);
                this.startActivity(intent);
                break;
            case R.id.sell_books:
                Intent intent1 = new Intent(this, SellActivity.class);
                this.startActivity(intent1);
                break;
            case R.id.buy_books:
                Intent intent2 = new Intent(this, BuyActivity.class);
                this.startActivity(intent2);
                break;
            case R.id.messages:
                Intent intent3 = new Intent(this, MessageActivity.class);
                this.startActivity(intent3);
                break;
            case R.id.posts:
                Intent intent4 = new Intent(this, PostActivity.class);
                this.startActivity(intent4);
                break;
            case R.id.help:
                Intent intent5 = new Intent(this, HelpActivity.class);
                this.startActivity(intent5);
                break;
            case R.id.about:
                Intent intent6 = new Intent(this, AboutActivity.class);
                this.startActivity(intent6);
                break;
        }
        return true;
    }
}
