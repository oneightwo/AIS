insert into departments (department_id, creation_date, name, parent_id)
values (1, localtime, 'dep1', null),
       (2, localtime, 'dep2', 1),
       (3, localtime, 'dep3', 2),
       (4, localtime, 'dep4', 1);

insert into employees (employee_id, date_of_birth, date_of_dismissal, email, employment_date, gender, is_leader,
                       name, patronymic, phone, salary, surname, department_id, position_id)
values (nextval('employee_seq'), to_date('30/12/1993', 'DD/MM/YYYY'), null, 'testone@gmail.com',
        to_date('23/01/2003', 'DD/MM/YYYY'),
        'MALE', true, 'ТестПервый', 'ТестовичПервый', '88005553534', 270000, 'ТестовПервый', 1, 1),
       (nextval('employee_seq'), to_date('4/02/1998', 'DD/MM/YYYY'), null, 'testtwo@gmail.com',
        to_date('07/05/2012', 'DD/MM/YYYY'),
        'FEMALE', false, 'ТестВторой', 'ТестовичВторой', '88005553535', 120000, 'ТестовВторой', 1, 2),
       (nextval('employee_seq'), to_date('23/02/1964', 'DD/MM/YYYY'), null, 'testthree@gmail.com',
        to_date('17/09/2011', 'DD/MM/YYYY'),
        'MALE', false, 'ТестТретий', 'ТестовичТретий', '88005553536', 55000, 'ТестовТретий', 1, 3);