package ee.ut.math.tvt.salessystem.ui.model;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;
/**
 * Adds HistoryItemsModel to the model , so here we deal with historyItems 
 * and their adding
 * @author - Andre. 
 *
 */
public class HistoryItemsModel extends SalesSystemTableModel<HistoryItem> {
	
	/**
	 * Somehow the serialVersionUID makes it better :) 
	 */
	private static final long serialVersionUID = 1L;
	
	public HistoryItemsModel() {
		super(new String[] {"Date","Time","Total Price"});
	}
	
	public int getColumnCount() {
		return headers.length;
	}
	
	public int getRowCount(){
		return rows.size();
	}
	
	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDateAsString();
		case 1:
			return item.getTimeStampAsString();
		case 2:
			return item.getSum();
			
		}
		throw new IllegalArgumentException("Column index out of range");
	}
	
	public void acceptedPurchase(HistoryItem acceptedPurchase){
		try {
			rows.add(acceptedPurchase);
			fireTableDataChanged();
		} catch (Exception e) {
			throw new RuntimeException("HistoryItemsModel Error : AcceptedPurchase Error: Line 49");
		}
	}
	
	public HistoryItem getHistoryItemByRowIndex(int row){
		try {
			return rows.get(row);
		} catch (Exception e) {
			throw new RuntimeException("HistoryItemsModel Error : getHistoryItemByRowIndex Error: Line 57");
		}
	}
	
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final HistoryItem item : rows) {
			buffer.append(item.getDateAsString() + "\t");
			buffer.append(item.getTimeStampAsString() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
}
