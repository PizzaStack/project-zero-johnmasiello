-- Table: public.personal_info

-- DROP TABLE public.personal_info;

CREATE TABLE public.personal_info
(
    customer_id int NOT NULL,
    first_name text COLLATE pg_catalog."default" NOT NULL,
    last_name text COLLATE pg_catalog."default" NOT NULL,
    middle_initial character(1) COLLATE pg_catalog."default" NOT NULL,
    dob date NOT NULL,
	ssn character varying(9) COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(9) COLLATE pg_catalog."default" NOT NULL,
    beneficiary text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT personal_info_pkey PRIMARY KEY (customer_id),
    CONSTRAINT unique_email UNIQUE (email)
,
    CONSTRAINT unique_ssn UNIQUE (ssn)
,
    CONSTRAINT customer_id_fk FOREIGN KEY (customer_id)
        REFERENCES public.customer_login (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT ssn_exact_length CHECK (char_length(ssn::text) = 9) NOT VALID,
    CONSTRAINT formatted_email CHECK (email ~~ '%@%'::text) NOT VALID,
    CONSTRAINT phone_number_exact_length CHECK (char_length(phone_number::text) = 10) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.personal_info
    OWNER to utamyrdsjp;