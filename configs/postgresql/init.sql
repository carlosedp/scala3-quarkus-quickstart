-- Create user and database for quickstart project
-- The user, password and database names match the configuration in application.properties
-- This init script is run when the postgres container is started

-- Create user quickstart with password quickstart
-- DROP ROLE IF EXISTS quickstart;

CREATE ROLE quickstart WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  NOREPLICATION
  ENCRYPTED PASSWORD 'SCRAM-SHA-256$4096:DAxlsDd1OAZZYMg2pkNTEA==$kX2WgwO9MpGNE1dnS5KijfWbYlkxCAS+/Ghe6CFzqyw=:h8HpaC9WeRRymKbIQlmgSXw9drXEfZ5uQj3nYipUYbQ=';

-- Create database quarkus with owner quickstart
-- DROP DATABASE IF EXISTS quarkus;

CREATE DATABASE quarkus
    WITH
    OWNER = quickstart
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
