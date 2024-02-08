--------------------------------------------------------------
--                  INSERT PROFILES                         --
--------------------------------------------------------------
INSERT INTO users --passwd = 123
(id, visibility, first_name, last_name, email, password, status,
 created_date, roles)
SELECT '550e8400-e29b-41d4-a716-446655440000',
       true,
       'Ali',
       'Aliyev',
       'myshukurov@gmail.com',
       'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',
       'ACTIVE',
       now(),
       array ['SUPER_ADMIN', 'ADMIN', 'MODERATOR', 'USER']::varchar[]
WHERE NOT EXISTS(SELECT id
                 FROM users
                 WHERE id = '550e8400-e29b-41d4-a716-446655440000');