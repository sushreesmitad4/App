--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.18
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-12-12 15:24:42

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 296 (class 1259 OID 1759317)
-- Name: walletkey; Type: TABLE; Schema: public; Owner: wallet
--

CREATE TABLE walletkey (
    wallet character varying(500) NOT NULL
);


ALTER TABLE walletkey OWNER TO wallet;

--
-- TOC entry 3231 (class 0 OID 1759317)
-- Dependencies: 296
-- Data for Name: walletkey; Type: TABLE DATA; Schema: public; Owner: wallet
--

INSERT INTO walletkey (wallet) VALUES ('Tarang1234567890');


-- Completed on 2016-12-12 15:24:42

--
-- PostgreSQL database dump complete
--

