package com.app.orderservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "shop_id")
    private String shopId;

    @Column(name = "brand_id")
    private String brandId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price", precision = 21, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "product_quantity")
    private Integer productQuantity;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "subtotal", precision = 21, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "shipping_cost", precision = 21, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "tax", precision = 21, scale = 2)
    private BigDecimal tax;

    @Column(name = "total_money", precision = 21, scale = 2)
    private BigDecimal totalMoney;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "product_color")
    private String productColor;

    @Column(name = "product_size")
    private String productSize;

    @Column(name = "product_weight", precision = 21, scale = 2)
    private BigDecimal productWeight;

    @Column(name = "discount", precision = 21, scale = 2)
    private BigDecimal discount;

    @Column(name = "coupon_code")
    private String couponCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Cart id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Cart createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return this.createdUser;
    }

    public Cart createdUser(String createdUser) {
        this.setCreatedUser(createdUser);
        return this;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Cart updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return this.updatedUser;
    }

    public Cart updatedUser(String updatedUser) {
        this.setUpdatedUser(updatedUser);
        return this;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getUserId() {
        return this.userId;
    }

    public Cart userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return this.shopId;
    }

    public Cart shopId(String shopId) {
        this.setShopId(shopId);
        return this;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBrandId() {
        return this.brandId;
    }

    public Cart brandId(String brandId) {
        this.setBrandId(brandId);
        return this;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductId() {
        return this.productId;
    }

    public Cart productId(String productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public Cart productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return this.productPrice;
    }

    public Cart productPrice(BigDecimal productPrice) {
        this.setProductPrice(productPrice);
        return this;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return this.productQuantity;
    }

    public Cart productQuantity(Integer productQuantity) {
        this.setProductQuantity(productQuantity);
        return this;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return this.productImage;
    }

    public Cart productImage(String productImage) {
        this.setProductImage(productImage);
        return this;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getSubtotal() {
        return this.subtotal;
    }

    public Cart subtotal(BigDecimal subtotal) {
        this.setSubtotal(subtotal);
        return this;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getShippingCost() {
        return this.shippingCost;
    }

    public Cart shippingCost(BigDecimal shippingCost) {
        this.setShippingCost(shippingCost);
        return this;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public Cart tax(BigDecimal tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    public Cart totalMoney(BigDecimal totalMoney) {
        this.setTotalMoney(totalMoney);
        return this;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Cart createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductColor() {
        return this.productColor;
    }

    public Cart productColor(String productColor) {
        this.setProductColor(productColor);
        return this;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return this.productSize;
    }

    public Cart productSize(String productSize) {
        this.setProductSize(productSize);
        return this;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public BigDecimal getProductWeight() {
        return this.productWeight;
    }

    public Cart productWeight(BigDecimal productWeight) {
        this.setProductWeight(productWeight);
        return this;
    }

    public void setProductWeight(BigDecimal productWeight) {
        this.productWeight = productWeight;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public Cart discount(BigDecimal discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public Cart couponCode(String couponCode) {
        this.setCouponCode(couponCode);
        return this;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdUser='" + getCreatedUser() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", updatedUser='" + getUpdatedUser() + "'" +
            ", userId='" + getUserId() + "'" +
            ", shopId='" + getShopId() + "'" +
            ", brandId='" + getBrandId() + "'" +
            ", productId='" + getProductId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productPrice=" + getProductPrice() +
            ", productQuantity=" + getProductQuantity() +
            ", productImage='" + getProductImage() + "'" +
            ", subtotal=" + getSubtotal() +
            ", shippingCost=" + getShippingCost() +
            ", tax=" + getTax() +
            ", totalMoney=" + getTotalMoney() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", productColor='" + getProductColor() + "'" +
            ", productSize='" + getProductSize() + "'" +
            ", productWeight=" + getProductWeight() +
            ", discount=" + getDiscount() +
            ", couponCode='" + getCouponCode() + "'" +
            "}";
    }
}
