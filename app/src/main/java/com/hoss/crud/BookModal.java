package com.hoss.crud;

import android.os.Parcel;
import android.os.Parcelable;

public class BookModal implements Parcelable {
    private String bookID;  
    private String title;
    private String author;
    private String desc;
    private String img;
    private String price;
    private String kind;
  

    public BookModal() {
    }

    protected BookModal(Parcel in) {
        title = in.readString();
        author = in.readString();
        desc = in.readString();
        img = in.readString();
        price = in.readString();
        kind = in.readString();
        bookID = in.readString();
    }

    public static final Creator<BookModal> CREATOR = new Creator<BookModal>() {
        @Override
        public BookModal createFromParcel(Parcel in) {
            return new BookModal(in);
        }

        @Override
        public BookModal[] newArray(int size) {
            return new BookModal[size];
        }
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getBookID() {
        return bookID;
    }

    public void setbookID(String bookID) {
        this.bookID = bookID;
    }

    public BookModal(String title, String author, String desc, String img, String price, String kind, String bookID) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.img = img;
        this.price = price;
        this.kind = kind;
        this.bookID = bookID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(desc);
        parcel.writeString(img);
        parcel.writeString(price);
        parcel.writeString(kind);
        parcel.writeString(bookID);
    }
}
