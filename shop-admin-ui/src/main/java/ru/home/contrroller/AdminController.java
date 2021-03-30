package ru.home.contrroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.home.persist.repo.CategoryRepository;
import ru.home.persist.repo.ProductRepository;
import ru.home.persist.repo.RoleRepository;
import ru.home.persist.repo.UserRepository;

@Controller
@RequestMapping("/")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public AdminController(UserRepository userRepository, RoleRepository roleRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String indexPage() {
        return "index";
    }

    @GetMapping("users")
    public String userPage(Model model) {
        logger.info("User page requested");

        model.addAttribute("users", userRepository.findAll());
        return "user";
    }

    @GetMapping("roles")
    public String rolePage(Model model) {
        logger.info("Role page requested");

        model.addAttribute("roles", roleRepository.findAll());
        return "role";
    }

    @GetMapping("products")
    public String productPage(Model model) {
        logger.info("Product page requested");

        model.addAttribute("products", productRepository.findAll());
        return "product";
    }

    @GetMapping("categories")
    public String categoryPage(Model model) {
        logger.info("Category page requested");

        model.addAttribute("categories", categoryRepository.findAll());
        return "category";
    }
}
