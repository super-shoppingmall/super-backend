CREATE TABLE `member` (
                          `member_id` integer PRIMARY KEY,
                          `email` varchar(255),
                          `password` varchar(255),
                          `phone` integer,
                          `address` varchar(255),
                          `gender` varchar(255),
                          `about_me` varchar(255),
                          `profile_image` varchar(255),
                          `created_at` timestamp,
                          `updated_at` timestamp
);

CREATE TABLE `product` (
                           `product_id` integer PRIMARY KEY,
                           `product_name` varchar(255),
                           `category_name` varchar(255),
                           `product_price` integer,
                           `product_quantity` integer,
                           `product_images` varchar(255),
                           `product_detail` varchar(255),
                           `product_on_sale` boolean,
                           `created_at` timestamp,
                           `updated_at` timestamp
);

CREATE TABLE `cart` (
                        `cart_id` integer PRIMARY KEY,
                        `member_id` integer,
                        `product_id` integer,
                        `cart_count` integer,
                        `cart_total_price` integer
);

CREATE TABLE `order` (
                         `order_id` integer PRIMARY KEY,
                         `member_id` integer,
                         `product_id` integer,
                         `address` varchar(255),
                         `order_state` varchar(255),
                         `order_total_price` integer,
                         `ordered_at` timestamp
);

CREATE TABLE `paymoney` (
                            `paymoney_id` integer PRIMARY KEY,
                            `member_id` integer,
                            `paymoney` integer
);

CREATE TABLE `payment` (
                           `payment_id` integer PRIMARY KEY,
                           `order_id` integer,
                           `paid_at` timestamp
);

ALTER TABLE `order` ADD FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`);
