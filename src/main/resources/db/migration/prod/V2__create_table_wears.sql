CREATE TABLE wears (
                       id BIGINT IDENTITY(1,1) PRIMARY KEY,

                       name            NVARCHAR(150) NOT NULL,
                       description     NVARCHAR(500) NOT NULL,
                       size            NVARCHAR(30) NOT NULL,         -- Enum WearSize
                       condition       NVARCHAR(30) NOT NULL,         -- Enum WearCondition
                       status          NVARCHAR(30) NOT NULL,         -- Enum WearStatus
                       category        NVARCHAR(30) NOT NULL,         -- Enum WearCategory
                       brand           NVARCHAR(100) NOT NULL,
                       color           NVARCHAR(50) NOT NULL,
                       genre           NVARCHAR(30) NOT NULL,         -- Enum Genre
                       material        NVARCHAR(50) NOT NULL,
                       image_url       NVARCHAR(255) NOT NULL,

                       created_at      DATETIME2 DEFAULT SYSUTCDATETIME(),
                       updated_at      DATETIME2 DEFAULT SYSUTCDATETIME(),

                       active          BIT NOT NULL DEFAULT 1,
                       deleted_at      DATETIME2 NULL,

                       owner_id        BIGINT NOT NULL,

                       CONSTRAINT fk_wear_owner
                           FOREIGN KEY (owner_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE
);
