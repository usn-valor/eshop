package ru.home.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.home.controller.repr.ProductRepr;
import ru.home.service.model.LineItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    private final Map<LineItem, Integer> lineItems;

    public CartServiceImpl() {
        this.lineItems = new ConcurrentHashMap<>();
    }

    @JsonCreator
    public CartServiceImpl(@JsonProperty("lineItems") List<LineItem> lineItems) {
        this.lineItems = lineItems.stream().collect(Collectors.toMap(li -> li, LineItem::getQty));
    }

    @Override
    public void addProductQty(ProductRepr productRepr, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productRepr, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + qty);
    }

    @Override
    public void removeProductQty(ProductRepr productRepr, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productRepr, color, material);
        int currentQty = lineItems.getOrDefault(lineItem, 0);
        if (currentQty - qty > 0) {
            lineItems.put(lineItem, currentQty - qty);
        } else {
            lineItems.remove(lineItem);
        }
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQty);
        return new ArrayList<>(lineItems.keySet());
    }

    /*@JsonIgnore
    @Override
    public BigDecimal getSubTotal() {
        lineItems.forEach(LineItem::setQty);
        return lineItems.keySet().stream()
                .map(LineItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }*/

    @Override
    public BigDecimal getSubTotal() {
        BigDecimal sum = new BigDecimal(0);
        for (LineItem li: getLineItems()) {
            sum = sum.add(li.getTotal());
        }
        return sum;
    }

    @Override
    public void removeProduct(ProductRepr productRepr, String color, String material) {
        LineItem lineItem = new LineItem(productRepr, color, material);
        lineItems.remove(lineItem);
    }

    @Override
    public void updateAllQty(Map<Long, Integer> productIdQtyMap) {
        for (Map.Entry<Long, Integer> pair: productIdQtyMap.entrySet()) {
            Long l = Long.parseLong(String.valueOf(pair.getKey()));
            int i = Integer.parseInt(String.valueOf(pair.getValue()));
            for (Map.Entry<LineItem, Integer> pair1: lineItems.entrySet()) {
                if (l.equals(pair1.getKey().getProductId())) {
                    if (i == 0) {
                        lineItems.remove(pair1.getKey());
                        break;
                    }
                    lineItems.replace(pair1.getKey(), i);
                    break;
                }
            }
        }
    }
}
