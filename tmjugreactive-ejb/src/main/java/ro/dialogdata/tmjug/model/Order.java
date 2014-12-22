package ro.dialogdata.tmjug.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.validator.constraints.NotEmpty;

import ro.dialogdata.tmjug.util.DateFormatterAdapter;


@Entity
@XmlRootElement
@Table(name = "Orders")
public class Order implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z][a-zA-Z][A-Za-z0-9 -]+", message = "Must be compliant with patter AAXXX")
    @Column(name = "order_id")
    private String orderId;
    
    @XmlJavaTypeAdapter(DateFormatterAdapter.class)
    @Temporal(TemporalType.TIMESTAMP) 
    @Column(name = "create_date") 
    private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
