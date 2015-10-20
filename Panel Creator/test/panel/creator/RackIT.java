/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel.creator;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author EricGummerson
 */
public class RackIT {
    
    public RackIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName method, of class Rack.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Rack instance = new Rack();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Rack.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Rack instance = new Rack();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumSuctionGroups method, of class Rack.
     */
    @Test
    public void testGetNumSuctionGroups() {
        System.out.println("getNumSuctionGroups");
        Rack instance = new Rack();
        int expResult = 0;
        int result = instance.getNumSuctionGroups();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumSuctionGroups method, of class Rack.
     */
    @Test
    public void testSetNumSuctionGroups() {
        System.out.println("setNumSuctionGroups");
        int numSuctionGroups = 0;
        Rack instance = new Rack();
        instance.setNumSuctionGroups(numSuctionGroups);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumCondenserFans method, of class Rack.
     */
    @Test
    public void testGetNumCondenserFans() {
        System.out.println("getNumCondenserFans");
        Rack instance = new Rack();
        int expResult = 0;
        int result = instance.getNumCondenserFans();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumCondenserFans method, of class Rack.
     */
    @Test
    public void testSetNumCondenserFans() {
        System.out.println("setNumCondenserFans");
        int numCondenserFans = 0;
        Rack instance = new Rack();
        instance.setNumCondenserFans(numCondenserFans);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuctionGroup method, of class Rack.
     */
    @Test
    public void testGetSuctionGroup() {
        System.out.println("getSuctionGroup");
        Rack instance = new Rack();
        ArrayList<SuctionGroup> expResult = null;
        ArrayList<SuctionGroup> result = instance.getSuctionGroup();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuctionGroupIndex method, of class Rack.
     */
    @Test
    public void testGetSuctionGroupIndex() {
        System.out.println("getSuctionGroupIndex");
        int index = 0;
        Rack instance = new Rack();
        SuctionGroup expResult = null;
        SuctionGroup result = instance.getSuctionGroupIndex(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSuctionGroup method, of class Rack.
     */
    @Test
    public void testSetSuctionGroup() {
        System.out.println("setSuctionGroup");
        ArrayList<SuctionGroup> suctionGroup = null;
        Rack instance = new Rack();
        instance.setSuctionGroup(suctionGroup);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSuctionGroupIndex method, of class Rack.
     */
    @Test
    public void testSetSuctionGroupIndex() {
        System.out.println("setSuctionGroupIndex");
        SuctionGroup suctionGroup = null;
        int index = 0;
        Rack instance = new Rack();
        instance.setSuctionGroupIndex(suctionGroup, index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxSuctionGroups method, of class Rack.
     */
    @Test
    public void testGetMaxSuctionGroups() {
        System.out.println("getMaxSuctionGroups");
        Rack instance = new Rack();
        int expResult = 0;
        int result = instance.getMaxSuctionGroups();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxSuctionGroups method, of class Rack.
     */
    @Test
    public void testSetMaxSuctionGroups() {
        System.out.println("setMaxSuctionGroups");
        int maxSuctionGroups = 0;
        Rack instance = new Rack();
        instance.setMaxSuctionGroups(maxSuctionGroups);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuctionGroupNameIndex method, of class Rack.
     */
    @Test
    public void testGetSuctionGroupNameIndex() {
        System.out.println("getSuctionGroupNameIndex");
        int index = 0;
        Rack instance = new Rack();
        String expResult = "";
        String result = instance.getSuctionGroupNameIndex(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSuctionGroupNames method, of class Rack.
     */
    @Test
    public void testGetSuctionGroupNames() {
        System.out.println("getSuctionGroupNames");
        Rack instance = new Rack();
        String[] expResult = null;
        String[] result = instance.getSuctionGroupNames();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSuctionGroup method, of class Rack.
     */
    @Test
    public void testAddSuctionGroup() {
        System.out.println("addSuctionGroup");
        int num = 0;
        Rack instance = new Rack();
        instance.addSuctionGroup(num);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
