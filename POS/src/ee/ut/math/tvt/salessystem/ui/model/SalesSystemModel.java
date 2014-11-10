package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {
    private static final Logger log = Logger.getLogger(SalesSystemModel.class);

    private StockTableModel warehouseTableModel; // Warehouse model
    private PurchaseInfoTableModel currentPurchaseTableModel; // Current shopping cart model
    private HistoryItemsModel currenthistoryTableModel; // HistoryTableModel
    private final SalesDomainController domainController;

    /**
     * Construct application model.
     * @param domainController Sales domain controller.
     */
    public SalesSystemModel(SalesDomainController domainController) {
        this.domainController = domainController;
        
        warehouseTableModel = new StockTableModel();
        currentPurchaseTableModel = new PurchaseInfoTableModel();
        currenthistoryTableModel = new HistoryItemsModel();
       
        currenthistoryTableModel = new HistoryItemsModel();
        // populate stock model with data from the warehouse
        warehouseTableModel.populateWithData(domainController.loadWarehouseState());
        // same for history
        currenthistoryTableModel.populateWithData(domainController.loadHistoryState());
    }

    public StockTableModel getWarehouseTableModel() {
        return warehouseTableModel;
    }

    public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
        return currentPurchaseTableModel;
    }
    
    public HistoryItemsModel getHistoryItemsModel() {
    	return currenthistoryTableModel;
    }
    
    public List<StockItem> getWarehouseState() {
        return this.getWarehouseTableModel().getTableRows();
    }
}
