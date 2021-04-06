package ru.home.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.home.error.SWWException;
import ru.home.service.ProductForUserService;

@Controller
@RequestMapping("product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductForUserService productForUserService;

    @Autowired
    public ProductController(ProductForUserService productForUserService) {
        this.productForUserService = productForUserService;
    }

    @GetMapping
    public String productListPage(Model model) {
        logger.info("Product list page");

       model.addAttribute("userProducts", productForUserService.findAll());

        return "categories-left-sidebar";
    }

    @GetMapping("/{id}")
    public String productItem(@PathVariable("id") Long id, Model model) {
        logger.info("Product page");

        model.addAttribute("productDetail", productForUserService.findById(id).orElseThrow(SWWException::new));

        return "product-details";
    }
}
