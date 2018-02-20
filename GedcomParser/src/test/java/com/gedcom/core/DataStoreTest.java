package com.gedcom.core;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class DataStoreTest {
    private static DataStore dataStore = null;

    @Before
    public void init() {
        dataStore = DataStore.getDataStore();
    }

    @After
    public void deinit() {
        Whitebox.setInternalState(DataStore.class, "dataStore", (DataStore) null);
        dataStore = null;
    }

    @Test
    public void test_addNodeWithoutChildren() {
        List<DataNode> expectedNodeList = new LinkedList<>();
        DataNode node = new DataNode(0, "@1234@", null, "data");
        expectedNodeList.add(dataStore.getRoot());
        expectedNodeList.add(node);

        dataStore.addNode(node);

        // verification
        List<DataNode> actualNodeList = new LinkedList<>();
        retrieveNodesInOrder(actualNodeList, dataStore.getRoot());
        Assert.assertEquals(actualNodeList.size(), expectedNodeList.size());
        Assert.assertTrue(actualNodeList.containsAll(expectedNodeList));
    }

    /**
     * Test to verify the tree data creation when single child comes for a node
     */
    @Test
    public void test_addNodeWithSingleChild() {
        List<DataNode> expectedNodeList = new LinkedList<>();
        DataNode node1 = new DataNode(0, "@1234@", null, "parent");
        DataNode node2 = new DataNode(1, "@5678@", null, "child");
        expectedNodeList.add(dataStore.getRoot());
        expectedNodeList.add(node1);
        expectedNodeList.add(node2);

        dataStore.addNode(node1);
        dataStore.addNode(node2);

        // verification
        List<DataNode> actualNodeList = new LinkedList<>();
        retrieveNodesInOrder(actualNodeList, dataStore.getRoot());
        Assert.assertEquals(actualNodeList.size(), expectedNodeList.size());
        Assert.assertTrue(actualNodeList.containsAll(expectedNodeList));
    }

    /**
     * Test to verify the tree data creation when more than one children comes
     * for a node
     */
    @Test
    public void test_addNodeWithMultipleChildren() {
        List<DataNode> expectedNodeList = new LinkedList<>();
        DataNode node1 = new DataNode(0, "@1234@", null, "parent");
        DataNode node2 = new DataNode(1, "@5678@", null, "first child");
        DataNode node3 = new DataNode(1, "@910@", null, "second child");
        DataNode node4 = new DataNode(2, "@112@", null, "third child");
        expectedNodeList.add(dataStore.getRoot());
        expectedNodeList.add(node1);
        expectedNodeList.add(node2);
        expectedNodeList.add(node3);
        expectedNodeList.add(node4);

        dataStore.addNode(node1);
        dataStore.addNode(node2);
        dataStore.addNode(node3);
        dataStore.addNode(node4);

        // verification
        List<DataNode> actualNodeList = new LinkedList<>();
        retrieveNodesInOrder(actualNodeList, dataStore.getRoot());
        Assert.assertEquals(actualNodeList.size(), expectedNodeList.size());
        Assert.assertTrue(actualNodeList.containsAll(expectedNodeList));
    }

    /**
     * Test to verify the tree data creation when more than one hierarchy exists
     * in the Gedcom data with multiple children in each for a node
     */
    @Test
    public void test_addNodeWithMultipleHierarchies() {
        List<DataNode> expectedNodeList = new LinkedList<>();
        DataNode node1 = new DataNode(0, "@1234@", null, "first hierarchy");
        DataNode node2 = new DataNode(1, "@5678@", null, "first child");
        DataNode node3 = new DataNode(2, "@910@", null, "second child");
        DataNode node4 = new DataNode(0, "@12@", null, "second hierarchy");
        DataNode node5 = new DataNode(1, "@13@", null, "first child");
        DataNode node6 = new DataNode(2, "@14@", null, "second child");
        expectedNodeList.add(dataStore.getRoot());
        expectedNodeList.add(node1);
        expectedNodeList.add(node2);
        expectedNodeList.add(node3);
        expectedNodeList.add(node4);
        expectedNodeList.add(node5);
        expectedNodeList.add(node6);

        dataStore.addNode(node1);
        dataStore.addNode(node2);
        dataStore.addNode(node3);
        dataStore.addNode(node4);
        dataStore.addNode(node5);
        dataStore.addNode(node6);

        // verification
        List<DataNode> actualNodeList = new LinkedList<>();
        retrieveNodesInOrder(actualNodeList, dataStore.getRoot());
        Assert.assertEquals(actualNodeList.size(), expectedNodeList.size());
        Assert.assertTrue(actualNodeList.containsAll(expectedNodeList));
    }

    /**
     * Method to retrieve nodes in order for verification
     * 
     * @param node
     * @return
     */
    private List<DataNode> retrieveNodesInOrder(List<DataNode> actualNodeList, DataNode node) {
        actualNodeList.add(node);
        List<DataNode> children = node.getChildren();
        if (children != null) {
            for (DataNode child : children) {
                retrieveNodesInOrder(actualNodeList, child);
            }
        }
        return actualNodeList;
    }

}
