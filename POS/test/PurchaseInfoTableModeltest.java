import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModeltest {
        private PurchaseInfoTableModel tableModel;      
        private StockItem stockitem1;
        private SoldItem solditem1;
    
        @Before
        public void setUp() {
                tableModel = new PurchaseInfoTableModel();
                stockitem1 = new StockItem ((long) 0, "testjook1", "kallis testjook", 50.00, 5);
                solditem1 = new SoldItem(stockitem1, 2);
        }
        
        @Test
        public void testGetItemByName(){
                tableModel.addItem(solditem1);
                assertEquals(solditem1, tableModel.getItemByName("testjook1"));
    }
}
