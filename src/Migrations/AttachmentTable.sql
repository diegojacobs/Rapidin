/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Diego Jacobs
 * Created: Feb 11, 2017
 */

CREATE TABLE attachment
(
    attachment_id    serial primary key,
    email_id       int not null,
    filepath        text not null
);