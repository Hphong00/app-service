package com.app.orderservice.service.mapper;

import com.app.orderservice.domain.Order;
import com.app.orderservice.service.dto.OrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {}
