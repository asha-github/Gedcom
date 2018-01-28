package com.aconex.gedcom.core;

import org.junit.Assert;
import org.junit.Test;

public class DataNodeTest {
    /**
     * Test to verify the DataNode APIs for a node without any children
     */
    @Test
    public void test_DataNodeWithoutChildren() {
        DataNode node = new DataNode(0, "@1234@", null, "data");
        Assert.assertTrue(node.isLeafNode());
        Assert.assertNull(node.getChildren());
        Assert.assertTrue(node.isDataAvailable());
    }

    /**
     * Test to verify the DataNode APIs for a node with children
     */
    @Test
    public void test_DataNodeWithChildren() {
        DataNode node = new DataNode(0, "@1234@", null, "");
        DataNode child = new DataNode(1, "TAG", null, "child");
        node.addChild(child);

        Assert.assertFalse(node.isLeafNode());
        Assert.assertEquals(node.getChildren().get(0), child);
        Assert.assertFalse(node.isDataAvailable());
    }

}
