CREATE TABLE `tb_order` (
                            `seq` int(11) NOT NULL AUTO_INCREMENT,
                            `userId` varchar(50) NOT NULL,
                            `name` varchar(100) NOT NULL,
                            `email` varchar(100) NOT NULL,
                            `phone` varchar(20) DEFAULT NULL,
                            `address` varchar(255) DEFAULT NULL,
                            `regDate` datetime DEFAULT current_timestamp(),
                            `editDate` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_c;


CREATE TABLE `tb_product` (
                              `seq` varchar(50) NOT NULL,
                              `order_seq` varchar(50) NOT NULL,
                              `name` varchar(100) NOT NULL,
                              `price` decimal(10,2) NOT NULL,
                              `regDate` datetime DEFAULT current_timestamp(),
                              `editDate` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                              PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;