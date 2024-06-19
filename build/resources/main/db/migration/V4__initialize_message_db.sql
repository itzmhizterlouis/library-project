create table messages (

    message_id serial primary key,
    admin_id int not null,
    user_id int not null,
    message varchar(3000) not null,
    book_id int not null,
    created_at timestamp not null default now(),

    constraint FK_message_admin_id foreign key (admin_id)
                      references users(user_id),
    constraint FK_message_user_id foreign key (user_id)
        references users(user_id),
    constraint FK_message_book_id foreign key (book_id)
        references books(book_id)
)
