CREATE TABLE IF NOT EXISTS products (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price NUMERIC(12, 2) NOT NULL,
    sku VARCHAR(50) NOT NULL UNIQUE,
    available_quantity INT NOT NULL CHECK (available_quantity >= 0)
);