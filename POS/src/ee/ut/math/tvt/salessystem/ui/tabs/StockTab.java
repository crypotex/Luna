package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;


public class StockTab {

	private JButton addItembutton;

	private SalesSystemModel model;

	public StockTab(SalesSystemModel model) {
		this.model = model;
	}

	// warehouse stock tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItembutton = new JButton("Add");
		addItembutton.addActionListener(new ActionListener() {
			//modified by Annika, reason: to open a new window where you can add new item to stock
			public void actionPerformed(ActionEvent e) {
				addItemWindow();
			}
		});

		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItembutton, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}


	// table of the wareshouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

	//displays new window where you can enter new item details and adds items to stock
	private void addItemWindow() {
		JDialog addingWindow = new JDialog(new JFrame(), "Adding a new item to stock");
		GridBagLayout gb2 = new GridBagLayout();
		GridBagConstraints gc2 = new GridBagConstraints();
		gc2.ipadx = 10;
		gc2.ipady = 10;
		addingWindow.setLayout(gb2);


		//lisame valjad id, nime, koguse ja hinna jaoks
		JTextField id = new JTextField(5);
		JTextField quantity = new JTextField("1", 5);
		JTextField name = new JTextField(5);
		JTextField price = new JTextField(5);

		ArrayList<JTextField> textfields = new ArrayList<>();
		textfields.addAll(Arrays.asList(id, quantity, name, price));

		ArrayList<String> labels = new ArrayList<>();
		labels.addAll(Arrays.asList("Bar code:", "Amount:", "Name:", "Price:"));
		int counter = 0;
		for (int i = 0; i < labels.size(); i++) {
			gc2.gridx = 0;
			gc2.gridy = counter;
			addingWindow.add(new JLabel(labels.get(i)), gc2);
			gc2.gridx = 1;
			addingWindow.add(textfields.get(i), gc2);
			counter += 1;
		}


		//lisame nupu, mis lisab uue toote stocki
		JButton addItemButton = new JButton("Add to stock");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long inputId = Long.parseLong(id.getText());
					int inputQuantity = Integer.parseInt(quantity.getText());
					String inputName = name.getText();
					double inputPrice = Double.parseDouble(price.getText());

					StockItem newItem = new StockItem(inputId, inputName, "", inputPrice, inputQuantity);
					
					try {
						Method m = StockTableModel.class.getDeclaredMethod("addItem", StockItem.class);
						m.invoke(model.getWarehouseTableModel(), newItem);
					} catch (NoSuchMethodException e2) {
						e2.printStackTrace();
					} catch (SecurityException e2) {
						e2.printStackTrace();
					} catch (IllegalAccessException e2) {
						e2.printStackTrace();
					} catch (IllegalArgumentException e2) {
						e2.printStackTrace();
					} catch (InvocationTargetException e2) {
						e2.printStackTrace();
					}
					
					addingWindow.setVisible(false);

				} catch (NumberFormatException e2) {
					JDialog wrongFormatWindow = new JDialog(new JFrame(), "Wrong format!");
					JLabel wrongFormatLabel = new JLabel("WRONG FORMAT TYPE " + e2.getMessage().toUpperCase(), SwingConstants.CENTER);
					wrongFormatLabel.setForeground(Color.RED);
					wrongFormatLabel.setSize(16, 16);
					wrongFormatWindow.add(wrongFormatLabel);
					wrongFormatWindow.setLocation(550, 300);
					wrongFormatWindow.setMinimumSize(new Dimension(350, 100));
					wrongFormatWindow.setAlwaysOnTop(true);
					wrongFormatWindow.setVisible(true);
				}

			}
		});

		gc2.gridx = 0;
		gc2.gridy = 4;
		gc2.gridwidth = 2;	
		addingWindow.add(addItemButton, gc2);

		addingWindow.setMinimumSize(new Dimension(200, 200));
		addingWindow.setLocation(550, 300);
		addingWindow.setVisible(true);
	}

}
