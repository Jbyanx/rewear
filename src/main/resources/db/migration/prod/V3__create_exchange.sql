CREATE TABLE exchange (
                          id BIGINT IDENTITY(1,1) PRIMARY KEY,
                          requester_id BIGINT NOT NULL,
                          owner_id BIGINT NOT NULL,
                          offered_wear_id BIGINT NOT NULL,
                          requested_wear_id BIGINT NOT NULL,
                          status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                          requester_confirmed BIT NOT NULL DEFAULT 0,
                          owner_confirmed BIT NOT NULL DEFAULT 0,
                          created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
                          updated_at DATETIME2 DEFAULT SYSUTCDATETIME(),

                          CONSTRAINT fk_exchange_requester FOREIGN KEY (requester_id) REFERENCES users(id),
                          CONSTRAINT fk_exchange_owner FOREIGN KEY (owner_id) REFERENCES users(id),
                          CONSTRAINT fk_exchange_offered_wear FOREIGN KEY (offered_wear_id) REFERENCES wears(id),
                          CONSTRAINT fk_exchange_requested_wear FOREIGN KEY (requested_wear_id) REFERENCES wears(id)
);
