package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;


public class StockTab {

	private JButton addItem;

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

		addItem = new JButton("Add");
		addItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog adding_window = new JDialog(new JFrame(), "Adding a new item to stock");
				GridBagLayout gb2 = new GridBagLayout();
			    GridBagConstraints gc2 = new GridBagConstraints();
			    gc2.ipadx = 10;
			    gc2.ipady = 10;
			    adding_window.setLayout(gb2);
			    

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
					adding_window.add(new JLabel(labels.get(i)), gc2);
					gc2.gridx = 1;
					adding_window.add(textfields.get(i), gc2);
					counter += 1;
				}
				

				//lisame nupu, mis lisab uue toote stocki
				JButton addItemButton = new JButton("Add to stock");
				addItemButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//StockTableModel.this.addItem();;
						System.out.println("Added to stock"); //siin peab tegema veel nii, et ta lisaks stocki
					}
				});
				
				gc2.gridx = 0;
				gc2.gridy = 4;
				gc2.gridwidth = 2;	
				adding_window.add(addItemButton, gc2);
				
				adding_window.setMinimumSize(new Dimension(200, 200));
				adding_window.setLocation(550, 300);
				adding_window.setVisible(true);
			}
		});
		
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);

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

}
