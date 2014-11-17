
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

        private PurchaseInfoTableModel tableModel;
        private HistoryItem h;
        
        /**
         * Methods with @Before annotations will be invoked before each test is run.
         */
        @Before
        public void setUp() {
                soldItem1 = new SoldItem (new StockItem ((long) 0, "testjook1", "kallis testjook", 50.00, 5),1);
                
                soldItem2 = new SoldItem (new StockItem ((long) 1, "testjook2", "odav testjook", 5.00, 10),2);
                
                tableModel = new PurchaseInfoTableModel();
                
        }

        // TODO
        @Test
        public void testAddSoldItem(){
                tableModel.addItem(soldItem1);

                assertEquals(1, tableModel.getRowCount());                      //get no. of items;
                assertEquals( (long)0, tableModel.getValueAt(0, 0));            //Get id
                assertEquals( "testjook1", tableModel.getValueAt(0, 1));        //get name
                assertEquals( 50.00, tableModel.getValueAt(0, 2));                // get price
                assertEquals( 1, tableModel.getValueAt(0, 3));                  //get qty
                assertEquals( 50.00 , tableModel.getValueAt(0, 4));               //Get sum ( price*qty)
        }

        @Test
        public void testGetSumWithNoItems(){
                h = new HistoryItem(tableModel.getTableRows(),0.0);
                assertEquals(0.0, h.getSum(), 0.0001);

        }
        @Test
        public void testGetSumWithOneItem(){
                tableModel.addItem(soldItem1);
                h = new HistoryItem(tableModel.getTableRows(),50.00);
                assertEquals(50.00, h.getSum(), 0.0001);

        }
        @Test
        public void testGetSumWithMultipleItems(){
                tableModel.addItem(soldItem1);
                tableModel.addItem(soldItem2);

                h = new HistoryItem(tableModel.getTableRows(),55.00);
                assertEquals(55.00, h.getSum(), 0.0001);

        }
}
