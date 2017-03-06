--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.18
-- Dumped by pg_dump version 9.5.1

-- Started on 2016-12-12 15:24:04

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
-- TOC entry 281 (class 1259 OID 1759263)
-- Name: t_email_neg; Type: TABLE; Schema: public; Owner: wallet
--

CREATE TABLE t_email_neg (
    email character varying(100) NOT NULL,
    reason character varying(50),
    ip character varying(20),
    country character varying(50),
    date_added date DEFAULT now(),
    status character varying(20),
    message character varying(100),
    no_of_hits numeric DEFAULT 0,
    merchant_id character varying DEFAULT 1 NOT NULL,
    expiry_date date
);


ALTER TABLE t_email_neg OWNER TO wallet;

--
-- TOC entry 3234 (class 0 OID 1759263)
-- Dependencies: 281
-- Data for Name: t_email_neg; Type: TABLE DATA; Schema: public; Owner: wallet
--

INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('123.123.123.123', 'dfsf', NULL, NULL, '2013-01-04', '1', 'dsfsf', 0, 'tarn0502458976911969', '2014-01-04');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asdasd@ass.com', 'aaa', NULL, NULL, '2013-01-15', '1', 'aaa', 0, 'SS93091631004657', '2014-01-15');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asd@asd.com', 'gfgd', NULL, NULL, '2013-01-03', '1', 'dfgdgdg', 0, 'sapna36021305178627', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asd@asd.com', 'n', NULL, NULL, '2013-02-07', '1', 'n', 1, 'testme04760547329441', '2014-02-07');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asdasd@sdf.com', 'aa', NULL, NULL, '2013-01-15', '0', 'aa', 1, 'Aerona48273253770615', '2014-01-15');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asdf@asdf.com', 'sdasd', NULL, NULL, '2013-01-03', '1', 'sfsdfsdf', 0, 'BUSINE03374715120921', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asdf.asf.asd@gm.com', '29 wish', NULL, NULL, '2013-01-29', '1', '29 message', 1, 'COMPAa76449971299946', '2014-01-29');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asi@asi.com', 'aa', NULL, NULL, '2013-01-03', '1', 'aa', 0, 'BUSINE03374715120921', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asi@asi.com', 'aa', NULL, NULL, '2013-01-03', '1', 'aa', 0, 'HONDA400759840097449', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asi@asi.com', 'aa', NULL, NULL, '2013-01-03', '1', 'aa', 0, 'IBMWER86932957059706', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('asi@asi.com', 'aa', NULL, NULL, '2013-01-03', '1', 'aa', 0, 'SS93091631004657', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('azx@azx.com', 'qaz', NULL, NULL, '2013-01-15', '1', 'qaz', 0, 'sapna36021305178627', '2014-01-15');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('iliy@gmail.com', 'ilu', NULL, NULL, '2013-01-17', '1', 'ilu', 0, 'IBMWER86932957059706', '2014-01-17');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('qqq@qqq.com', 'qq', NULL, NULL, '2013-01-16', '1', 'qq', 0, 'IBMWER86932957059706', '2014-01-16');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('qqq@qqq.qqq', 'aa', NULL, NULL, '2013-01-03', '1', 'aa', 0, 'HONDA400759840097449', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('qqq@qqq.qqq', 'aa', NULL, NULL, '2013-01-03', '1', 'aa', 0, 'SS93091631004657', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('rajeshk@tata.com', 'rajeshk', NULL, NULL, '2013-01-17', '1', 'rajeshk', 0, 'airkraft456', '2014-01-17');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('rajesh@test.com', 'rajesh email', NULL, NULL, '2013-02-07', '1', 'rajesh email', 4, 'visaka23126765849444', '2014-02-07');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('raj@raj.com', 'hi', NULL, NULL, '2013-01-07', '1', 'hi', 0, 'sapna36021305178627', '2014-01-07');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sfgg@sdd.com', 'ss', NULL, NULL, '2013-01-17', '0', 'ss', 0, 'Aerona48273253770615', '2014-01-17');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sowiydbbfnm@dggnv.com', 'sdfsdf', NULL, NULL, '2013-01-11', '1', 'sdfsff', 0, 'airkraft456', '2014-01-11');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sowmi.abcd@gmail.com', 'my reason', NULL, NULL, '2013-02-05', '0', 'my message', 0, 'COMPAa14452986376186', '2014-02-05');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sowmi.somwi@com', 'sss', NULL, NULL, '2013-01-05', '1', 'sss', 0, 'sapna36021305178627', '2014-01-05');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sowmiyasn@gmail.com', 'my wish', NULL, NULL, '2013-01-03', '1', 'my reason', 0, 'COMPAa87690258995240', '2014-01-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sowmiyasn@yahoo.co.in', 'test email', NULL, NULL, '2013-02-03', '1', 'test email message', 1, 'SS93091631004657', '2014-02-03');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sri@kanth', 'hi', NULL, NULL, '2013-01-07', '1', 'hello', 0, 'sapna36021305178627', '2014-01-07');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sss@ddd.fff', 'a', NULL, NULL, '2013-02-09', '0', 'a', 0, 'subway06100448652895', '2013-02-10');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('sss@sdsdss.com', 'aa', NULL, NULL, '2013-02-08', '1', 'aa', 0, 'PJeIdx06123587753043', '2014-02-08');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('tom1@jones.com', 'Test', NULL, NULL, '2013-01-28', '1', 'Test', 1, 'Aerona48273253770615', '2014-01-28');
INSERT INTO t_email_neg (email, reason, ip, country, date_added, status, message, no_of_hits, merchant_id, expiry_date) VALUES ('gmail22.com', 'abb', NULL, NULL, '2013-02-21', '1', 'abb', 0, 'AmWay147891567076293', '2014-02-21');


-- Completed on 2016-12-12 15:24:04

--
-- PostgreSQL database dump complete
--

