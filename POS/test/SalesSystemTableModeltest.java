import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class SalesSystemTableModeltest {
        private SalesDomainController controller;
        private SalesSystemModel model;
        private StockTableModel stockModel;
        private StockItem stockitem1;

        
        @Before
        public void setUp() {
                controller = new SalesDomainControllerImpl();
                model = new SalesSystemModel(controller);
                stockModel = model.getWarehouseTableModel();
                stockitem1=new StockItem ((long) 0, "testjook1", "kallis testjook", 50.00, 5);
                
        }
              
        @Test 
        public void testClear() {
                if (stockModel.getRowCount()!=0){
                        stockModel.clear();
                        assertEquals(0, stockModel.getRowCount());
                }
        }                

        @Test
        public <T> void testPopulateWithData() {
                stockModel.addItem(stockitem1);
                List<StockItem> data = new ArrayList<StockItem>(stockModel.getTableRows());
                stockModel.clear();
                assertEquals((long)0, stockModel.getRowCount());
                stockModel.populateWithData(data);
                assertEquals(stockitem1, stockModel.getItemByName("testjook1"));
        }

}