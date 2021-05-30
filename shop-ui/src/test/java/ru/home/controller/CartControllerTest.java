package ru.home.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.home.controller.repr.ProductRepr;
import ru.home.persist.model.Brand;
import ru.home.persist.model.Category;
import ru.home.persist.model.Product;
import ru.home.persist.repo.BrandRepository;
import ru.home.persist.repo.CategoryRepository;
import ru.home.persist.repo.ProductRepository;
import ru.home.service.CartService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CartControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

//    @MockBean // аннотация для замены реального бина на мокнутый
//    private CartService cartService;

    private Product product;

    @BeforeEach
    public void init() {
        Brand brand = brandRepository.save(new Brand("brand"));
        Category category = categoryRepository.save(new Category("Category"));
        product = productRepository.save(new Product("Product", new BigDecimal(12345), category, brand));
    }

    @Test
    public void testAddToCart() throws Exception {
        MockHttpSession session = new MockHttpSession(); // проверка картсервиса через мок-сессию
        mvc.perform(post("/cart")
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("productId", Long.toString(product.getId()))
                .param("qty", "3")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/cart"));

        session.getAttributeNames();
        CartService cartService = (CartService) session.getAttribute("scopedTarget.cartServiceImpl");
        assertFalse(cartService.getLineItems().isEmpty());
        assertEquals(1, cartService.getLineItems().size());
        assertEquals(3, cartService.getLineItems().get(0).getQty());
        assertEquals(0, product.getPrice().multiply(new BigDecimal(3)).compareTo(cartService.getSubTotal()));

//        verify(cartService).addProductQty(
//                /*any(ProductRepr.class)*/argThat(argument ->
//                argument.getId().equals(product.getId()) && argument.getName().equals(product.getName())),
//                any(),
//                any(),
//                /*anyInt()*/eq(3)); /* метод для проверки через Мокито в случае, если какой-то сервис не работает
//                корректно в рамках интеграционного теста */


    }
}
