package ru.home.service;

import ru.home.controller.repr.ProductRepr;
import ru.home.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CartService {

    void addProductQty(ProductRepr productRepr, String color, String material, int qty);

    void removeProductQty(ProductRepr productRepr, String color, String material, int qty);

    void removeProduct(ProductRepr productRepr, String color, String material);

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();

    void updateAllQty(Map<Long, Integer> productIdQtyMap);
}
