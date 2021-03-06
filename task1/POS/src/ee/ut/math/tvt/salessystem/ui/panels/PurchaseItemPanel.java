package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel implements ComboBoxEditor {

	private static final long serialVersionUID = 1L;

	private EventList<String> objects;
	private ArrayList<String> elements;

	// Text field on the dialogPane
	private JTextField barCodeField;
	private JTextField quantityField;
	private JComboBox<String> nameField;
	private JTextField priceField;

	private JButton addItemButton;

	// Warehouse model
	private SalesSystemModel model;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;

		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Initialize the textfields
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		nameField = new JComboBox<String>();
		JTextField tf = new JTextField();
		nameField.getEditor().setItem(tf);
		nameField.setPrototypeDisplayValue("XXXXXXXXXXX"); // see kast ei hyppa ringi siis

		elements = new ArrayList<String>();

		//loeme jarjest lao andmeid ja lisame need comboboxi
		for (int i = 0; i < model.getWarehouseTableModel().getRowCount(); i++) {
			elements.add(model.getWarehouseTableModel().getValueAt(i, 1).toString());
		}
		
		objects = GlazedLists.eventListOf(elements.toArray(new String[elements.size()]));
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AutoCompleteSupport.install(nameField, objects);
			}
		});
		
		// Author: Annika&Viki
		// Reason: When new item added to stock then combobox is updated
		model.getWarehouseTableModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				ArrayList<String> newProducts = new ArrayList<String>();

				// If a new product was added then it's added to the existing list of products
				for (int i = 0; i < model.getWarehouseTableModel().getRowCount(); i++) {
					if (elements.contains(model.getWarehouseTableModel().getValueAt(i, 1).toString()) == false) {
						elements.add(model.getWarehouseTableModel().getValueAt(i, 1).toString());
						newProducts.add(model.getWarehouseTableModel().getValueAt(i, 1).toString());
					}
				}
				objects.addAll(newProducts);
			}
		});

		priceField = new JTextField();

		// Fill the dialog fields if the bar code text field loses focus
		nameField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fillDialogFields();

			}
		});


		barCodeField.setEditable(false);
		priceField.setEditable(false);

		// == Add components to the panel

		// - bar code
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});

		panel.add(addItemButton);

		return panel;
	}

	// Fill dialog with data from the "database".
	public void fillDialogFields() {
		StockItem stockItem = getStockItemByName();

		if (stockItem != null) {
			String barcodeString = String.valueOf(stockItem.getId());
			barCodeField.setText(barcodeString);
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
		} else {
			reset();
		}
	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	private StockItem getStockItemById() { 
		try {
			int code = Integer.parseInt(barCodeField.getText());
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	//modified by Annika, reason: 
	// Search the warehouse for a StockItem with the name entered
	// to the name textfield.
	private StockItem getStockItemByName() { 
		try {
			String name = nameField.getSelectedItem().toString();
			return model.getWarehouseTableModel().getItemByName(name);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart.
	 */
	public void addItemEventHandler() {
		/** add chosen item to the shopping cart.
		modified by Annika, reason: to see if entered quantity is bigger than in stock (displays warning message), 
		if purchase is cancelled set the first quantity, else decrease the quantity 
		@author - Annika
		 */
		StockItem stockItem = getStockItemByName();
		if (stockItem != null) {
			int quantity;
			try {
				quantity = Integer.parseInt(quantityField.getText());
			} catch (NumberFormatException ex) {
				quantity = 1;
			}
			//kui soovitud kogus on suurem kui laos olev kogus, siis naidatakse warning messageit
			if (quantity > stockItem.getQuantity()) {
				JDialog warning_window = new JDialog(new JFrame(), "Warning");
				JLabel warning_message = new JLabel("NOT ENOUGH ITEMS IN STOCK!", SwingConstants.CENTER);
				warning_message.setForeground(Color.RED);
				warning_message.setSize(16, 16);
				warning_window.add(warning_message);

				warning_window.setSize(200, 150);
				warning_window.setAlwaysOnTop(true);
				warning_window.setLocation(550, 300);
				warning_window.setVisible(true);
			}
			else {
				//vahendatakse kogust, aga kui ost tyhistatakse, siis pannakse esialgne kogus tagasi
				SoldItem item = new SoldItem(stockItem, quantity);
				item.setId(stockItem.getId());
				model.getCurrentPurchaseTableModel().addItem(item);
				stockItem.setQuantity(stockItem.getQuantity() - quantity);
			}
		}
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		barCodeField.setText("");
		quantityField.setText("1");
		nameField.setSelectedIndex(0);;
		priceField.setText("");
	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

	@Override
	public Component getEditorComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItem(Object anObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void selectAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addActionListener(ActionListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeActionListener(ActionListener l) {
		// TODO Auto-generated method stub

	}

}
