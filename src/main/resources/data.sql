-- Create User Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Insert Sample Users
INSERT INTO users (name, email, password, role) VALUES
('Admin User', 'admin@bookstore.com', 'adminpassword', 'ADMIN'),
('Regular User', 'user@bookstore.com', 'userpassword', 'USER');

-- Create Book Table
CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL,
    category VARCHAR(50) NOT NULL
);

-- Insert Sample Books
INSERT INTO books (title, author, price, stock_quantity, category) VALUES
('Java Programming', 'John Doe', 29.99, 100, 'Programming'),
('Spring Framework', 'Jane Smith', 39.99, 50, 'Programming'),
('The Art of War', 'Sun Tzu', 19.99, 200, 'Philosophy');

-- Create Order Table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    bookId BIGINT NOT NULL,
    quantity INT NOT NULL,
    totalPrice DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (bookId) REFERENCES books(id)
);

-- Insert Sample Orders
INSERT INTO orders (userId, bookId, quantity, totalPrice, status) VALUES
(1, 1, 2, 59.98, 'PENDING'),
(2, 2, 1, 39.99, 'COMPLETED');

-- Create Shopping Cart Table
CREATE TABLE IF NOT EXISTS shopping_cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT NOT NULL,
    bookId BIGINT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (bookId) REFERENCES books(id)
);

-- Insert Sample Shopping Cart Items
INSERT INTO shopping_cart (userId, bookId, quantity) VALUES
(2, 1, 1), -- User 2 added 1 Java Programming book to cart
(2, 3, 2); -- User 2 added 2 The Art of War books to cart
