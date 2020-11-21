package com.uci.warehouse.Test;

import com.uci.warehouse.Order;
import com.uci.warehouse.RouteBFS;
import com.uci.warehouse.Warehouse;
import com.uci.warehouse.readFile;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


import static org.junit.Assert.*;

public class RoutBfsTest {
    RouteBFS n =new RouteBFS(new Order(1));



    @Test
    public void testisShelf() throws FileNotFoundException {
        //n.getshelf();
        ArrayList<Integer> shelf = new ArrayList<>();
        shelf.add(2);
        shelf.add(0);
        assertEquals(true,n.isShelf(shelf));
    }
    @Test
    public void test() throws FileNotFoundException {

        RouteBFS n =new RouteBFS(new Order(1));
        RouteBFS.itemNode end;
        ArrayList<Integer> item1 = new ArrayList<>();
        // north of (14,8)
        item1.add(14);
        item1.add(9);
        ArrayList<Integer> item2 = new ArrayList<>();
        // north of (10,14)
        item2.add(10);
        item2.add(15);
        end=n.BFS(item1, item2);
    }
}
