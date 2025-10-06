CREATE TABLE reviews (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         exchange_id BIGINT NOT NULL,
                         reviewer_id BIGINT NOT NULL,
                         reviewed_user_id BIGINT NOT NULL,
                         rating INT NOT NULL,
                         comment VARCHAR(500),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                         CONSTRAINT fk_review_exchange FOREIGN KEY (exchange_id) REFERENCES exchanges(id),
                         CONSTRAINT fk_review_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(id),
                         CONSTRAINT fk_review_reviewed_user FOREIGN KEY (reviewed_user_id) REFERENCES users(id)
);
