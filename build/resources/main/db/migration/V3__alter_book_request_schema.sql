alter table book_requests
    add column created_at timestamp not null default now(),
    drop constraint book_requests_book_id_user_id_book_request_type_key;

create table if not exists change_date_requests (

    change_date_request_id serial primary key,
    book_request_id int not null unique,
    user_id int not null,
    old_due_date timestamp not null,
    new_due_date timestamp not null,

    constraint FK_change_date_request_book_request_id foreign key (book_request_id)
        references book_requests(book_request_id),
    constraint FK_change_date_request_user_id foreign key (user_id)
        references users(user_id)
);
