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
public class SuctionGroupIT {
    
    public SuctionGroupIT() {
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
     * Test of getName method, of class SuctionGroup.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        SuctionGroup instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class SuctionGroup.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        SuctionGroup instance = null;
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumCompressors method, of class SuctionGroup.
     */
    @Test
    public void testGetNumCompressors() {
        System.out.println("getNumCompressors");
        SuctionGroup instance = null;
        int expResult = 0;
        int result = instance.getNumCompressors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumCompressors method, of class SuctionGroup.
     */
    @Test
    public void testSetNumCompressors() {
        System.out.println("setNumCompressors");
        int numCompressors = 0;
        SuctionGroup instance = null;
        instance.setNumCompressors(numCompressors);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumSystems method, of class SuctionGroup.
     */
    @Test
    public void testGetNumSystems() {
        System.out.println("getNumSystems");
        SuctionGroup instance = null;
        int expResult = 0;
        int result = instance.getNumSystems();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumSystems method, of class SuctionGroup.
     */
    @Test
    public void testSetNumSystems() {
        System.out.println("setNumSystems");
        int numSystems = 0;
        SuctionGroup instance = null;
        instance.setNumSystems(numSystems);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSystemNames method, of class SuctionGroup.
     */
    @Test
    public void testGetSystemNames() {
        System.out.println("getSystemNames");
        SuctionGroup instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getSystemNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSystemNameIndex method, of class SuctionGroup.
     */
    @Test
    public void testGetSystemNameIndex() {
        System.out.println("getSystemNameIndex");
        int index = 0;
        SuctionGroup instance = null;
        String expResult = "";
        String result = instance.getSystemNameIndex(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSystemNames method, of class SuctionGroup.
     */
    @Test
    public void testSetSystemNames() {
        System.out.println("setSystemNames");
        ArrayList<String> systemNames = null;
        SuctionGroup instance = null;
        instance.setSystemNames(systemNames);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSystemNames method, of class SuctionGroup.
     */
    @Test
    public void testAddSystemNames() {
        System.out.println("addSystemNames");
        int num = 0;
        SuctionGroup instance = null;
        instance.addSystemNames(num);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of replaceSystemName method, of class SuctionGroup.
     */
    @Test
    public void testReplaceSystemName() {
        System.out.println("replaceSystemName");
        String name = "";
        int index = 0;
        SuctionGroup instance = null;
        instance.replaceSystemName(name, index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCompressorNames method, of class SuctionGroup.
     */
    @Test
    public void testGetCompressorNames() {
        System.out.println("getCompressorNames");
        SuctionGroup instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getCompressorNames();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCompressorNameIndex method, of class SuctionGroup.
     */
    @Test
    public void testGetCompressorNameIndex() {
        System.out.println("getCompressorNameIndex");
        int index = 0;
        SuctionGroup instance = null;
        String expResult = "";
        String result = instance.getCompressorNameIndex(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCompressorNames method, of class SuctionGroup.
     */
    @Test
    public void testSetCompressorNames() {
        System.out.println("setCompressorNames");
        ArrayList<String> compressorNames = null;
        SuctionGroup instance = null;
        instance.setCompressorNames(compressorNames);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCompressorNames method, of class SuctionGroup.
     */
    @Test
    public void testAddCompressorNames() {
        System.out.println("addCompressorNames");
        int num = 0;
        SuctionGroup instance = null;
        instance.addCompressorNames(num);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of replaceCompressorName method, of class SuctionGroup.
     */
    @Test
    public void testReplaceCompressorName() {
        System.out.println("replaceCompressorName");
        String name = "";
        int index = 0;
        SuctionGroup instance = null;
        instance.replaceCompressorName(name, index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
