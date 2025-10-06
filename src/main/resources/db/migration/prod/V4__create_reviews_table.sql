CREATE TABLE reviews (
                         id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         exchange_id BIGINT NOT NULL,
                         reviewer_id BIGINT NOT NULL,
                         reviewed_user_id BIGINT NOT NULL,
                         rating INT NOT NULL,
                         comment NVARCHAR(500),
                         created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
                         CONSTRAINT fk_review_exchange FOREIGN KEY (exchange_id) REFERENCES exchanges(id),
                         CONSTRAINT fk_review_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(id),
                         CONSTRAINT fk_review_reviewed_user FOREIGN KEY (reviewed_user_id) REFERENCES users(id)
);
