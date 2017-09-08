package com.yt.demo.core.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 产品
 * @author joeyzheng
 * @date 2017年2月7日
 */
@Entity
@Table(name = "spus")
@EntityListeners(AuditingEntityListener.class)
public class SPUEntity extends AuditEntity<Long>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	protected Long id;
	
	//SPU代码
	@Column(name = "code",nullable=false)
	private String code;
	
	//SPU名称
	@Column(name = "name",nullable=false)
	private String name;
	
	//描述
	@Column(name = "description")
	private String description;
	
	//品牌ID
	@Column(name = "brand_id",nullable=false)
	private Long brandId;

	@Column(name = "brand_path",nullable=false)
	private String brandPath;
	
	//分类ID
	@Column(name = "category_id",nullable=false)
	private Long categoryId;


	
	//型号ID
	@Column(name = "model_id")
	private Long modelId;

	@Column(name = "model_path",nullable=false)
	private String modelPath;
	
	//首图 id
	@Column(name = "image_id")
	private Long imageId;
	
	//是否删除（1：是、0否）
	@Column(name = "is_delete")
	private Boolean deleted = false;
	
	//是否停产（1：是或停产、0否或在产）
	@Column(name = "is_off_production")
	private Boolean offProduction = false;
	
	//指导价格
	@Column(name = "guide_price",nullable=false)
	private BigDecimal guidePrice;
	
	//商家产品编号
	@Column(name = "merchant_number")
	private String merchantNumber;
	
	//上级SPU ID
	@Column(name = "parent_id",nullable=false)
	private Long parentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Boolean getOffProduction() {
		return offProduction;
	}

	public void setOffProduction(Boolean offProduction) {
		this.offProduction = offProduction;
	}

	public String getMerchantNumber() {
//		if(StringUtils.isNotBlank(this.merchantNumber)){
//			this.merchantNumber = this.merchantNumber.toUpperCase();
//		}
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public BigDecimal getGuidePrice() {
		return guidePrice;
	}

	public void setGuidePrice(BigDecimal guidePrice) {
		this.guidePrice = guidePrice;
	}
	
	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getBrandPath() {
		return brandPath;
	}

	public void setBrandPath(String brandPath) {
		this.brandPath = brandPath;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
}
