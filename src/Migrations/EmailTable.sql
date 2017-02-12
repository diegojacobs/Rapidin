/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Diego Jacobs
 * Created: Feb 11, 2017
 */

CREATE TABLE email
(
    email_id    serial primary key,
    from_email       text not null,
    to_email        text not null,
    subject       text,
    content        text
);