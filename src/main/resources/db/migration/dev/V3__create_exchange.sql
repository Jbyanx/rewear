CREATE TABLE exchange (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          requester_id BIGINT NOT NULL,
                          owner_id BIGINT NOT NULL,
                          offered_wear_id BIGINT NOT NULL,
                          requested_wear_id BIGINT NOT NULL,
                          status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                          requester_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
                          owner_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                          CONSTRAINT fk_exchange_requester FOREIGN KEY (requester_id) REFERENCES users(id),
                          CONSTRAINT fk_exchange_owner FOREIGN KEY (owner_id) REFERENCES users(id),
                          CONSTRAINT fk_exchange_offered_wear FOREIGN KEY (offered_wear_id) REFERENCES wears(id),
                          CONSTRAINT fk_exchange_requested_wear FOREIGN KEY (requested_wear_id) REFERENCES wears(id)
);
