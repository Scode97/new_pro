package user.admin.ws.shared.dto;

import java.util.Date;

public class OrderDTO {
	
	private long id;
	private String orderId;
	private Date order_date;
	private UserDto userDetails;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public UserDto getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDto userDetails) {
		this.userDetails = userDetails;
	}

}
