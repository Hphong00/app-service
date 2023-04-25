package com.app.productservice.service;

import com.app.productservice.core.BaseDto;
import com.app.productservice.core.Datatable;
import com.app.productservice.core.constant.WebCoApiClientConstants;
import com.app.productservice.core.utils.WebCoUtils;
import com.app.productservice.core.exception.WebCoRuntimeException;
import com.app.productservice.domain.Product;
import com.app.productservice.repository.ProductRepository;
import com.app.productservice.repository.impl.ProductRepositoryImpl;
import com.app.productservice.service.dto.ProductDTO;
import com.app.productservice.service.dto.ProductSearchDTO;
import com.app.productservice.service.mapper.ProductMapper;

import java.time.Instant;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ProductMapper productMapper;

    @Autowired
    private WebCoUtils webCoUtils;

    public ProductService(ProductRepository productRepository, ProductRepositoryImpl productRepositoryImpl, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productRepositoryImpl = productRepositoryImpl;
        this.productMapper = productMapper;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDto(product);
    }

    /**
     * Update a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO update(HttpServletRequest request, UUID id, ProductDTO productDTO) {
        log.debug("Request to update Product : {}", productDTO);
        Product product = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Instant instant = calendar.toInstant();
        try {
            product = productRepository.getById(id);
            product.setDescription(StringUtils.isNotBlank(productDTO.getDescription()) ? productDTO.getDescription().trim() : product.getDescription());
            product.setBrandId(StringUtils.isNotBlank(productDTO.getBrandId()) ? productDTO.getBrandId().trim() : product.getBrandId());
            product.setCategory(StringUtils.isNotBlank(productDTO.getCategory()) ? productDTO.getCategory().trim() : product.getCategory());
            product.setImageUrl(StringUtils.isNotBlank(productDTO.getImageUrl()) ? productDTO.getImageUrl().trim() : product.getImageUrl());
            product.setManufacturerId(StringUtils.isNotBlank(productDTO.getManufacturerId()) ? productDTO.getManufacturerId().trim() : product.getManufacturerId());
            product.setName(StringUtils.isNotBlank(productDTO.getName()) ? productDTO.getName().trim() : product.getName());
            product.setQuantity((productDTO.getQuantity() != null && productDTO.getQuantity() > 0) ? productDTO.getQuantity() : product.getQuantity());
            product.setPrice((productDTO.getPrice() != null) ? productDTO.getPrice() : product.getPrice());
            product.setUpdatedUser(StringUtils.isNotBlank(productDTO.getUpdatedUser()) ? productDTO.getUpdatedUser().trim() : product.getUpdatedUser());
            product.setProductAttributeId(StringUtils.isNotBlank(productDTO.getProductAttributeId()) ? productDTO.getProductAttributeId().trim() : product.getProductAttributeId());
            product.setUpdatedDate(instant);
            product = productRepository.save(product);
        } catch (Exception ex) {
            log.error("update ProductDTO ERROR:" + ex.getMessage());
            throw new WebCoRuntimeException(WebCoApiClientConstants.ERR_SYSTEM, "Không thành công");
        }
        return productMapper.toDto(product);
    }

    /**
     * Partially update a product.
     *
     * @param productDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {
        log.debug("Request to partially update Product : {}", productDTO);

        return productRepository
            .findById(productDTO.getId())
            .map(existingProduct -> {
                productMapper.partialUpdate(existingProduct, productDTO);

                return existingProduct;
            })
            .map(productRepository::save)
            .map(productMapper::toDto);
    }

    /**
     * Get all the products.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(UUID id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }

    public Datatable getListProduct(ProductSearchDTO productSearchDTO) {
        log.debug("Request to get list Product : {}", productSearchDTO);
        return productRepositoryImpl.getListProduct(productSearchDTO);
    }
}
