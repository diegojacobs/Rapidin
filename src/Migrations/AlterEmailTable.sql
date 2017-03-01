/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Diego Jacobs
 * Created: Feb 26, 2017
 */

ALTER TABLE email 
ADD COLUMN createddate TIMESTAMP NOT NULL;

ALTER TABLE email 
RENAME content TO data;
