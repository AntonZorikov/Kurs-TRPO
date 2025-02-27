-- INSERT INTO users (username, password, role) VALUES ('admin', 'admin', 'ADMIN');
-- INSERT INTO pawn_requests (item_name, description, expected_price, status, created_at) 
-- VALUES ('Телефон', 'iPhone 12', 50000, 'NEW', CURRENT_TIMESTAMP); 

INSERT INTO users (username, password, role, balance) 
VALUES ('admin', 'admin', 'ADMIN', 0.0); 

INSERT INTO users (username, password, role, balance) 
VALUES ('user', 'user', 'USER', 0.0);
