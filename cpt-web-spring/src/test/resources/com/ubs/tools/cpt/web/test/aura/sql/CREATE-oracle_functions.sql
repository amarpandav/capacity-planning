CREATE FUNCTION to_upper(str VARCHAR) RETURNS VARCHAR AS $$
BEGIN
RETURN upper(str);
END
$$
LANGUAGE plpgsql;