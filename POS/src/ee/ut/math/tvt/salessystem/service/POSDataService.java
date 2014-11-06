package ee.ut.math.tvt.salessystem.service;

import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class POSDataService {

	private Session session = HibernateUtil.currentSession();

	public List<StockItem> getStockItems() {
		List<StockItem> result = session.createQuery("from StockItem").list();
		return result;
	}

	public List<SoldItem> getSoldItems() {
		List<SoldItem> result = session.createQuery("from SoldItem").list();
		return result;
	}

	public List<HistoryItem> getHistoryItems() {
		List<HistoryItem> result = session.createQuery("from HistoryItem").list();
		return result;
	}

}