package com.app.orderservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.*;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

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

    @Column(name = "shipping_cost", precision = 21, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "tax", precision = 21, scale = 2)
    private BigDecimal tax;

    @Column(name = "total_money", precision = 21, scale = 2)
    private BigDecimal totalMoney;

    @Column(name = "status")
    private String status;

    @Column(name = "delivery_date")
    private Instant deliveryDate;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Order id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Order createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return this.createdUser;
    }

    public Order createdUser(String createdUser) {
        this.setCreatedUser(createdUser);
        return this;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public Order updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return this.updatedUser;
    }

    public Order updatedUser(String updatedUser) {
        this.setUpdatedUser(updatedUser);
        return this;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getUserId() {
        return this.userId;
    }

    public Order userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return this.shopId;
    }

    public Order shopId(String shopId) {
        this.setShopId(shopId);
        return this;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBrandId() {
        return this.brandId;
    }

    public Order brandId(String brandId) {
        this.setBrandId(brandId);
        return this;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductId() {
        return this.productId;
    }

    public Order productId(String productId) {
        this.setProductId(productId);
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public Order productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return this.productPrice;
    }

    public Order productPrice(BigDecimal productPrice) {
        this.setProductPrice(productPrice);
        return this;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return this.productQuantity;
    }

    public Order productQuantity(Integer productQuantity) {
        this.setProductQuantity(productQuantity);
        return this;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return this.productImage;
    }

    public Order productImage(String productImage) {
        this.setProductImage(productImage);
        return this;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductColor() {
        return this.productColor;
    }

    public Order productColor(String productColor) {
        this.setProductColor(productColor);
        return this;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return this.productSize;
    }

    public Order productSize(String productSize) {
        this.setProductSize(productSize);
        return this;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public BigDecimal getProductWeight() {
        return this.productWeight;
    }

    public Order productWeight(BigDecimal productWeight) {
        this.setProductWeight(productWeight);
        return this;
    }

    public void setProductWeight(BigDecimal productWeight) {
        this.productWeight = productWeight;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public Order discount(BigDecimal discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public Order couponCode(String couponCode) {
        this.setCouponCode(couponCode);
        return this;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getShippingCost() {
        return this.shippingCost;
    }

    public Order shippingCost(BigDecimal shippingCost) {
        this.setShippingCost(shippingCost);
        return this;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTax() {
        return this.tax;
    }

    public Order tax(BigDecimal tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    public Order totalMoney(BigDecimal totalMoney) {
        this.setTotalMoney(totalMoney);
        return this;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getStatus() {
        return this.status;
    }

    public Order status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getDeliveryDate() {
        return this.deliveryDate;
    }

    public Order deliveryDate(Instant deliveryDate) {
        this.setDeliveryDate(deliveryDate);
        return this;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippingAddress() {
        return this.shippingAddress;
    }

    public Order shippingAddress(String shippingAddress) {
        this.setShippingAddress(shippingAddress);
        return this;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public Order paymentMethod(String paymentMethod) {
        this.setPaymentMethod(paymentMethod);
        return this;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public Order paymentStatus(String paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
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
            ", productColor='" + getProductColor() + "'" +
            ", productSize='" + getProductSize() + "'" +
            ", productWeight=" + getProductWeight() +
            ", discount=" + getDiscount() +
            ", couponCode='" + getCouponCode() + "'" +
            ", shippingCost=" + getShippingCost() +
            ", tax=" + getTax() +
            ", totalMoney=" + getTotalMoney() +
            ", status='" + getStatus() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", shippingAddress='" + getShippingAddress() + "'" +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
