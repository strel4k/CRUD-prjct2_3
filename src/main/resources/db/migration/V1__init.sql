-- Таблица Writer
CREATE TABLE writer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

-- Таблица PostStatus (в виде ENUM-like таблицы)
CREATE TABLE post_status (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Таблица Post
CREATE TABLE post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP,
    writer_id BIGINT,
    status_id INT,
    FOREIGN KEY (writer_id) REFERENCES writer(id),
    FOREIGN KEY (status_id) REFERENCES post_status(id)
);

-- Таблица Label
CREATE TABLE label (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Связующая таблица между Post и Label
CREATE TABLE post_label (
    post_id BIGINT NOT NULL,
    label_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, label_id),
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (label_id) REFERENCES label(id) ON DELETE CASCADE
);

-- Начальные значения для post_status
INSERT INTO post_status (id, name) VALUES
  (1, 'ACTIVE'),
  (2, 'UNDER_REVIEW'),
  (3, 'DELETED');