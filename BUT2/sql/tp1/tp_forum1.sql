DROP SCHEMA IF EXISTS forum1;

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


