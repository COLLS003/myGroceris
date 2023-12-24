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

-- Products table
create table if not exists groceries."Products" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL,
    "price" FLOAT NOT NULL,
    "category" INTEGER NOT NULL,
    "imageUrl" VARCHAR NOT NULL
);

-- OrderItems table
create table if not exists groceries."OrderItems" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "productID" INTEGER NOT NULL,
    "quantity" FLOAT NOT NULL,
    "subtotal" FLOAT NOT NULL,
    "orderID" INTEGER NOT NULL
);

-- Orders table
create table if not exists groceries."Orders" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "dataCreated" DATE NOT NULL,
    "status" VARCHAR NOT NULL,
    "total" FLOAT NOT NULL,
    "userID" INTEGER NOT NULL
);
