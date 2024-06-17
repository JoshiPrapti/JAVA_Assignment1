DROP DATABASE IF EXISTS Movies_db;
CREATE DATABASE Movies_db;
USE movies_db;
CREATE TABLE Movies(
id INT AUTO_INCREMENT PRIMARY KEY,
genre VARCHAR(50) NOT NULL,
watch_count INT NOT NULL);

INSERT INTO Movies (genre, watch_count) VALUES
('Action', 1200000),
('Drama', 950000),
('Comedy', 870000),
('Adventure', 800000),
('Horror', 600000),
('Sci-Fi', 550000);