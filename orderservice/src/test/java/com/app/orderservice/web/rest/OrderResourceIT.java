package com.app.orderservice.web.rest;

import static com.app.orderservice.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.orderservice.IntegrationTest;
import com.app.orderservice.domain.Order;
import com.app.orderservice.repository.OrderRepository;
import com.app.orderservice.service.dto.OrderDTO;
import com.app.orderservice.service.mapper.OrderMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderResourceIT {

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_ID = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRODUCT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_PRODUCT_QUANTITY = 1;
    private static final Integer UPDATED_PRODUCT_QUANTITY = 2;

    private static final String DEFAULT_PRODUCT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_SIZE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRODUCT_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRODUCT_WEIGHT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);

    private static final String DEFAULT_COUPON_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUPON_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SHIPPING_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_SHIPPING_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MONEY = new BigDecimal(2);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SHIPPING_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .createdDate(DEFAULT_CREATED_DATE)
            .createdUser(DEFAULT_CREATED_USER)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedUser(DEFAULT_UPDATED_USER)
            .userId(DEFAULT_USER_ID)
            .shopId(DEFAULT_SHOP_ID)
            .brandId(DEFAULT_BRAND_ID)
            .productId(DEFAULT_PRODUCT_ID)
            .productName(DEFAULT_PRODUCT_NAME)
            .productPrice(DEFAULT_PRODUCT_PRICE)
            .productQuantity(DEFAULT_PRODUCT_QUANTITY)
            .productImage(DEFAULT_PRODUCT_IMAGE)
            .productColor(DEFAULT_PRODUCT_COLOR)
            .productSize(DEFAULT_PRODUCT_SIZE)
            .productWeight(DEFAULT_PRODUCT_WEIGHT)
            .discount(DEFAULT_DISCOUNT)
            .couponCode(DEFAULT_COUPON_CODE)
            .shippingCost(DEFAULT_SHIPPING_COST)
            .tax(DEFAULT_TAX)
            .totalMoney(DEFAULT_TOTAL_MONEY)
            .status(DEFAULT_STATUS)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .shippingAddress(DEFAULT_SHIPPING_ADDRESS)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return order;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .createdDate(UPDATED_CREATED_DATE)
            .createdUser(UPDATED_CREATED_USER)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedUser(UPDATED_UPDATED_USER)
            .userId(UPDATED_USER_ID)
            .shopId(UPDATED_SHOP_ID)
            .brandId(UPDATED_BRAND_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productColor(UPDATED_PRODUCT_COLOR)
            .productSize(UPDATED_PRODUCT_SIZE)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .discount(UPDATED_DISCOUNT)
            .couponCode(UPDATED_COUPON_CODE)
            .shippingCost(UPDATED_SHIPPING_COST)
            .tax(UPDATED_TAX)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .status(UPDATED_STATUS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .shippingAddress(UPDATED_SHIPPING_ADDRESS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);
        restOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrder.getCreatedUser()).isEqualTo(DEFAULT_CREATED_USER);
        assertThat(testOrder.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testOrder.getUpdatedUser()).isEqualTo(DEFAULT_UPDATED_USER);
        assertThat(testOrder.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testOrder.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testOrder.getBrandId()).isEqualTo(DEFAULT_BRAND_ID);
        assertThat(testOrder.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testOrder.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testOrder.getProductPrice()).isEqualByComparingTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testOrder.getProductQuantity()).isEqualTo(DEFAULT_PRODUCT_QUANTITY);
        assertThat(testOrder.getProductImage()).isEqualTo(DEFAULT_PRODUCT_IMAGE);
        assertThat(testOrder.getProductColor()).isEqualTo(DEFAULT_PRODUCT_COLOR);
        assertThat(testOrder.getProductSize()).isEqualTo(DEFAULT_PRODUCT_SIZE);
        assertThat(testOrder.getProductWeight()).isEqualByComparingTo(DEFAULT_PRODUCT_WEIGHT);
        assertThat(testOrder.getDiscount()).isEqualByComparingTo(DEFAULT_DISCOUNT);
        assertThat(testOrder.getCouponCode()).isEqualTo(DEFAULT_COUPON_CODE);
        assertThat(testOrder.getShippingCost()).isEqualByComparingTo(DEFAULT_SHIPPING_COST);
        assertThat(testOrder.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testOrder.getTotalMoney()).isEqualByComparingTo(DEFAULT_TOTAL_MONEY);
        assertThat(testOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testOrder.getShippingAddress()).isEqualTo(DEFAULT_SHIPPING_ADDRESS);
        assertThat(testOrder.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void createOrderWithExistingId() throws Exception {
        // Create the Order with an existing ID
        orderRepository.saveAndFlush(order);
        OrderDTO orderDTO = orderMapper.toDto(order);

        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUser").value(hasItem(DEFAULT_CREATED_USER)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedUser").value(hasItem(DEFAULT_UPDATED_USER)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].shopId").value(hasItem(DEFAULT_SHOP_ID)))
            .andExpect(jsonPath("$.[*].brandId").value(hasItem(DEFAULT_BRAND_ID)))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productPrice").value(hasItem(sameNumber(DEFAULT_PRODUCT_PRICE))))
            .andExpect(jsonPath("$.[*].productQuantity").value(hasItem(DEFAULT_PRODUCT_QUANTITY)))
            .andExpect(jsonPath("$.[*].productImage").value(hasItem(DEFAULT_PRODUCT_IMAGE)))
            .andExpect(jsonPath("$.[*].productColor").value(hasItem(DEFAULT_PRODUCT_COLOR)))
            .andExpect(jsonPath("$.[*].productSize").value(hasItem(DEFAULT_PRODUCT_SIZE)))
            .andExpect(jsonPath("$.[*].productWeight").value(hasItem(sameNumber(DEFAULT_PRODUCT_WEIGHT))))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(sameNumber(DEFAULT_DISCOUNT))))
            .andExpect(jsonPath("$.[*].couponCode").value(hasItem(DEFAULT_COUPON_CODE)))
            .andExpect(jsonPath("$.[*].shippingCost").value(hasItem(sameNumber(DEFAULT_SHIPPING_COST))))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(sameNumber(DEFAULT_TAX))))
            .andExpect(jsonPath("$.[*].totalMoney").value(hasItem(sameNumber(DEFAULT_TOTAL_MONEY))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].shippingAddress").value(hasItem(DEFAULT_SHIPPING_ADDRESS)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)));
    }

    @Test
    @Transactional
    void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUser").value(DEFAULT_CREATED_USER))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedUser").value(DEFAULT_UPDATED_USER))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.shopId").value(DEFAULT_SHOP_ID))
            .andExpect(jsonPath("$.brandId").value(DEFAULT_BRAND_ID))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.productPrice").value(sameNumber(DEFAULT_PRODUCT_PRICE)))
            .andExpect(jsonPath("$.productQuantity").value(DEFAULT_PRODUCT_QUANTITY))
            .andExpect(jsonPath("$.productImage").value(DEFAULT_PRODUCT_IMAGE))
            .andExpect(jsonPath("$.productColor").value(DEFAULT_PRODUCT_COLOR))
            .andExpect(jsonPath("$.productSize").value(DEFAULT_PRODUCT_SIZE))
            .andExpect(jsonPath("$.productWeight").value(sameNumber(DEFAULT_PRODUCT_WEIGHT)))
            .andExpect(jsonPath("$.discount").value(sameNumber(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.couponCode").value(DEFAULT_COUPON_CODE))
            .andExpect(jsonPath("$.shippingCost").value(sameNumber(DEFAULT_SHIPPING_COST)))
            .andExpect(jsonPath("$.tax").value(sameNumber(DEFAULT_TAX)))
            .andExpect(jsonPath("$.totalMoney").value(sameNumber(DEFAULT_TOTAL_MONEY)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.shippingAddress").value(DEFAULT_SHIPPING_ADDRESS))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .createdDate(UPDATED_CREATED_DATE)
            .createdUser(UPDATED_CREATED_USER)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedUser(UPDATED_UPDATED_USER)
            .userId(UPDATED_USER_ID)
            .shopId(UPDATED_SHOP_ID)
            .brandId(UPDATED_BRAND_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productColor(UPDATED_PRODUCT_COLOR)
            .productSize(UPDATED_PRODUCT_SIZE)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .discount(UPDATED_DISCOUNT)
            .couponCode(UPDATED_COUPON_CODE)
            .shippingCost(UPDATED_SHIPPING_COST)
            .tax(UPDATED_TAX)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .status(UPDATED_STATUS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .shippingAddress(UPDATED_SHIPPING_ADDRESS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        OrderDTO orderDTO = orderMapper.toDto(updatedOrder);

        restOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrder.getCreatedUser()).isEqualTo(UPDATED_CREATED_USER);
        assertThat(testOrder.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testOrder.getUpdatedUser()).isEqualTo(UPDATED_UPDATED_USER);
        assertThat(testOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOrder.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testOrder.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
        assertThat(testOrder.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testOrder.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testOrder.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testOrder.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testOrder.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testOrder.getProductColor()).isEqualTo(UPDATED_PRODUCT_COLOR);
        assertThat(testOrder.getProductSize()).isEqualTo(UPDATED_PRODUCT_SIZE);
        assertThat(testOrder.getProductWeight()).isEqualByComparingTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testOrder.getDiscount()).isEqualByComparingTo(UPDATED_DISCOUNT);
        assertThat(testOrder.getCouponCode()).isEqualTo(UPDATED_COUPON_CODE);
        assertThat(testOrder.getShippingCost()).isEqualByComparingTo(UPDATED_SHIPPING_COST);
        assertThat(testOrder.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testOrder.getTotalMoney()).isEqualByComparingTo(UPDATED_TOTAL_MONEY);
        assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testOrder.getShippingAddress()).isEqualTo(UPDATED_SHIPPING_ADDRESS);
        assertThat(testOrder.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(UUID.randomUUID());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(UUID.randomUUID());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(UUID.randomUUID());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderWithPatch() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order using partial update
        Order partialUpdatedOrder = new Order();
        partialUpdatedOrder.setId(order.getId());

        partialUpdatedOrder
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedUser(UPDATED_UPDATED_USER)
            .userId(UPDATED_USER_ID)
            .shopId(UPDATED_SHOP_ID)
            .brandId(UPDATED_BRAND_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productSize(UPDATED_PRODUCT_SIZE)
            .tax(UPDATED_TAX)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .shippingAddress(UPDATED_SHIPPING_ADDRESS)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrder))
            )
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrder.getCreatedUser()).isEqualTo(DEFAULT_CREATED_USER);
        assertThat(testOrder.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testOrder.getUpdatedUser()).isEqualTo(UPDATED_UPDATED_USER);
        assertThat(testOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOrder.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testOrder.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
        assertThat(testOrder.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testOrder.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testOrder.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testOrder.getProductQuantity()).isEqualTo(DEFAULT_PRODUCT_QUANTITY);
        assertThat(testOrder.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testOrder.getProductColor()).isEqualTo(DEFAULT_PRODUCT_COLOR);
        assertThat(testOrder.getProductSize()).isEqualTo(UPDATED_PRODUCT_SIZE);
        assertThat(testOrder.getProductWeight()).isEqualByComparingTo(DEFAULT_PRODUCT_WEIGHT);
        assertThat(testOrder.getDiscount()).isEqualByComparingTo(DEFAULT_DISCOUNT);
        assertThat(testOrder.getCouponCode()).isEqualTo(DEFAULT_COUPON_CODE);
        assertThat(testOrder.getShippingCost()).isEqualByComparingTo(DEFAULT_SHIPPING_COST);
        assertThat(testOrder.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testOrder.getTotalMoney()).isEqualByComparingTo(UPDATED_TOTAL_MONEY);
        assertThat(testOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testOrder.getShippingAddress()).isEqualTo(UPDATED_SHIPPING_ADDRESS);
        assertThat(testOrder.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateOrderWithPatch() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order using partial update
        Order partialUpdatedOrder = new Order();
        partialUpdatedOrder.setId(order.getId());

        partialUpdatedOrder
            .createdDate(UPDATED_CREATED_DATE)
            .createdUser(UPDATED_CREATED_USER)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedUser(UPDATED_UPDATED_USER)
            .userId(UPDATED_USER_ID)
            .shopId(UPDATED_SHOP_ID)
            .brandId(UPDATED_BRAND_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .productImage(UPDATED_PRODUCT_IMAGE)
            .productColor(UPDATED_PRODUCT_COLOR)
            .productSize(UPDATED_PRODUCT_SIZE)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .discount(UPDATED_DISCOUNT)
            .couponCode(UPDATED_COUPON_CODE)
            .shippingCost(UPDATED_SHIPPING_COST)
            .tax(UPDATED_TAX)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .status(UPDATED_STATUS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .shippingAddress(UPDATED_SHIPPING_ADDRESS)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrder))
            )
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOrder.getCreatedUser()).isEqualTo(UPDATED_CREATED_USER);
        assertThat(testOrder.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testOrder.getUpdatedUser()).isEqualTo(UPDATED_UPDATED_USER);
        assertThat(testOrder.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testOrder.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testOrder.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
        assertThat(testOrder.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testOrder.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testOrder.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testOrder.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testOrder.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testOrder.getProductColor()).isEqualTo(UPDATED_PRODUCT_COLOR);
        assertThat(testOrder.getProductSize()).isEqualTo(UPDATED_PRODUCT_SIZE);
        assertThat(testOrder.getProductWeight()).isEqualByComparingTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testOrder.getDiscount()).isEqualByComparingTo(UPDATED_DISCOUNT);
        assertThat(testOrder.getCouponCode()).isEqualTo(UPDATED_COUPON_CODE);
        assertThat(testOrder.getShippingCost()).isEqualByComparingTo(UPDATED_SHIPPING_COST);
        assertThat(testOrder.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testOrder.getTotalMoney()).isEqualByComparingTo(UPDATED_TOTAL_MONEY);
        assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testOrder.getShippingAddress()).isEqualTo(UPDATED_SHIPPING_ADDRESS);
        assertThat(testOrder.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testOrder.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(UUID.randomUUID());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(UUID.randomUUID());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(UUID.randomUUID());

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, order.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
