package net.androidbootcamp.bookxchange.model;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@IgnoreExtraProperties
public class HitsList {

    @SerializedName("hits")
    @Expose
    private List<BookSource> bookIndex;

    public List<BookSource> getBookIndex() {
        return bookIndex;
    }

    public void setBookIndex(List<BookSource> bookIndex) {
        this.bookIndex = bookIndex;
    }
}
