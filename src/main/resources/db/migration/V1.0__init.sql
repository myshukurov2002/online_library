------------CREATE DB WITH USER-------------------
CREATE ROLE userjon WITH LOGIN PASSWORD '123';
CREATE DATABASE think_store WITH OWNER userjon;

------------------UUID-------------------
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";