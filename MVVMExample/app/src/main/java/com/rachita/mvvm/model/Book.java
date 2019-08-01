package com.rachita.mvvm.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rachita.mvvm.BR;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "book_table", foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id",
        childColumns = "category_id", onDelete = CASCADE))
public class Book extends BaseObservable {

    @ColumnInfo(name = "book_name")
    private String name;

    @ColumnInfo(name = "book_price")
    private String price;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    private int bookId;


    @ColumnInfo(name = "category_id")
    private int categoryId;

    @Ignore
    public Book() {
    }


    public Book(String name, String price, int categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
        notifyPropertyChanged(BR.bookId);
    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }
}
