ALTER TABLE Users
ADD login VARCHAR(50)
AFTER id;
ALTER TABLE Users
ADD password VARCHAR(50)
AFTER login;

UPDATE users
SET login='ama', password='123'
where id=1;
UPDATE users
SET login='pav', password='456'
where id=2;

ALTER TABLE Users
ADD CONSTRAINT login_unique UNIQUE (login);
ALTER TABLE Users
MODIFY login VARCHAR(50) NOT NULL;
ALTER TABLE Users
MODIFY password VARCHAR(50) NOT NULL;

