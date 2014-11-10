package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import sun.util.logging.resources.logging;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.service.POSDataService;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	private Session session = HibernateUtil.currentSession();
	private static final Logger log = Logger.getLogger(SalesDomainControllerImpl.class);

	public void submitCurrentPurchase(HistoryItem item) throws VerificationFailedException {
		Transaction transaction = session.beginTransaction();
		session.save(item);
		for (SoldItem soldItem : item.getPurchaseItemList()) {
			soldItem.setHistoryItem(item);
			session.save(soldItem);
		}
		transaction.commit();
	}
	
	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {	
		List<StockItem> dataset = session.createQuery("from StockItem").list();
		return dataset;
	}
	/*
	 * Call this method only if stockitem not in the Bases of Data
	 */
	public void addToWarehouse(StockItem st) {
		Transaction tx = session.beginTransaction();
		session.save(st);
		tx.commit();
	}

	public List<HistoryItem> loadHistoryState() {	
		List<HistoryItem> dataset = session.createQuery("from HistoryItem").list();
		return dataset;
	}
	public static Session getSession(){
		return HibernateUtil.currentSession();
	}

	public void endSession() {
		HibernateUtil.closeSession();
	}

}
