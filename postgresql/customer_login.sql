-- Table: public.customer_login

-- DROP TABLE public.customer_login;

CREATE TABLE public.customer_login
(
	username text COLLATE pg_catalog."default" NOT NULL,
	password text COLLATE pg_catalog."default" NOT NULL,
	customer_id int NOT NULL DEFAULT nextval('customer_login_customer_id_seq'::regclass),
	CONSTRAINT username_pk PRIMARY KEY (username),
	CONSTRAINT unique_customer_id UNIQUE (customer_id)
,
	CONSTRAINT unique_username UNIQUE (username)
,
	CONSTRAINT minimum_length_username CHECK (char_length(username) >= 6),
	CONSTRAINT minimum_length_password CHECK (char_length(password) >= 6)
)
WITH (
	OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.customer_login
	OWNER to utamyrdsjp;
	
-- SEQUENCE: public.customer_login_customer_id_seq

-- DROP SEQUENCE public.customer_login_customer_id_seq;

CREATE SEQUENCE public.customer_login_customer_id_seq;

ALTER SEQUENCE public.customer_login_customer_id_seq
    OWNER TO utamyrdsjp;