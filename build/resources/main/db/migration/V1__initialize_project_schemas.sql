CREATE SCHEMA IF NOT EXISTS "library_db";

CREATE TABLE IF NOT EXISTS users (

    user_id             serial PRIMARY KEY,
    matric_number       VARCHAR(50)  NOT NULL,
    password            VARCHAR(500) NOT NULL,
    department          varchar(50)  not null,

    locked boolean not null default false,
    enabled boolean not null default true,
    deleted boolean not null default false,
    role varchar (50) not null,

    unique (matric_number)
);

CREATE TABLE IF NOT EXISTS books (

    book_id             serial primary key,
    name                varchar(255),
    about               varchar(3000),
    image_url           varchar(3000),
    author varchar(255) not null
);

create table IF NOT EXISTS book_departments (

    book_department_id  serial primary key,
    book_id             int not null,
    department_name     varchar(50),

    unique (book_id, department_name),

    CONSTRAINT FK_book_departments_book_id
        FOREIGN KEY (book_id)
            REFERENCES books(book_id)
);

create table if not exists book_requests (

    book_request_id         serial primary key,
    book_id                 int not null,
    user_id                 int not null,
    pick_up_date            timestamp not null,
    due_date                timestamp not null,
    book_request_type       varchar(50),
    status                  varchar(50),
    description             varchar(50),

    unique (book_id, user_id, book_request_type),

    CONSTRAINT FK_book_requests_book_id
        FOREIGN KEY (book_id)
            REFERENCES books(book_id),

    CONSTRAINT FK_book_requests_user_id
        FOREIGN KEY (user_id)
            REFERENCES users(user_id)
);
