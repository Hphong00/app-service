package com.app.orderservice.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.app.orderservice.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private UUID id;

    private Instant createdDate;

    private String createdUser;

    private Instant updatedDate;

    private String updatedUser;

    private String userId;

    private String shopId;

    private String brandId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private String productImage;

    private String productColor;

    private String productSize;

    private BigDecimal productWeight;

    private BigDecimal discount;

    private String couponCode;

    private BigDecimal shippingCost;

    private BigDecimal tax;

    private BigDecimal totalMoney;

    private String status;

    private Instant deliveryDate;

    private String shippingAddress;

    private String paymentMethod;

    private String paymentStatus;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public BigDecimal getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(BigDecimal productWeight) {
        this.productWeight = productWeight;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id='" + getId() + "'" +
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
