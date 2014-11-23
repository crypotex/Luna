import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.AfterClass;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

public class StockTableModeltest {
    private SalesDomainController controller;
    private SalesSystemModel model;
    private StockTableModel stockModel;
    private static StockItem stockItem1;
    private static StockItem stockitem2;
    

    
    @Before
    public void setUp() {
            stockItem1 = new StockItem ((long) 99, "STMtest", "STMtest",999.00, 9);
            controller = new SalesDomainControllerImpl();
            model = new SalesSystemModel(controller);
            stockModel = model.getWarehouseTableModel();       
    }

//    @Test
//    public void testValidateNameUniqueness(){
//            long initialItemCount= (long) stockModel.getRowCount();
//            
//            stockModel.addItem(stockItem1);
//            assertEquals((long)9, (stockModel.getItemByName(stockItem1.getName())).getQuantity()); 
//            
//            
//            stockitem2 = new StockItem(stockModel.getItemByName("STMtest").getId(),stockItem1.getName(),stockItem1.getDescription(),stockItem1.getQuantity());
//            
//           System.out.println(stockitem2);
//            stockModel.addItem(stockitem2);
//            assertEquals((long)18, (stockModel.getItemByName(stockItem1.getName())).getQuantity());
// 
//            assertEquals(initialItemCount+1, (long)stockModel.getRowCount());
//    }

    @Test
    public void testHasEnoughInStock(){
            stockModel.addItem(stockItem1);
            assertTrue(stockModel.getItemByName("STMtest").getQuantity()>=stockItem1.getQuantity());
    }

    @Test
    public void testGetItemByIdWhenItemExists(){
            stockModel.addItem(stockItem1);
            StockItem a = stockModel.getItemByName("STMtest");
            long b = a.getId();
            assertEquals(stockItem1.getName(), stockModel.getItemById(b).getName());
    		assertEquals(stockItem1.getDescription(), stockModel.getItemById(b).getDescription());
    }

    @Test(expected=NoSuchElementException.class)
     
    public void testGetItemByIdWhenThrowsException(){
        	stockModel.addItem(stockItem1);
        	stockModel.getItemById(999);
    }
}