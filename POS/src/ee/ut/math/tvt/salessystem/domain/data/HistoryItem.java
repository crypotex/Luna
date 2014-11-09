package ee.ut.math.tvt.salessystem.domain.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
/**
 * Handle and store HistoryItems. (They are displayable in HistoryTab)
 * @author - Andre. 
 *
 */

@Entity
@Table(name="HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@OneToMany(mappedBy="sale")
	private List<SoldItem> purchaseItemList;
	
	@Column(name="date")
	private Date date;
	
	@Column(name="sum")
	private double sum;
	
	@Column(name="payment")
	private double payment;
	
	public HistoryItem(List<SoldItem> soldItems, double sum ,double payment){
		Date newDate = new Date();
		this.id = newDate.getTime();
		this.purchaseItemList = soldItems;
		this.date = newDate;
		this.sum = sum;
		this.payment = payment;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return "Historyitem id: " + id + " ja date: " + getDateAsString() + " " + getTimeStampAsString();
	}
	
	public double getSum() {
		return sum;
	}
	
	public double getPayment() {
		return payment;
	}
	
	public List<SoldItem> getPurchaseItemList() {
		return purchaseItemList;
	}
	
	/**
	 * Next 2 methods are for showing both time and date
	 * @return date/time as string
	 */
	public String getDateAsString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = dateFormat.format(date);
		return dateString;
	}
	
	public String getTimeStampAsString() {
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String timeStampAsString=timeFormat.format(date.getTime());
		return timeStampAsString;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	
}
