ALTER TABLE Users
ADD login VARCHAR(50);
ALTER TABLE Users
ADD password VARCHAR(50);

UPDATE Users
SET login='ama', password='123'
where id=1;
UPDATE Users
SET login='pav', password='456'
where id=2;

ALTER TABLE Users
ADD CONSTRAINT login_unique UNIQUE (login);
ALTER TABLE Users
ALTER COLUMN login SET NOT NULL;
ALTER TABLE Users
ALTER COLUMN password SET NOT NULL;

