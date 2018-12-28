-- Table: public.account_info

-- DROP TABLE public.account_info;

CREATE TABLE public.account_info
(
    id int NOT NULL DEFAULT nextval('account_info_id_seq'::regclass),
    account_name text COLLATE pg_catalog."default" NOT NULL,
    customer_id int NOT NULL,
    joint_customer_id int NOT NULL DEFAULT '-1'::integer,
    date_opened date NOT NULL,
    date_closed date NOT NULL,
    type smallint NOT NULL,
    status smallint NOT NULL,
    balance double precision NOT NULL DEFAULT 0,
    approved_one_id text COLLATE pg_catalog."default" NOT NULL,
    approved_two_id text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT account_info_pkey PRIMARY KEY (id),
    CONSTRAINT unique_account_name_and_owners UNIQUE (account_name, customer_id, joint_customer_id)
,
    CONSTRAINT customer_id_fk FOREIGN KEY (customer_id)
        REFERENCES public.personal_info (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT nonnegative_balance CHECK (balance >= 0::double precision) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.account_info
    OWNER to utamyrdsjp;

-- Index: fki_customer_id_fk

-- DROP INDEX public.fki_customer_id_fk;

CREATE INDEX fki_customer_id_fk
    ON public.account_info USING btree
    (customer_id)
    TABLESPACE pg_default;

-- SEQUENCE: public.account_info_id_seq

-- DROP SEQUENCE public.account_info_id_seq;

CREATE SEQUENCE public.account_info_id_seq;

ALTER SEQUENCE public.account_info_id_seq
    OWNER TO utamyrdsjp;