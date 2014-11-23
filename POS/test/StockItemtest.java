import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemtest {

        @Before
        public void setUp() {
        }

        @Test
        public void testClone(){
                StockItem s = new StockItem((long) 0, "SItest", "SItest", 1.01, 10);
                StockItem sClone=(StockItem) s.clone();
                assertEquals(s.getId(), sClone.getId());
                assertEquals(s.getName(), sClone.getName());
                assertEquals(s.getDescription(), sClone.getDescription());
                assertEquals(s.getQuantity(), sClone.getQuantity());
        }

        @Test
        public void testGetColumn(){
                StockItem s = new StockItem((long) 0, "SItest", "SItest", 1.01, 10);
                assertEquals((long) 0, s.getColumn(0)); 
                assertEquals("SItest",s.getColumn(1));    
                assertEquals( 1.01,s.getColumn(2));             
                assertEquals(10,s.getColumn(3));                

                

        }

}
