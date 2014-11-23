import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemtest {
    	private static StockItem stockitem1;
        
    	@Before
    	public void setUp() {
            	stockitem1 = new StockItem((long) 0, "testjook", "testjook", 50.00, 5);
    	}

        @Test
        public void testGetSum(){
                SoldItem s1 = new SoldItem(stockitem1, 5);
                assertEquals( 250.0, s1.getSum(), 0.0001);
        }

        @Test
        public void testGetSumWithZeroQuantity(){
                SoldItem s1 = new SoldItem(stockitem1, 0);
                assertEquals( 0.0, s1.getSum(), 0.0001);

        }

}