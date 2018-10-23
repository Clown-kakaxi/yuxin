package com.yuchengtech.crm.comsit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OCRM_COMPOSIT_FUNCTION")
public class OcrmCompositFunction {

	
    /** 账户类型ID */
    @Id
    @GeneratedValue(generator = "CommonSequnce", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name = "MENU_ID")
    private Long menuId;
    
    private String comsits;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getComsits() {
		return comsits;
	}

	public void setComsits(String comsits) {
		this.comsits = comsits;
	}
    
    
}
