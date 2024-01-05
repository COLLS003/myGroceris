-- Create extension and schema for "groceries" database
create extension hstore;
create schema groceries;

-- User table
create table if not exists groceries."User" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL,
    "mail" VARCHAR NOT NULL,
    "location" INTEGER NOT NULL,
    "userType" INTEGER NOT NULL,
    "phone" VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL,
    "status" VARCHAR NOT NULL
);

-- flight table
CREATE TABLE IF NOT EXISTS groceries."Flights" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL,
    "origin" VARCHAR NOT NULL,
    "destination" VARCHAR NOT NULL,
    "capacity" INTEGER NOT NULL,
    "pilot" VARCHAR NOT NULL,
    "departure" VARCHAR NOT NULL,
    "arrival" VARCHAR NOT NULL
);

--- Bookings table
CREATE TABLE IF NOT EXISTS groceries."Bookings" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "flight_id" BIGINT NOT NULL,
    "user_id" BIGINT NOT NULL,
    FOREIGN KEY ("flight_id") REFERENCES groceries."Flights"("id"),
    FOREIGN KEY ("user_id") REFERENCES groceries."User"("id")
);
