CREATE TABLE Users (
 	 id INT AUTO_INCREMENT PRIMARY KEY,
     firstName VARCHAR(50) NOT NULL,
     lastName VARCHAR(50) NOT NULL,
     salary NUMERIC NOT NULL,
     dateOfBirth DATE NOT NULL
 ) DEFAULT CHARSET=utf8mb4;