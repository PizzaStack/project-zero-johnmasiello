-- Table: public.application

-- DROP TABLE public.application;

CREATE TABLE public.application
(
    id int NOT NULL DEFAULT nextval('application_id_seq'::regclass),
    account_name text COLLATE pg_catalog."default" NOT NULL,
    customer_id int NOT NULL,
    joint_customer_id int NOT NULL DEFAULT '-1'::integer,
    joint_customer_ssn character varying(9) COLLATE pg_catalog."default" NOT NULL,
    type smallint NOT NULL,
    CONSTRAINT application_pkey PRIMARY KEY (id),
    CONSTRAINT unique_customer_with_account UNIQUE (customer_id, account_name)
,
    CONSTRAINT customer_id_fk FOREIGN KEY (id)
        REFERENCES public.personal_info (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT ssn_exact_length CHECK (char_length(joint_customer_ssn::text) = 9) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.application
    OWNER to utamyrdsjp;
	
	
CREATE SEQUENCE public.application_id_seq;

ALTER SEQUENCE public.application_id_seq
    OWNER TO utamyrdsjp;