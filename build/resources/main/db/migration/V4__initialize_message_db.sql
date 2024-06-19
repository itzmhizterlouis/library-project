create table messages (

    message_id serial primary key,
    admin_id int not null,
    user_id int not null,
    message varchar(3000) not null,

    constraint FK_message_admin_id foreign key (admin_id)
                      references users(user_id),
    constraint FK_message_user_id foreign key (user_id)
        references users(user_id)
)
