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
	@Column(name="ID")
	private long id;

	@OneToMany(mappedBy="historyItem")
	private List<SoldItem> purchaseItemList;
	
	@Column(name="DATE")
	private Date date;
	
	@Column(name="SUMMA")
	private double sum;
	
	public HistoryItem(List<SoldItem> soldItems, double sum){
		Date newDate = new Date();
		this.purchaseItemList = soldItems;
		this.date = newDate;
		this.sum = sum;
	}
	
	public HistoryItem(){
		date = new Date();
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
	
	public void setId(long id) {
		this.id = id;
	}

	public void setPurchaseItemList(List<SoldItem> purchaseItemList) {
		this.purchaseItemList = purchaseItemList;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

}
