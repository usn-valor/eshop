package ru.home.controller.repr;

import java.math.BigDecimal;
import java.util.List;

public class ProductRepr {

    private Long id;

    private String name;

    private BigDecimal price;

    private Long pictureId;

    private List<Long> pictureIds;

    public ProductRepr() {
    }

    public ProductRepr(Long id, String name, BigDecimal price, Long pictureId, List<Long> pictureIds) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pictureId = pictureId;
        this.pictureIds = pictureIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public void setPictureId(Long pictureId) {
        this.pictureId = pictureId;
    }

    public List<Long> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Long> pictureIds) {
        this.pictureIds = pictureIds;
    }
}
