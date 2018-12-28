BEGIN
RETURN (SELECT username FROM customer_login 
WHERE username = in_username) IS NULL;
END;

-- FUNCTION: public.is_unique_customer_username(text)

-- DROP FUNCTION public.is_unique_customer_username(text);

CREATE OR REPLACE FUNCTION public.is_unique_customer_username(
	in_username text DEFAULT ''::text)
    RETURNS boolean
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$BEGIN
RETURN (SELECT username FROM customer_login 
WHERE username = in_username) IS NULL;
END;$BODY$;

ALTER FUNCTION public.is_unique_customer_username(text)
    OWNER TO utamyrdsjp;
