INSERT INTO `tb_order` (`userId`, `name`, `email`, `phone`, `address`, `regDate`, `editDate`)
VALUES
    ('user123', 'John Doe', 'john@example.com', '1234567890', '123 Main St, City', '2024-05-03 10:00:00', '2024-05-03 10:00:00'),
    ('user456', 'Jane Smith', 'jane@example.com', '9876543210', '456 Elm St, Town', '2024-05-03 10:05:00', '2024-05-03 10:05:00'),
    ('user789', 'Alice Johnson', 'alice@example.com', '5551234567', '789 Oak St, Village', '2024-05-03 10:10:00', '2024-05-03 10:10:00'),
    ('user101112', 'Bob Brown', 'bob@example.com', '9998887777', '1011 Pine St, Countryside', '2024-05-03 10:15:00', '2024-05-03 10:15:00'),
    ('user131415', 'Emily Wilson', 'emily@example.com', '1112223333', '1314 Maple St, Suburb', '2024-05-03 10:20:00', '2024-05-03 10:20:00'),
    ('user161718', 'Michael Garcia', 'michael@example.com', '4445556666', '1617 Cedar St, Downtown', '2024-05-03 10:25:00', '2024-05-03 10:25:00'),
    ('user192021', 'Sarah Martinez', 'sarah@example.com', '7778889999', '1920 Walnut St, Beachside', '2024-05-03 10:30:00', '2024-05-03 10:30:00'),
    ('user222324', 'David Lee', 'david@example.com', '2223334444', '2223 Birch St, Lakeside', '2024-05-03 10:35:00', '2024-05-03 10:35:00'),
    ('user252627', 'Olivia Rodriguez', 'olivia@example.com', '8889990000', '2526 Cherry St, Mountain', '2024-05-03 10:40:00', '2024-05-03 10:40:00'),
    ('user282930', 'Daniel Hernandez', 'daniel@example.com', '3334445555', '2829 Pineapple St, Riverfront', '2024-05-03 10:45:00', '2024-05-03 10:45:00');

INSERT INTO `tb_product` (`seq`, `order_seq`, `name`, `price`, `regDate`, `editDate`)
VALUES
    ('1', '1', 'Product A', 29.99, '2024-05-03 11:00:00', '2024-05-03 11:00:00'),
    ('2', '1', 'Product B', 19.99, '2024-05-03 11:05:00', '2024-05-03 11:05:00'),
    ('3', '2', 'Product C', 39.99, '2024-05-03 11:10:00', '2024-05-03 11:10:00'),
    ('4', '2', 'Product D', 49.99, '2024-05-03 11:15:00', '2024-05-03 11:15:00'),
    ('5', '2', 'Product E', 59.99, '2024-05-03 11:20:00', '2024-05-03 11:20:00'),
    ('6', '2', 'Product F', 69.99, '2024-05-03 11:25:00', '2024-05-03 11:25:00'),
    ('7', '3', 'Product G', 79.99, '2024-05-03 11:30:00', '2024-05-03 11:30:00'),
    ('8', '3', 'Product H', 89.99, '2024-05-03 11:35:00', '2024-05-03 11:35:00'),
    ('9', '5', 'Product I', 99.99, '2024-05-03 11:40:00', '2024-05-03 11:40:00'),
    ('10', '6', 'Product J', 109.99, '2024-05-03 11:45:00', '2024-05-03 11:45:00');
