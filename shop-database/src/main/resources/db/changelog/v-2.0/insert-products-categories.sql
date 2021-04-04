INSERT INTO `products` (`product_name`, `description`, `price`)
    VALUE   ('Молоко', 'Простоквашино', 100.0),
        ('Батон', 'Нарезной', 35.0);
GO

INSERT INTO `categories` (`name`)
VALUE ('Молочка'), ('Выпечка');
GO

INSERT INTO `products_categories`(`product_id`, `category_id`)
SELECT (SELECT id FROM `products` WHERE `product_name` = 'Молоко'), (SELECT id FROM `categories` WHERE `name` = 'Молочка')
UNION ALL
SELECT (SELECT id FROM `products` WHERE `product_name` = 'Батон'), (SELECT id FROM `categories` WHERE `name` = 'Выпечка');
GO
