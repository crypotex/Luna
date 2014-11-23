import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;

public class HistoryItemtest {
        
		private SoldItem soldItem1;
        private SoldItem soldItem2;
        private PurchaseInfoTableModel purchaseModel;
        private HistoryItem h;
        
        @Before
        public void setUp() {
                soldItem1 = new SoldItem (new StockItem ((long) 0, "testjook1", "kallis testjook", 50.00, 5),1);
                
                soldItem2 = new SoldItem (new StockItem ((long) 1, "testjook2", "odav testjook", 5.00, 10),2);
                
                purchaseModel = new PurchaseInfoTableModel();
                
        }

        @Test
        public void testAddSoldItem(){
        		purchaseModel.addItem(soldItem1);

                assertEquals(1, purchaseModel.getRowCount());
                assertEquals( (long)0, purchaseModel.getValueAt(0, 0));
                assertEquals( "testjook1", purchaseModel.getValueAt(0, 1));
                assertEquals( 50.00, purchaseModel.getValueAt(0, 2));
                assertEquals( 1, purchaseModel.getValueAt(0, 3));
                assertEquals( 50.00 , purchaseModel.getValueAt(0, 4));
        }

        @Test
        public void testGetSumWithNoItems(){
                h = new HistoryItem(purchaseModel.getTableRows(),0.0);
                assertEquals(0.0, h.getSum(), 0.0001);

        }
        @Test
        public void testGetSumWithOneItem(){
        		purchaseModel.addItem(soldItem1);
                h = new HistoryItem(purchaseModel.getTableRows(),50.00);
                assertEquals(50.00, h.getSum(), 0.0001);

        }
        @Test
        public void testGetSumWithMultipleItems(){
        		purchaseModel.addItem(soldItem1);
        		purchaseModel.addItem(soldItem2);

                h = new HistoryItem(purchaseModel.getTableRows(),55.00);
                assertEquals(55.00, h.getSum(), 0.0001);

        }
}
