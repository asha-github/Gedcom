package com.aconex.gedcom.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Node representing data.
 * 
 * @author ashas
 *
 */
public class DataNode {
    private int level;
    private String id;
    private String tag;
    private String data;
    private DataNode parentNode;
    private List<DataNode> children;

    public DataNode(int level, String id, String tag, String data) {
        super();
        this.level = level;
        this.id = id;
        this.tag = tag;
        this.data = data;
    }

    /**
     * API to add a child to this current node. Linked list is used to maintain
     * the list of children so that retrieval can be done in the added order.
     * 
     * @param node
     *            - node to be added as child
     */
    public void addChild(DataNode node) {
        if (children == null) {
            children = new LinkedList<DataNode>();
        }
        children.add(node);
    }

    public int getLevel() {
        return level;
    }

    public String getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getData() {
        return data;
    }

    public DataNode getParent() {
        return parentNode;
    }

    public void setParent(DataNode parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * API to get the list of children of this node.
     * 
     * @return
     */
    public List<DataNode> getChildren() {
        return children;
    }

    /**
     * API to know whether the current node is a leaf node.
     * 
     * @return
     */
    public boolean isLeafNode() {
        return children == null;
    }

    /**
     * API to know whether data is available in the node
     * 
     * @return
     */
    public boolean isDataAvailable() {
        return data != null && !data.isEmpty();
    }
}
