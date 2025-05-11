CREATE TABLE public."user" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);
CREATE TABLE email_data (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    user_id INT REFERENCES public."user"(id)
);
CREATE TABLE phone_data (
    id SERIAL PRIMARY KEY,
    phone VARCHAR(20) NOT NULL,
    user_id INT REFERENCES public."user"(id)
);
CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    initial_balance NUMERIC NOT NULL,
    balance NUMERIC NOT NULL,
    user_id INT REFERENCES public."user"(id)
);
