INSERT INTO forum1._user (nickname, pass, email, docs) VALUES
('Félix',  'pass', 'felix@mail.com',  4),
('Arthur', 'pass', 'arthur@mail.com', 3),
('Jean',   'pass', 'jean@mail.com',   3),
('Thomas', 'pass', 'thomas@mail.com', 3),
('Alex',   'pass', 'alex@mail.com',   1);

-- iddoc auto (SERIAL) : insérés dans l'ordre 1 à 14
INSERT INTO forum1._document (content, author) VALUES
('Bonjour tout le monde !', 'Félix'),                                  -- 1
('Quelqu''un jouera encore au foot cette année ?', 'Arthur'),          -- 2
('Salut Félix ! Tu vas bien ?', 'Arthur'),                             -- 3
('Oui, merci. Et toi ?', 'Félix'),                                     -- 4
('Tiens, Félix, tu es rentré de vacances ?', 'Jean'),                  -- 5
('Bonjour Félix ! Bonjour à tous !', 'Thomas'),                        -- 6
('Salut Thomas !', 'Jean'),                                            -- 7
('Hello Thomas. Tu reprends le foot cette année ?', 'Arthur'),         -- 8
('Oui, moi !', 'Jean'),                                                -- 9
('Pas moi.', 'Thomas'),                                                -- 10
('Non, je vais me mettre au handball, je crois.', 'Thomas'),           -- 11
('Ah ? Dommage, on s''amusait bien ensemble.', 'Félix'),               -- 12
('Moi aussi.', 'Félix'),                                               -- 13
('Et moi !', 'Alex');                                                  -- 14

INSERT INTO forum1._post (iddoc) VALUES (1), (2);

INSERT INTO forum1._comment (iddoc, ref) VALUES
(3, 1),
(4, 3),
(5, 1),
(6, 1),
(7, 6),
(8, 6),
(9, 2),
(10, 2),
(11, 8),
(12, 8),
(13, 2),
(14, 2);

SELECT * FROM forum1._user;
SELECT * FROM forum1._document WHERE author = 'Félix';
SELECT iddoc FROM forum1._comment WHERE ref = 1;
WITH RECURSIVE hierarchie AS(
    SELECT iddoc FROM forum1._comment WHERE ref = 1
    UNION
    SELECT cv.iddoc FROM forum1._comment cv INNER JOIN hierarchie h ON cv.ref = h.iddoc
) SELECT iddoc FROM hierarchie;
