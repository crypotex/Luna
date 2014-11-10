package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	
	private Session session = HibernateUtil.currentSession();
	
	public void submitCurrentPurchase(HistoryItem item) throws VerificationFailedException {
		System.out.println("Submit current purchase. jee");
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
