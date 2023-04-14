package com.app.orderservice.web.rest;

import static com.app.orderservice.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.orderservice.IntegrationTest;
import com.app.orderservice.domain.Cart;
import com.app.orderservice.repository.CartRepository;
import com.app.orderservice.service.dto.CartDTO;
import com.app.orderservice.service.mapper.CartMapper;
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
 * Integration tests for the {@link CartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CartResourceIT {

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

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SHIPPING_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_SHIPPING_COST = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MONEY = new BigDecimal(2);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartMockMvc;

    private Cart cart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createEntity(EntityManager em) {
        Cart cart = new Cart()
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
            .subtotal(DEFAULT_SUBTOTAL)
            .shippingCost(DEFAULT_SHIPPING_COST)
            .tax(DEFAULT_TAX)
            .totalMoney(DEFAULT_TOTAL_MONEY)
            .createdAt(DEFAULT_CREATED_AT)
            .productColor(DEFAULT_PRODUCT_COLOR)
            .productSize(DEFAULT_PRODUCT_SIZE)
            .productWeight(DEFAULT_PRODUCT_WEIGHT)
            .discount(DEFAULT_DISCOUNT)
            .couponCode(DEFAULT_COUPON_CODE);
        return cart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createUpdatedEntity(EntityManager em) {
        Cart cart = new Cart()
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
            .subtotal(UPDATED_SUBTOTAL)
            .shippingCost(UPDATED_SHIPPING_COST)
            .tax(UPDATED_TAX)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .createdAt(UPDATED_CREATED_AT)
            .productColor(UPDATED_PRODUCT_COLOR)
            .productSize(UPDATED_PRODUCT_SIZE)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .discount(UPDATED_DISCOUNT)
            .couponCode(UPDATED_COUPON_CODE);
        return cart;
    }

    @BeforeEach
    public void initTest() {
        cart = createEntity(em);
    }

    @Test
    @Transactional
    void createCart() throws Exception {
        int databaseSizeBeforeCreate = cartRepository.findAll().size();
        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);
        restCartMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCart.getCreatedUser()).isEqualTo(DEFAULT_CREATED_USER);
        assertThat(testCart.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testCart.getUpdatedUser()).isEqualTo(DEFAULT_UPDATED_USER);
        assertThat(testCart.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCart.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testCart.getBrandId()).isEqualTo(DEFAULT_BRAND_ID);
        assertThat(testCart.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testCart.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testCart.getProductPrice()).isEqualByComparingTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testCart.getProductQuantity()).isEqualTo(DEFAULT_PRODUCT_QUANTITY);
        assertThat(testCart.getProductImage()).isEqualTo(DEFAULT_PRODUCT_IMAGE);
        assertThat(testCart.getSubtotal()).isEqualByComparingTo(DEFAULT_SUBTOTAL);
        assertThat(testCart.getShippingCost()).isEqualByComparingTo(DEFAULT_SHIPPING_COST);
        assertThat(testCart.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testCart.getTotalMoney()).isEqualByComparingTo(DEFAULT_TOTAL_MONEY);
        assertThat(testCart.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCart.getProductColor()).isEqualTo(DEFAULT_PRODUCT_COLOR);
        assertThat(testCart.getProductSize()).isEqualTo(DEFAULT_PRODUCT_SIZE);
        assertThat(testCart.getProductWeight()).isEqualByComparingTo(DEFAULT_PRODUCT_WEIGHT);
        assertThat(testCart.getDiscount()).isEqualByComparingTo(DEFAULT_DISCOUNT);
        assertThat(testCart.getCouponCode()).isEqualTo(DEFAULT_COUPON_CODE);
    }

    @Test
    @Transactional
    void createCartWithExistingId() throws Exception {
        // Create the Cart with an existing ID
        cartRepository.saveAndFlush(cart);
        CartDTO cartDTO = cartMapper.toDto(cart);

        int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarts() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList
        restCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId().toString())))
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
            .andExpect(jsonPath("$.[*].subtotal").value(hasItem(sameNumber(DEFAULT_SUBTOTAL))))
            .andExpect(jsonPath("$.[*].shippingCost").value(hasItem(sameNumber(DEFAULT_SHIPPING_COST))))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(sameNumber(DEFAULT_TAX))))
            .andExpect(jsonPath("$.[*].totalMoney").value(hasItem(sameNumber(DEFAULT_TOTAL_MONEY))))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].productColor").value(hasItem(DEFAULT_PRODUCT_COLOR)))
            .andExpect(jsonPath("$.[*].productSize").value(hasItem(DEFAULT_PRODUCT_SIZE)))
            .andExpect(jsonPath("$.[*].productWeight").value(hasItem(sameNumber(DEFAULT_PRODUCT_WEIGHT))))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(sameNumber(DEFAULT_DISCOUNT))))
            .andExpect(jsonPath("$.[*].couponCode").value(hasItem(DEFAULT_COUPON_CODE)));
    }

    @Test
    @Transactional
    void getCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get the cart
        restCartMockMvc
            .perform(get(ENTITY_API_URL_ID, cart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cart.getId().toString()))
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
            .andExpect(jsonPath("$.subtotal").value(sameNumber(DEFAULT_SUBTOTAL)))
            .andExpect(jsonPath("$.shippingCost").value(sameNumber(DEFAULT_SHIPPING_COST)))
            .andExpect(jsonPath("$.tax").value(sameNumber(DEFAULT_TAX)))
            .andExpect(jsonPath("$.totalMoney").value(sameNumber(DEFAULT_TOTAL_MONEY)))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.productColor").value(DEFAULT_PRODUCT_COLOR))
            .andExpect(jsonPath("$.productSize").value(DEFAULT_PRODUCT_SIZE))
            .andExpect(jsonPath("$.productWeight").value(sameNumber(DEFAULT_PRODUCT_WEIGHT)))
            .andExpect(jsonPath("$.discount").value(sameNumber(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.couponCode").value(DEFAULT_COUPON_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCart() throws Exception {
        // Get the cart
        restCartMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart
        Cart updatedCart = cartRepository.findById(cart.getId()).get();
        // Disconnect from session so that the updates on updatedCart are not directly saved in db
        em.detach(updatedCart);
        updatedCart
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
            .subtotal(UPDATED_SUBTOTAL)
            .shippingCost(UPDATED_SHIPPING_COST)
            .tax(UPDATED_TAX)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .createdAt(UPDATED_CREATED_AT)
            .productColor(UPDATED_PRODUCT_COLOR)
            .productSize(UPDATED_PRODUCT_SIZE)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .discount(UPDATED_DISCOUNT)
            .couponCode(UPDATED_COUPON_CODE);
        CartDTO cartDTO = cartMapper.toDto(updatedCart);

        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cartDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCart.getCreatedUser()).isEqualTo(UPDATED_CREATED_USER);
        assertThat(testCart.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCart.getUpdatedUser()).isEqualTo(UPDATED_UPDATED_USER);
        assertThat(testCart.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCart.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testCart.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
        assertThat(testCart.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testCart.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testCart.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testCart.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testCart.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testCart.getSubtotal()).isEqualByComparingTo(UPDATED_SUBTOTAL);
        assertThat(testCart.getShippingCost()).isEqualByComparingTo(UPDATED_SHIPPING_COST);
        assertThat(testCart.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testCart.getTotalMoney()).isEqualByComparingTo(UPDATED_TOTAL_MONEY);
        assertThat(testCart.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCart.getProductColor()).isEqualTo(UPDATED_PRODUCT_COLOR);
        assertThat(testCart.getProductSize()).isEqualTo(UPDATED_PRODUCT_SIZE);
        assertThat(testCart.getProductWeight()).isEqualByComparingTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testCart.getDiscount()).isEqualByComparingTo(UPDATED_DISCOUNT);
        assertThat(testCart.getCouponCode()).isEqualTo(UPDATED_COUPON_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(UUID.randomUUID());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cartDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(UUID.randomUUID());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(UUID.randomUUID());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCartWithPatch() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart using partial update
        Cart partialUpdatedCart = new Cart();
        partialUpdatedCart.setId(cart.getId());

        partialUpdatedCart
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .userId(UPDATED_USER_ID)
            .brandId(UPDATED_BRAND_ID)
            .productId(UPDATED_PRODUCT_ID)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productQuantity(UPDATED_PRODUCT_QUANTITY)
            .shippingCost(UPDATED_SHIPPING_COST)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .productColor(UPDATED_PRODUCT_COLOR)
            .productWeight(UPDATED_PRODUCT_WEIGHT);

        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCart.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCart))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCart.getCreatedUser()).isEqualTo(DEFAULT_CREATED_USER);
        assertThat(testCart.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCart.getUpdatedUser()).isEqualTo(DEFAULT_UPDATED_USER);
        assertThat(testCart.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCart.getShopId()).isEqualTo(DEFAULT_SHOP_ID);
        assertThat(testCart.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
        assertThat(testCart.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testCart.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testCart.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testCart.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testCart.getProductImage()).isEqualTo(DEFAULT_PRODUCT_IMAGE);
        assertThat(testCart.getSubtotal()).isEqualByComparingTo(DEFAULT_SUBTOTAL);
        assertThat(testCart.getShippingCost()).isEqualByComparingTo(UPDATED_SHIPPING_COST);
        assertThat(testCart.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testCart.getTotalMoney()).isEqualByComparingTo(UPDATED_TOTAL_MONEY);
        assertThat(testCart.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCart.getProductColor()).isEqualTo(UPDATED_PRODUCT_COLOR);
        assertThat(testCart.getProductSize()).isEqualTo(DEFAULT_PRODUCT_SIZE);
        assertThat(testCart.getProductWeight()).isEqualByComparingTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testCart.getDiscount()).isEqualByComparingTo(DEFAULT_DISCOUNT);
        assertThat(testCart.getCouponCode()).isEqualTo(DEFAULT_COUPON_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCartWithPatch() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart using partial update
        Cart partialUpdatedCart = new Cart();
        partialUpdatedCart.setId(cart.getId());

        partialUpdatedCart
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
            .subtotal(UPDATED_SUBTOTAL)
            .shippingCost(UPDATED_SHIPPING_COST)
            .tax(UPDATED_TAX)
            .totalMoney(UPDATED_TOTAL_MONEY)
            .createdAt(UPDATED_CREATED_AT)
            .productColor(UPDATED_PRODUCT_COLOR)
            .productSize(UPDATED_PRODUCT_SIZE)
            .productWeight(UPDATED_PRODUCT_WEIGHT)
            .discount(UPDATED_DISCOUNT)
            .couponCode(UPDATED_COUPON_CODE);

        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCart.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCart))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCart.getCreatedUser()).isEqualTo(UPDATED_CREATED_USER);
        assertThat(testCart.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testCart.getUpdatedUser()).isEqualTo(UPDATED_UPDATED_USER);
        assertThat(testCart.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCart.getShopId()).isEqualTo(UPDATED_SHOP_ID);
        assertThat(testCart.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
        assertThat(testCart.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testCart.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testCart.getProductPrice()).isEqualByComparingTo(UPDATED_PRODUCT_PRICE);
        assertThat(testCart.getProductQuantity()).isEqualTo(UPDATED_PRODUCT_QUANTITY);
        assertThat(testCart.getProductImage()).isEqualTo(UPDATED_PRODUCT_IMAGE);
        assertThat(testCart.getSubtotal()).isEqualByComparingTo(UPDATED_SUBTOTAL);
        assertThat(testCart.getShippingCost()).isEqualByComparingTo(UPDATED_SHIPPING_COST);
        assertThat(testCart.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testCart.getTotalMoney()).isEqualByComparingTo(UPDATED_TOTAL_MONEY);
        assertThat(testCart.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCart.getProductColor()).isEqualTo(UPDATED_PRODUCT_COLOR);
        assertThat(testCart.getProductSize()).isEqualTo(UPDATED_PRODUCT_SIZE);
        assertThat(testCart.getProductWeight()).isEqualByComparingTo(UPDATED_PRODUCT_WEIGHT);
        assertThat(testCart.getDiscount()).isEqualByComparingTo(UPDATED_DISCOUNT);
        assertThat(testCart.getCouponCode()).isEqualTo(UPDATED_COUPON_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(UUID.randomUUID());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cartDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(UUID.randomUUID());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(UUID.randomUUID());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeDelete = cartRepository.findAll().size();

        // Delete the cart
        restCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, cart.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
