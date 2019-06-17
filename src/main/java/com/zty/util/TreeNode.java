package com.zty.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNode<T> implements Serializable {
    private static final long serialVersionUID = 2100109853792385008L;

    private T currNodes;

    private List<TreeNode<T>> listNodes = new ArrayList<TreeNode<T>>();

    public List<TreeNode<T>> getListNodes() {
        return listNodes;
    }

    public void setListNodes(List<TreeNode<T>> listNodes) {
        this.listNodes = listNodes;
    }

    public T getCurrNodes() {
        return currNodes;
    }

    public void setCurrNodes(T currNodes) {
        this.currNodes = currNodes;
    }
}
