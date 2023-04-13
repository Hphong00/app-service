package com.app.productservice.web.rest;

import static com.app.productservice.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.productservice.IntegrationTest;
import com.app.productservice.domain.Product;
import com.app.productservice.repository.ProductRepository;
import com.app.productservice.service.dto.ProductDTO;
import com.app.productservice.service.mapper.ProductMapper;
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
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_USER = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_USER = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_USER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ATTRIBUTE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ATTRIBUTE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_ID = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .createdDate(DEFAULT_CREATED_DATE)
            .createdUser(DEFAULT_CREATED_USER)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedUser(DEFAULT_UPDATED_USER)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .category(DEFAULT_CATEGORY)
            .imageUrl(DEFAULT_IMAGE_URL)
            .manufacturerId(DEFAULT_MANUFACTURER_ID)
            .productAttributeId(DEFAULT_PRODUCT_ATTRIBUTE_ID)
            .brandId(DEFAULT_BRAND_ID);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .createdDate(UPDATED_CREATED_DATE)
            .createdUser(UPDATED_CREATED_USER)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedUser(UPDATED_UPDATED_USER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .category(UPDATED_CATEGORY)
            .imageUrl(UPDATED_IMAGE_URL)
            .manufacturerId(UPDATED_MANUFACTURER_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID)
            .brandId(UPDATED_BRAND_ID);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getCreatedUser()).isEqualTo(DEFAULT_CREATED_USER);
        assertThat(testProduct.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testProduct.getUpdatedUser()).isEqualTo(DEFAULT_UPDATED_USER);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProduct.getManufacturerId()).isEqualTo(DEFAULT_MANUFACTURER_ID);
        assertThat(testProduct.getProductAttributeId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_ID);
        assertThat(testProduct.getBrandId()).isEqualTo(DEFAULT_BRAND_ID);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        productRepository.saveAndFlush(product);
        ProductDTO productDTO = productMapper.toDto(product);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdUser").value(hasItem(DEFAULT_CREATED_USER)))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedUser").value(hasItem(DEFAULT_UPDATED_USER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].manufacturerId").value(hasItem(DEFAULT_MANUFACTURER_ID)))
            .andExpect(jsonPath("$.[*].productAttributeId").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_ID)))
            .andExpect(jsonPath("$.[*].brandId").value(hasItem(DEFAULT_BRAND_ID)));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdUser").value(DEFAULT_CREATED_USER))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedUser").value(DEFAULT_UPDATED_USER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.manufacturerId").value(DEFAULT_MANUFACTURER_ID))
            .andExpect(jsonPath("$.productAttributeId").value(DEFAULT_PRODUCT_ATTRIBUTE_ID))
            .andExpect(jsonPath("$.brandId").value(DEFAULT_BRAND_ID));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .createdDate(UPDATED_CREATED_DATE)
            .createdUser(UPDATED_CREATED_USER)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedUser(UPDATED_UPDATED_USER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .category(UPDATED_CATEGORY)
            .imageUrl(UPDATED_IMAGE_URL)
            .manufacturerId(UPDATED_MANUFACTURER_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID)
            .brandId(UPDATED_BRAND_ID);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getCreatedUser()).isEqualTo(UPDATED_CREATED_USER);
        assertThat(testProduct.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testProduct.getUpdatedUser()).isEqualTo(UPDATED_UPDATED_USER);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProduct.getManufacturerId()).isEqualTo(UPDATED_MANUFACTURER_ID);
        assertThat(testProduct.getProductAttributeId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_ID);
        assertThat(testProduct.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).manufacturerId(UPDATED_MANUFACTURER_ID);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getCreatedUser()).isEqualTo(DEFAULT_CREATED_USER);
        assertThat(testProduct.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testProduct.getUpdatedUser()).isEqualTo(DEFAULT_UPDATED_USER);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProduct.getManufacturerId()).isEqualTo(UPDATED_MANUFACTURER_ID);
        assertThat(testProduct.getProductAttributeId()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_ID);
        assertThat(testProduct.getBrandId()).isEqualTo(DEFAULT_BRAND_ID);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .createdDate(UPDATED_CREATED_DATE)
            .createdUser(UPDATED_CREATED_USER)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedUser(UPDATED_UPDATED_USER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .category(UPDATED_CATEGORY)
            .imageUrl(UPDATED_IMAGE_URL)
            .manufacturerId(UPDATED_MANUFACTURER_ID)
            .productAttributeId(UPDATED_PRODUCT_ATTRIBUTE_ID)
            .brandId(UPDATED_BRAND_ID);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getCreatedUser()).isEqualTo(UPDATED_CREATED_USER);
        assertThat(testProduct.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testProduct.getUpdatedUser()).isEqualTo(UPDATED_UPDATED_USER);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProduct.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProduct.getManufacturerId()).isEqualTo(UPDATED_MANUFACTURER_ID);
        assertThat(testProduct.getProductAttributeId()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_ID);
        assertThat(testProduct.getBrandId()).isEqualTo(UPDATED_BRAND_ID);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(UUID.randomUUID());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId().toString()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
