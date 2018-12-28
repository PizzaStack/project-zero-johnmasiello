CREATE SEQUENCE IF NOT EXISTS public.customer_login_customer_id_seq;

ALTER SEQUENCE public.customer_login_customer_id_seq
    OWNER TO utamyrdsjp;
	
	
CREATE SEQUENCE IF NOT EXISTS public.account_info_id_seq;

ALTER SEQUENCE public.account_info_id_seq
    OWNER TO utamyrdsjp;
	
	
CREATE SEQUENCE IF NOT EXISTS public.application_id_seq;

ALTER SEQUENCE public.application_id_seq
    OWNER TO utamyrdsjp;


CREATE TABLE IF NOT EXISTS public.customer_login
(
	username text COLLATE pg_catalog."default" NOT NULL,
	password text COLLATE pg_catalog."default" NOT NULL,
	customer_id integer NOT NULL DEFAULT nextval('customer_login_customer_id_seq'::regclass),
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
	
ALTER SEQUENCE public.customer_login_customer_id_seq
    OWNED BY public.customer_login.customer_id;
	
	
CREATE TABLE IF NOT EXISTS public.personal_info
(
    customer_id integer NOT NULL,
    first_name text COLLATE pg_catalog."default" NOT NULL,
    last_name text COLLATE pg_catalog."default" NOT NULL,
    middle_initial character(1) COLLATE pg_catalog."default" NOT NULL,
    dob date NOT NULL,
	ssn character varying(9) COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(10) COLLATE pg_catalog."default" NOT NULL,
    beneficiary text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT personal_info_pkey PRIMARY KEY (customer_id),
    CONSTRAINT unique_email UNIQUE (email)
,
    CONSTRAINT unique_ssn UNIQUE (ssn)
,
    CONSTRAINT customer_id_in_personal_info_fk FOREIGN KEY (customer_id)
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
																						  
	
CREATE TABLE IF NOT EXISTS public.application
(
    id integer NOT NULL DEFAULT nextval('application_id_seq'::regclass),
    account_name text COLLATE pg_catalog."default" NOT NULL,
    customer_id integer NOT NULL,
    joint_customer_id integer NOT NULL DEFAULT '-1'::integer,
    joint_customer_ssn character varying(9) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    type smallint NOT NULL,
    CONSTRAINT application_pkey PRIMARY KEY (id),
    CONSTRAINT unique_customer_with_account UNIQUE (customer_id, account_name)
,
    CONSTRAINT customer_id_in_application_fk FOREIGN KEY (customer_id)
        REFERENCES public.personal_info (customer_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT ssn_exact_length CHECK (char_length(joint_customer_ssn::text) = ANY (ARRAY[0, 9])) NOT VALID
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.application
    OWNER to utamyrdsjp;
																						  
ALTER SEQUENCE public.application_id_seq
    OWNED BY public.application.id;
	
																						  
CREATE TABLE IF NOT EXISTS public.account_info
(
    id integer NOT NULL DEFAULT nextval('account_info_id_seq'::regclass),
    account_name text COLLATE pg_catalog."default" NOT NULL,
    customer_id integer NOT NULL,
    joint_customer_id integer NOT NULL DEFAULT '-1'::integer,
    date_opened date NOT NULL DEFAULT CURRENT_DATE,
    date_closed date NOT NULL DEFAULT 'infinity'::date,
    type smallint NOT NULL DEFAULT 0,
    status smallint NOT NULL DEFAULT 0,
    balance double precision NOT NULL DEFAULT 0,
    approved_app_id text COLLATE pg_catalog."default" NOT NULL,
    approved_acct_id text COLLATE pg_catalog."default" NOT NULL DEFAULT ''::text,
    CONSTRAINT account_info_pkey PRIMARY KEY (id),
    CONSTRAINT unique_account_name_and_owners UNIQUE (account_name, customer_id, joint_customer_id)
,
    CONSTRAINT customer_id_in_account_info_fk FOREIGN KEY (customer_id)
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

CREATE INDEX fki_customer_id_fk
    ON public.account_info USING btree
    (customer_id)
    TABLESPACE pg_default;
																						  
ALTER SEQUENCE public.account_info_id_seq
    OWNED BY public.account_info.id;