DROP SCHEMA IF EXISTS forum1 CASCADE;

CREATE SCHEMA forum1;

CREATE TABLE forum1._user(
  nickname VARCHAR(50) CONSTRAINT _user_pk PRIMARY KEY,
  pass VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  docs INT
);

CREATE TABLE forum1._document(
  iddoc SERIAL CONSTRAINT _document_pk PRIMARY KEY,
  content VARCHAR(500) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT now(),
  author VARCHAR(50) NOT NULL,
  CONSTRAINT document_fk_author FOREIGN KEY (author) REFERENCES forum1._user(nickname)
);

CREATE TABLE forum1._post(
  iddoc INTEGER,
  CONSTRAINT _post_pk PRIMARY KEY (iddoc),
  CONSTRAINT _post_fk_heritage FOREIGN KEY (iddoc) REFERENCES forum1._document(iddoc)
);

CREATE TABLE forum1._comment(
  iddoc INTEGER,
  ref INTEGER NOT NULL,
  CONSTRAINT _comment_pk PRIMARY KEY (iddoc),
  CONSTRAINT _comment_fk_heritage FOREIGN KEY (iddoc) REFERENCES forum1._document(iddoc),
  CONSTRAINT _comment_fk_ref FOREIGN KEY (ref) REFERENCES forum1._document(iddoc),
  CONSTRAINT _comment_ck_iddoc_ref CHECK (iddoc <> ref)
);

INSERT INTO forum1._user VALUES ('Félix', 'azerty123', 'felix@bdd.com');
INSERT INTO forum1._user VALUES ('Arthur', 'azerty_123', 'arthur@bdd.com');
INSERT INTO forum1._user VALUES ('Jean', 'azerty!123', 'jean@bdd.com');
INSERT INTO forum1._user VALUES ('Thomas', 'azerty&123', 'thomas@bdd.com');
INSERT INTO forum1._user VALUES ('Alex', 'azerty@123', 'alex@bdd.com');

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
    SELECT iddoc FROM forum1._comment WHERE ref = 1 UNION SELECT cv.iddoc FROM forum1._comment cv INNER JOIN hierarchie h ON cv.ref = h.iddoc)
SELECT iddoc FROM hierarchie;

create or replace function forum1._test_sql(p_iddoc INT)
  returns table (iddoc INT, ref INT) AS $$
    select c.iddoc, c.ref
    from forum1._comment c
    where c.ref = p_iddoc;
  $$ language 'sql';

create or replace function forum1.test_plpgsql(p_iddoc INT)
  returns table (iddoc INT, ref INT) as $$
  begin
    return query
      select c.iddoc, c.ref
      from forum1._comment c
      where c.ref = p_iddoc;
  end;
  $$ language 'plpgsql';

INSERT INTO forum1._user(nickname, pass, email) VALUES ('Anonymous', 'anonymous', 'anonymous@anonymous.com');

UPDATE forum1._document SET author='Anonymous' WHERE author='Alex';
DELETE FROM forum1._user WHERE nickname='Alex';
