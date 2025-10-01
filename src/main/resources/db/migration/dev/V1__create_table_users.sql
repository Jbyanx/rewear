CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       first_name       VARCHAR(50) NOT NULL,
                       last_name        VARCHAR(50) NOT NULL,
                       phone_number     VARCHAR(20) NOT NULL,
                       email            VARCHAR(150) NOT NULL UNIQUE,
                       username         VARCHAR(100) NOT NULL UNIQUE,
                       password         VARCHAR(72) NOT NULL,
                       address          VARCHAR(150) NOT NULL,
                       city             VARCHAR(50) NOT NULL,
                       country          VARCHAR(50) NOT NULL,

                       birth_date       DATE NOT NULL,
                       genre            VARCHAR(20) NOT NULL,
                       document_type    VARCHAR(20) NOT NULL,
                       document_number  VARCHAR(25) NOT NULL,
                       profile_image_url VARCHAR(255),

                       rating           DECIMAL(5,2) DEFAULT 0,
                       total_ratings    INT DEFAULT 0,

                       created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       is_active        BOOLEAN NOT NULL DEFAULT TRUE,
                       role             VARCHAR(30) NOT NULL
);
