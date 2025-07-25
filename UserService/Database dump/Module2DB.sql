--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-07-01 21:06:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 16390)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    age integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 223 (class 1255 OID 16428)
-- Name: find_user_by_email(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.find_user_by_email(input_email character varying) RETURNS SETOF public.users
    LANGUAGE sql
    AS $$
    SELECT * FROM users WHERE email = input_email;
$$;


ALTER FUNCTION public.find_user_by_email(input_email character varying) OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16389)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- TOC entry 4784 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 4626 (class 2604 OID 16393)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 4778 (class 0 OID 16390)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, name, email, age, created_at) FROM stdin;
2	Елена	elena@example.com	27	2024-06-18 13:10:00
3	Дмитрий	dmitry@example.com	31	2024-07-22 08:30:00
4	Ольга	olga@example.com	24	2024-08-30 12:00:00
5	Сергей	sergey@example.com	29	2024-09-14 15:25:00
6	Наталья	natalia@example.com	33	2024-10-28 17:40:00
7	Иван	ivan@example.com	25	2025-01-15 10:00:00
8	Пётр	petr@example.com	30	2025-02-20 11:30:00
9	Анна	anna@example.com	22	2025-03-10 09:15:00
10	Мария	maria@example.com	28	2025-04-05 14:20:00
1	alexey	alexey@example.com	35	2024-05-12 16:45:00
\.


--
-- TOC entry 4785 (class 0 OID 0)
-- Dependencies: 217
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 19, true);


--
-- TOC entry 4629 (class 2606 OID 16398)
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 4631 (class 2606 OID 16396)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


-- Completed on 2025-07-01 21:06:16

--
-- PostgreSQL database dump complete
--

