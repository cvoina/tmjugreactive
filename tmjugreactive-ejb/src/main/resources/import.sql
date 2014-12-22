--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements

insert into Orders(id, order_id, create_date) values (0, 'AB10','2014-12-15 12:22:33')

insert into Contact(id, name, email, address, order_id) values (0, 'John Doe', 'john.doe@test.com', 'test address', 'AB10') 
insert into Contact(id, name, email, address, order_id) values (1, 'Jane Doe', 'jane.doe@test.com', 'no address', 'AB10') 

insert into Product(id, name, sku, price, order_id) values (0, 'Book', '12345789', '92', 'AB10') 
insert into Product(id, name, sku, price, order_id) values (1, 'Cover', '987654321', '35', 'AB10') 
