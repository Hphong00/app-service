package com.app.orderservice.service;

import com.app.orderservice.domain.Order;
import com.app.orderservice.repository.OrderRepository;
import com.app.orderservice.service.dto.OrderDTO;
import com.app.orderservice.service.mapper.OrderMapper;
import com.app.orderservice.web.rest.errors.BadRequestAlertException;
import com.app.proxyclient.product.service.ProductClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String ENTITY_NAME = "orderserviceOrder";

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate, OrderMapper orderMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.orderMapper = orderMapper;
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderDTO save(HttpServletRequest request, OrderDTO orderDTO) throws JsonProcessingException {
        log.debug("Request to save Order : {}", orderDTO);
        ProductClientService productClientService = new ProductClientService();
        if (productClientService.findProductById(request, orderDTO.getProductId()) == null) {
            throw new BadRequestAlertException("Invalid order", ENTITY_NAME, "ordernull");
        }
        if (productClientService.updateProductById(request, orderDTO.getProductId(), orderDTO.getProductQuantity(), 2) == null) {
            throw new BadRequestAlertException("Invalid updateProductById", ENTITY_NAME, "ordernull");
        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        String orderJson = objectMapper.writeValueAsString(orderDTO);
//        Message<String> message = MessageBuilder.withPayload(orderJson).build();
        kafkaTemplate.send("order", orderDTO.getUserId());
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

//    public void sendMessage(Object message) {
//        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("order", message);
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                System.out.println("Sent message=[" + message +
//                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
//            } else {
//                System.out.println("Unable to send message=[" +
//                    message + "] due to : " + ex.getMessage());
//            }
//        });
//    }


    /**
     * Update a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderDTO update(OrderDTO orderDTO) {
        log.debug("Request to update Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Partially update a order.
     *
     * @param orderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);

                return existingOrder;
            })
            .map(orderRepository::save)
            .map(orderMapper::toDto);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(UUID id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
