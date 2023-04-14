package com.app.orderservice.service.mapper;

import com.app.orderservice.domain.Cart;
import com.app.orderservice.service.dto.CartDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDTO, Cart> {}
