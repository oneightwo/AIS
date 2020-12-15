insert into departments (department_id, creation_date, name, parent_id)
values (nextval('department_seq'), localtime, 'dep1', null), --department_id 1
       (nextval('department_seq'), localtime, 'dep2', 1),    --department_id 2
       (nextval('department_seq'), localtime, 'dep3', 2),    --department_id 3
       (nextval('department_seq'), localtime, 'dep4', 1); --department_id 4

insert into positions (position_id, name)
values (nextval('position_seq'), 'pos1'), --position_id 1
       (nextval('position_seq'), 'pos2'), --position_id 2
       (nextval('position_seq'), 'pos3'); --position_id 3

insert into employees (employee_id, date_of_birth, date_of_dismissal, email, employment_date, gender, is_leader,
                       name, patronymic, phone, salary, surname, department_id, position_id)
values (1, to_date('30/12/1993', 'DD/MM/YYYY'), null, 'testone@gmail.com', to_date('23/01/2003', 'DD/MM/YYYY'),
        'MALE', true, 'ТестПервый', 'ТестовичПервый', '88005553534', 270000, 'ТестовПервый', 1, 1),
       (2, to_date('4/02/1998', 'DD/MM/YYYY'), null, 'testtwo@gmail.com', to_date('07/05/2012', 'DD/MM/YYYY'),
        'FEMALE', false, 'ТестВторой', 'ТестовичВторой', '88005553535', 120000, 'ТестовВторой', 1, 2),
       (3, to_date('23/02/1964', 'DD/MM/YYYY'), null, 'testthree@gmail.com', to_date('17/09/2011', 'DD/MM/YYYY'),
        'MALE', false, 'ТестТретий', 'ТестовичТретий', '88005553536', 55000, 'ТестовТретий', 1, 3);