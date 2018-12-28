DECLARE
rec_password text;
rec_customer_id integer;
BEGIN
SELECT INTO rec_password, rec_customer_id
	customer_login.password, customer_login.customer_id
	FROM customer_login
	WHERE 
	customer_login.username LIKE _username;
IF _password = rec_password
THEN
	RETURN rec_customer_id;
ELSE
	RETURN -1;
END IF;
END;


-- FUNCTION: public.authenticate_customer_login(text, text)

-- DROP FUNCTION public.authenticate_customer_login(text, text);

CREATE OR REPLACE FUNCTION public.authenticate_customer_login(
	_username text DEFAULT ''::text,
	_password text DEFAULT ''::text)
    RETURNS integer
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$DECLARE
rec_password text;
rec_customer_id integer;
BEGIN
SELECT INTO rec_password, rec_customer_id
	customer_login.password, customer_login.customer_id
	FROM customer_login
	WHERE 
	customer_login.username LIKE _username;
IF _password = rec_password
THEN
	RETURN rec_customer_id;
ELSE
	RETURN -1;
END IF;
END;$BODY$;

ALTER FUNCTION public.authenticate_customer_login(text, text)
    OWNER TO utamyrdsjp;
