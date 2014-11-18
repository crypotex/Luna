import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockTableModeltest {

    	private SalesDomainController controller;
    	private SalesSystemModel model;
    	private StockTableModel stockModel;
    	private static StockItem stockItem1;

        @Before
        public void setUp() {
            stockItem1 = new StockItem ((long) 99, "OMA PRODUKT", "oma kirjeldus",99.00, 3);
            controller = new SalesDomainControllerImpl();
            model = new SalesSystemModel(controller);
            stockModel = model.getWarehouseTableModel();
        }

        @Test
        public void testValidateNameUniqueness(){
                long initialItemCount= (long) stockModel.getRowCount();
                
                stockModel.addItem(stockItem1); //Add an item once
                assertEquals((long)3, stockModel.getItemById(99).getQuantity()); 
                
                stockModel.addItem(stockItem1); //Add the same item again
                assertEquals((long)6, stockModel.getItemById(99).getQuantity()); //After adding the duplicate, the qty should have doubled

                //after adding the same item twice, we should have ended with only 1 new row in the table 
                assertEquals(initialItemCount+1, (long)stockModel.getRowCount());
                }
}
