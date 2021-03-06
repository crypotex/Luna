package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.*;



/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */

@Entity
@Table(name="SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "HISTORYITEM_ID", nullable = false)
	private HistoryItem historyItem;
	
	@ManyToOne
	@JoinColumn(name = "STOCKITEM_ID", nullable = false)
	private StockItem stockItem;

	@Column(name = "NAME")
	private String name;

	@Column(name = "QUANTITY")
	private Integer quantity;

	@Column(name = "PRICE")
	private double price;

	public SoldItem(StockItem stockItem, int quantity) {
		this.stockItem = stockItem;
		this.name = stockItem.getName();
		this.price = stockItem.getPrice();
		this.quantity = quantity;

	}
	
	public SoldItem() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = getStockItem().getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getSum() {
		return price * ((double) quantity);
	}

	public StockItem getStockItem() {
		return stockItem;
	}

	public void setStockItem(StockItem stockItem) {
		this.stockItem = stockItem;
	}

	public HistoryItem getHistoryItem() {
		return historyItem;
	}


	public void setHistoryItem(HistoryItem historyItem) {
		this.historyItem = historyItem;
	}

	@Override
	public String toString() {
		return "SoldItem [id=" + id + ", historyItem=" + historyItem
				+ ", stockItem=" + stockItem + ", name=" + name + ", quantity="
				+ quantity + ", price=" + price + "]";
	}
	
	
}
