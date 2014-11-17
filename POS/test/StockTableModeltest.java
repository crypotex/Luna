import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockTableModeltest {

        SalesDomainController SDC = new SalesDomainControllerImpl();
        StockTableModel model = new StockTableModel();
        List<StockItem> stock;

        @Before
        public void setUp() {
                stock = SDC.loadWarehouseState();
                model.populateWithData(stock);
        }

        @Test
        public void testGetItemByIdWhenItemExists() {
                StockItem item = stock.get(0);
                assertEquals(item, model.getItemById(item.getId()));
        }
}
