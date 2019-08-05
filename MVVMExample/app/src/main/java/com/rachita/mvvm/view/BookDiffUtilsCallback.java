package com.rachita.mvvm.view;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.rachita.mvvm.model.Book;

import java.util.List;

public class BookDiffUtilsCallback extends DiffUtil.Callback {

    List<Book> oldBooksList;
    List<Book> newBooksList;


    public BookDiffUtilsCallback(List<Book> oldBooksList, List<Book> newBooksList) {
        this.oldBooksList = oldBooksList;
        this.newBooksList = newBooksList;
    }

    @Override
    public int getOldListSize() {
        return oldBooksList==null?0:oldBooksList.size();
    }

    @Override
    public int getNewListSize() {
        return newBooksList==null?0:oldBooksList.size();
    }

    /**
     *  Called by the DiffUtil to decide whether two object represent the same Item.
     *  If your items have unique ids, this method should check their id equality.
     * @param oldBookPosition
     * @param newBookPosition
     * @return
     */
    @Override
    public boolean areItemsTheSame(int oldBookPosition, int newBookPosition) {
                 return oldBooksList.get(oldBookPosition).getBookId()==newBooksList.get(newBookPosition).getBookId();
    }


    /**
     * Checks whether two items have the same data.You can change its behavior depending on your UI.
     * This method is called by DiffUtil only if areItemsTheSame returns true.
     * @param oldBookPosition
     * @param newBookPosition
     * @return
     */

    @Override
    public boolean areContentsTheSame(int oldBookPosition, int newBookPosition) {
        return oldBooksList.get(oldBookPosition).equals(newBooksList.get(newBookPosition));
    }


    /**
     *  If areItemTheSame return true and areContentsTheSame returns false DiffUtil calls this method
     *  to get a payload about the change.
     * @param oldBookPosition
     * @param newBookPosition
     * @return
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldBookPosition, int newBookPosition) {
        return super.getChangePayload(oldBookPosition, newBookPosition);
    }
}
