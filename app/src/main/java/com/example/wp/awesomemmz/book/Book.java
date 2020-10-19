package com.example.wp.awesomemmz.book;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

/**
 * Created by wp on 2020/10/16.
 *
 * 必须保证包名路径和aidl中对应的.aidl文件的路径一样.
 *
 * 也可以把这个文件放到aidl目录下, 同时在 android{} 中间加上下面的内容
 * sourceSets {
 *     main {
 *         java.srcDirs = ['src/main/java', 'src/main/aidl']
 *     }
 * }
 */

public class Book implements Parcelable {
    public String id;
    public String name;

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @NotNull
    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    protected Book(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
