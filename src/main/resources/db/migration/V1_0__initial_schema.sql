CREATE TABLE Users (
 	 id serial PRIMARY KEY,
     firstName VARCHAR(50) NOT NULL,
     lastName VARCHAR(50) NOT NULL,
     salary NUMERIC NOT NULL,
     dateOfBirth DATE NOT NULL
 );