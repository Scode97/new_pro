  
package user.admin.ws.io.entity;


//////////////////////////////////////PROBLEM IN THIS TABLE///////////////////////////////////////////////////////////////////////////

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity( name="orders")
public class OrderEntity implements Serializable{

	private static final long serialVersionUID = 9182132082917757926L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String orderId;
	

	@Column(nullable=false)
	private Date order_date;

	@ManyToOne
	@JoinColumn(name="users")
	private UserEntity userDetails2;


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
	
	public UserEntity getUserDetails2() {
		return userDetails2;
	}

	public void setUserDetails2(UserEntity userDetails2) {
		this.userDetails2 = userDetails2;
	}


	
	
}