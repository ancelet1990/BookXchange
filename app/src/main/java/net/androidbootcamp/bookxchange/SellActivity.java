package net.androidbootcamp.bookxchange;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.androidbootcamp.bookxchange.model.Book;

import java.io.IOException;
import java.util.UUID;

public class SellActivity extends AppCompatActivity
{
    private EditText txtISBN, txtTitle, txtAuthor, txtPrice, txtTags, txtEdition;
    private Spinner spConditon;
    private String condition, bookID;
    private String photoURL;
    private Button btnUploadPic, btnCreatePost;
    private FloatingActionButton fabCreatePost;
    private ImageView imageView;
    private Book book;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    private DatabaseReference database;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        txtISBN = findViewById(R.id.txtISBN);
        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        spConditon = findViewById(R.id.spinnerCondition);
        txtPrice = findViewById(R.id.txtPrice);
        btnUploadPic = findViewById(R.id.btnAddPic);
        imageView = findViewById(R.id.imgBookPhoto);
        fabCreatePost = findViewById(R.id.fabCreatePost);
        txtEdition = findViewById(R.id.txtEdiiton);
        txtTags = findViewById(R.id.txtTags);

        imageView.setColorFilter(Color.LTGRAY);

        book = new Book();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        spConditon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                condition = spConditon.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getApplicationContext(), "Please select a condition",
                               Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<CharSequence> condition_adapter = ArrayAdapter
                .createFromResource(this, R.array.condition_array, R.layout.spinner_item);
        spConditon.setAdapter(condition_adapter);

        btnUploadPic.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                chooseImage();
            }
        });

        fabCreatePost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String isbn = txtISBN.getText().toString().trim();
                String title = txtTitle.getText().toString().trim();
                String author = txtAuthor.getText().toString().trim();
                String price = txtPrice.getText().toString().trim();
                String edition = txtEdition.getText().toString().trim();
                String tags = txtTags.getText().toString().trim();
                if (TextUtils.isEmpty(condition))
                {
                    Toast.makeText(getApplicationContext(), "Please select a condition",
                                   Toast.LENGTH_SHORT).show();
                    return;
                }



                database = FirebaseDatabase.getInstance().getReference();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //book = new Book(isbn, title, author, condition, price, photoURL, uid, false);
                book.setIsbn(isbn);
                book.setTitle(title);
                book.setAuthor(author);
                book.setCondition(condition);
                book.setPrice(price);
                book.setUid(uid);
                book.setEdition(edition);
                book.setTags(tags);
                book.setBookIsSold(false);

                database.child("books").child(bookID).setValue(book);
                startActivity(new Intent(SellActivity.this, ManagePostsActivity.class));
            }
        });
    }

    private void chooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&
            data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.clearColorFilter();
                imageView.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            bookID = UUID.randomUUID().toString();
            book.setBookID(bookID);
            final StorageReference ref = storageReference.child("Book_Images/" + bookID);

            ref.putFile(filePath)
               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
               {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                   {


                   }
               }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    progressDialog.dismiss();
                    Toast.makeText(SellActivity.this, "Failed " + e.getMessage(),
                                   Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() /
                                       taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        photoURL = task.getResult().toString();
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.v("This url = ", uri.toString());
                                book.setPhotoURL(uri.toString());
                                Log.v("this book url = ", book.getPhotoURL());
                            }
                        });

                        photoURL = ref.getDownloadUrl().toString();
                        progressDialog.dismiss();
                        Toast.makeText(SellActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                    else {


                    }
                }
            });
        } else
        {
            Toast.makeText(SellActivity.this, "Filepath is NULL", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.account:
                Intent intent = new Intent(this, AccountActivity.class);
                this.startActivity(intent);
                break;
            case R.id.sell_books:
                Intent intent1 = new Intent(this, SellActivity.class);
                this.startActivity(intent1);
                break;
            case R.id.buy_books:
                Intent intent2 = new Intent(this, MainBuyActivity.class);
                this.startActivity(intent2);
                break;
            case R.id.messages:
                Intent intent3 = new Intent(this, MainActivity.class);
                this.startActivity(intent3);
                break;
            case R.id.posts:
                Intent intent4 = new Intent(this, ManagePostsActivity.class);
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