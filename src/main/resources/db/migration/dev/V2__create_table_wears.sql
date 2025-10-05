CREATE TABLE wears (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,

                       name            VARCHAR(150) NOT NULL,
                       description     VARCHAR(500) NOT NULL,
                       size            VARCHAR(30) NOT NULL,          -- Enum WearSize
                       condition       VARCHAR(30) NOT NULL,          -- Enum WearCondition
                       status          VARCHAR(30) NOT NULL,          -- Enum WearStatus
                       category        VARCHAR(30) NOT NULL,          -- Enum WearCategory
                       brand           VARCHAR(100) NOT NULL,
                       color           VARCHAR(50) NOT NULL,
                       genre           VARCHAR(30) NOT NULL,          -- Enum Genre
                       material        VARCHAR(50) NOT NULL,
                       image_url       VARCHAR(255) NOT NULL,

                       created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                       active          BOOLEAN NOT NULL DEFAULT TRUE,
                       deleted_at      TIMESTAMP NULL,

                       owner_id        BIGINT NOT NULL,

                       CONSTRAINT fk_wear_owner
                           FOREIGN KEY (owner_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE
);
