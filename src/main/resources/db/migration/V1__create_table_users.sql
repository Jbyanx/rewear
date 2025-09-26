CREATE TABLE users (
                       id BIGINT IDENTITY(1,1) PRIMARY KEY,

                       first_name       NVARCHAR(50) NOT NULL,
                       last_name        NVARCHAR(50) NOT NULL,
                       phone_number     NVARCHAR(20) NOT NULL,
                       email            NVARCHAR(150) NOT NULL UNIQUE,
                       username         NVARCHAR(100) NOT NULL UNIQUE,
                       password         NVARCHAR(72) NOT NULL,
                       address          NVARCHAR(150) NOT NULL,
                       city             NVARCHAR(50) NOT NULL,
                       country          NVARCHAR(50) NOT NULL,

                       birth_date       DATE NOT NULL,
                       genre            NVARCHAR(20) NOT NULL,       -- Enum Genre
                       document_type    NVARCHAR(20) NOT NULL,       -- Enum DocumentType
                       document_number  NVARCHAR(25) NOT NULL,
                       profile_image_url NVARCHAR(255),

                       rating           DECIMAL(5,2) DEFAULT 0,
                       total_ratings    INT DEFAULT 0,

                       created_at       DATETIME2 DEFAULT SYSUTCDATETIME(),
                       updated_at       DATETIME2 DEFAULT SYSUTCDATETIME(),

                       is_active        BIT NOT NULL DEFAULT 1,
                       role             NVARCHAR(30) NOT NULL        -- Enum Role
);
