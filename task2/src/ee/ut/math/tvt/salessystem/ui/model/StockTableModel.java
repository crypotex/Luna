package ee.ut.math.tvt.salessystem.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);
	
	private List<StockItem> stockitems = new ArrayList<StockItem>();
	
	private final SalesDomainController controller;

	public StockTableModel(SalesDomainController controller) {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
		this.controller = controller;
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 */
	public void addItem(final StockItem stockItem) {
		try {
			StockItem item = getItemById(stockItem.getId());
			item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		}
		catch (NoSuchElementException e) {
			getTableRows().add(stockItem);
			log.debug("Added " + stockItem.getName()
					+ " quantity of " + stockItem.getQuantity());
		}
		refresh();
	}

	
	
	public boolean hasEnoughInStock(StockItem item, int quantity) {
	    for(StockItem i : getTableRows()) {
	        if (i.getId().equals(item.getId())) {
	            return (i.getQuantity() >= quantity);
	        }
	    }
	    return false;
	}
	
	public boolean validateNameUniqueness(String newName) {
	    for (StockItem item : getTableRows()) {
	        log.debug(" === Comparing: " + newName + " vs. " + item.getName());
	        
	        if (newName.equals(item.getName())) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : getTableRows()) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	@Override
	public List<StockItem> getTableRows() {
		return stockitems;
	}

	@Override
	public void clearRows() {
		stockitems = new ArrayList<StockItem>();
		
	}

	@Override
	public void refresh() {
		stockitems = controller.getAllStockItems();
		
	}

}
