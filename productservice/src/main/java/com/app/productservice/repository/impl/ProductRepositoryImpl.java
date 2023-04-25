package com.app.productservice.repository.impl;

import com.app.productservice.core.BaseDto;
import com.app.productservice.core.BaseRepository;
import com.app.productservice.core.Datatable;
import com.app.productservice.service.dto.ProductSearchDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductRepositoryImpl extends BaseRepository {
    private final Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class);
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private BaseDto sqlQuery(ProductSearchDTO productSearchDTO) {
        BaseDto result = new BaseDto();
        Map<String, Object> parameters = new HashMap<>();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" SELECT  *");
        sqlBuilder.append(" FROM Product ");
        sqlBuilder.append(" WHERE 1 =1  ");
        result.setSqlQuery(sqlBuilder.toString());
        result.setParameters(parameters);
        return result;
    }

    public Datatable getListProduct(ProductSearchDTO productSearchDTO) {
        BaseDto baseDto = sqlQuery(productSearchDTO);
        return getListDataTableBySqlQuery(baseDto.getSqlQuery(), baseDto.getParameters(), productSearchDTO.getCurrentPage(), productSearchDTO.getPageSize(), ProductSearchDTO.class, productSearchDTO.getSortName(), productSearchDTO.getSortType());
    }
}
