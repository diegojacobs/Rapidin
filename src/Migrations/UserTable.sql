/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Diego Jacobs
 * Created: Feb 11, 2017
 */

CREATE TABLE my_user
(
    my_user_id    serial primary key,
    first_name       text,
    last_name        text,
    email       text not null,
    password        text not null
);