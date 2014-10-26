package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Made By: Andre. What up ?
 */
public class HistoryTab {
	
    private SalesSystemModel model;

    public HistoryTab(SalesSystemModel model) {
    	this.model = model;
    } 
    
    // Historytab consists of JTable
    public Component draw() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        panel.setLayout(gb);
        // We might need to change these
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.NORTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 0.5;
        gc.weighty = 0.5;
        
        // Draws the historytable, which has a mouselistener for showing purchaseTable
        panel.add(drawHistoryMainTable(), gc);
        
        return panel;
    }
    /**
     * CreateHistoryTable method, extracted for clarification purposes ( a lot of 
     * rows of mouse events, but we only care about mouseClicked event. 
     * @return JTable
     */
    private JScrollPane createHistoryTable() {
    	JTable historyTable = new JTable(model.getHistoryItemsModel());
    	historyTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) { }
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) { }
			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseClicked(MouseEvent e) {
				/**
				 * @author: Annika
				 */
				JDialog oldPurchaseWindow = new JDialog(new JFrame(), "History of order");
				PurchaseInfoTableModel pitm = new PurchaseInfoTableModel();
				JTable soldItemsTable = new JTable(pitm);
				JScrollPane scrollpane = new JScrollPane(soldItemsTable);
				
				//gets the history itmes by row number
				int rowNumber = historyTable.rowAtPoint(e.getPoint());
				List<SoldItem> soldItems = model.getHistoryItemsModel().getHistoryItemByRowIndex(rowNumber).getPurchaseItemList();
				
				for (int i = 0; i < soldItems.size(); i++) {
					pitm.addItem(soldItems.get(i));
				}
				
				soldItemsTable.setPreferredScrollableViewportSize(soldItemsTable.getPreferredSize());
				
				oldPurchaseWindow.add(scrollpane);
				oldPurchaseWindow.pack();
				oldPurchaseWindow.setAlwaysOnTop(true);
				oldPurchaseWindow.setLocation(550, 300);
				oldPurchaseWindow.setVisible(true);
				
			}
		});
    	JTableHeader header = historyTable.getTableHeader();
    	
    	header.setReorderingAllowed(false);
    	JScrollPane scrollPane = new JScrollPane(historyTable);
    	return scrollPane;
    }
    
    private Component drawHistoryMainTable() {
    	JPanel panel = new JPanel();
    	
    	GridBagConstraints gc = new GridBagConstraints();
    	GridBagLayout gb = new GridBagLayout();
    	gc.fill = GridBagConstraints.BOTH;
    	gc.gridx = 0;
    	gc.gridy = 0;
    	gc.weightx = 0.5;
    	gc.weighty = 0.5;
    	panel.setLayout(gb);
    	panel.add(createHistoryTable(), gc);
    	
    	panel.setBorder(BorderFactory.createTitledBorder("Previous purchases"));
    	panel.setVisible(true);
    	return panel;
    	
    } 
    
}