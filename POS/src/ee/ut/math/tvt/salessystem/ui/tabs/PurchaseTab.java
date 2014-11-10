package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

	private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private final SalesDomainController domainController;
	private JButton newPurchase;
	private JButton submitPurchase;
	private JButton cancelPurchase;
	private PurchaseItemPanel purchasePane;
	private SalesSystemModel model;


	public PurchaseTab(SalesDomainController controller,
			SalesSystemModel model)
	{
		this.domainController = controller;
		this.model = model;
	}


	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog and
	 * shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model);
		panel.add(purchasePane, getConstraintsForPurchasePanel());

		return panel;
	}




	// The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined above
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}


	// Creates the button "New purchase"
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	// Creates the "Confirm" button
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}


	// Creates the "Cancel" button
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	/* === Event handlers for the menu buttons
	 *     (get executed when the buttons are clicked)
	 */

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}


	/**  Event handler for the <code>cancel purchase</code> event. */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			for (SoldItem it : model.getCurrentPurchaseTableModel().getTableRows()){
				model.getWarehouseTableModel().getItemByName(it.getName()).addQuantity(it.getQuantity());;
			}			
			domainController.cancelCurrentPurchase();
			endSale();
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/**
	 * @author - Andre
	 * SubmitPurchaseButtonClicked method, gives you a new JDialog for submitting purchase.
	 */
	protected void submitPurchaseButtonClicked() {
		log.info("Sale complete");
		try {
			log.debug("Contents of the current basket:\n" + model.getCurrentPurchaseTableModel());
			double toPay = 0;
			for (SoldItem item : model.getCurrentPurchaseTableModel().getTableRows()){
				toPay += item.getSum();
			}

			final double paySum = toPay;
			JDialog confirmDialog = new JDialog(new JFrame(), "Confirm page!");
			confirmDialog.setAlwaysOnTop(true);
			JLabel amountToPayString = new JLabel("Amount to pay: ");
			JTextField fieldAmountToPay = new JTextField(Double.toString(paySum));
			fieldAmountToPay.setEditable(false);
			JLabel amountPaid = new JLabel("Amount of money received: ");
			JLabel amountOfChange = new JLabel("Amount of change: ");
			JTextField fieldAmountOfChange = new JTextField("0");
			fieldAmountOfChange.setEnabled(false);
			JTextField fieldAmountPaid = new JTextField();
			JButton acceptPurchaseButton = new JButton("Accept Purchase");
			acceptPurchaseButton.setEnabled(false);
			JButton declinePurchaseButton = new JButton("Decline Purchase");
			fieldAmountPaid.addActionListener(new ActionListener() {
				@Override

				public void actionPerformed(ActionEvent e) {
					/**
					 * Checks if amount paid is is correctly formated and not negative.
					 * @author - Andre. 
					 */
					if (isNumeric(fieldAmountOfChange.getText()) && Double.parseDouble(fieldAmountPaid.getText())>= 0) {
						double change = Double.parseDouble(fieldAmountPaid.getText()) - paySum;
						fieldAmountOfChange.setText(Double.toString(change));
						acceptPurchaseButton.setEnabled(true);
					} else {
						confirmDialog.setAlwaysOnTop(false);
						JOptionPane.showMessageDialog(null,
								"Error: Negative amount of payment received",
								"Error Message",JOptionPane.ERROR_MESSAGE);
					}
					confirmDialog.setAlwaysOnTop(true);
				}
			});
			acceptPurchaseButton.addActionListener(new ActionListener() {
				/**
				 * @author - Andre
				 * Checks if everything ok, then adds historyitem to the historyItemModel
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					if (Double.parseDouble(fieldAmountOfChange.getText())<0 || fieldAmountPaid.getText().isEmpty()) {
						confirmDialog.setAlwaysOnTop(false);
						JOptionPane.showMessageDialog(null,
								"Error: Ask moar money",
								"Error Message",JOptionPane.ERROR_MESSAGE);
					} else { 
						HistoryItem it = new HistoryItem(model.getCurrentPurchaseTableModel().getTableRows(), paySum);
						try {
							domainController.submitCurrentPurchase(it);
						} catch (VerificationFailedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						model.getHistoryItemsModel().acceptedPurchase(it);
						endSale();
						model.getCurrentPurchaseTableModel().clear();
						confirmDialog.setVisible(false);

					}
				}
			});

			declinePurchaseButton.addActionListener(new ActionListener() {
				/**
				 * @author - Andre.
				 * Adds quantities back to warehouse stock, then clears table
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					for (SoldItem it : model.getCurrentPurchaseTableModel().getTableRows()){
						model.getWarehouseTableModel().getItemByName(it.getName()).addQuantity(it.getQuantity());;
					}
					endSale();
					model.getCurrentPurchaseTableModel().clear();
					confirmDialog.setVisible(false);

				}
			});
			/**
			 * @author - Andre.
			 * Graphics stuff now...
			 */
			confirmDialog.setSize(300,250);
			GridBagLayout gb = new GridBagLayout();
			GridBagConstraints gc = new GridBagConstraints();
			confirmDialog.setLayout(gb);

			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.weightx = 0.5;
			gc.gridx = 0;
			gc.gridy = 0;
			confirmDialog.add(amountToPayString,gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.weightx = 0.5;
			gc.gridx = 1;
			gc.gridy = 0;
			confirmDialog.add(fieldAmountToPay,gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.weightx = 0.5;
			gc.gridx = 0;
			gc.gridy = 1;
			confirmDialog.add(amountPaid,gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.weightx = 0.5;
			gc.gridx = 1;
			gc.gridy = 1;
			confirmDialog.add(fieldAmountPaid,gc);
			gc.weightx = 0.5;
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridy = 2;
			gc.gridx = 0;
			confirmDialog.add(amountOfChange,gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridx = 1;
			gc.gridy = 2;
			gc.weightx = 0.5;
			confirmDialog.add(fieldAmountOfChange,gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.weightx = 0.5;
			gc.gridy = 3;
			gc.gridx= 0;
			confirmDialog.add(acceptPurchaseButton,gc);
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.gridx = 1;
			gc.weightx = 0.5;
			gc.gridy = 3;
			confirmDialog.add(declinePurchaseButton,gc);

			confirmDialog.setLocation(550, 300);
			confirmDialog.setVisible(true);


			//			domainController.submitCurrentPurchase(
			//					model.getCurrentPurchaseTableModel().getTableRows());
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			log.error(e1.getMessage());
		}
	}




	/* === Helper methods that bring the whole purchase-tab to a certain state
	 *     when called.
	 */

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();

		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();

		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}




	/* === Next methods just create the layout constraints objects that control the
	 *     the layout of different elements in the purchase tab. These definitions are
	 *     brought out here to separate contents from layout, and keep the methods
	 *     that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}


	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}


	// The constraints that control the layout of the buttons in the purchase menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}

	public static boolean isNumeric(String str)  
	{  
		try  
		{  
			double d = Double.parseDouble(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
}
