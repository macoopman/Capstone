

-- Security Roles
INSERT INTO role (id, role_name, description) VALUES (1, "ROLE_ADMIN", "Admin role");
INSERT INTO role (id, role_name, description) VALUES (2, "ROLE_USER", "User role");

-- Users: password / $2a$12$CxcNa0EYyx9E1ifQopdBP.eRx4NurdDs7pcBJUTFa1TY4hwdAIM8e

INSERT INTO security_user(id, username, password) VALUES (900,'admin','$2a$12$CxcNa0EYyx9E1ifQopdBP.eRx4NurdDs7pcBJUTFa1TY4hwdAIM8e');
-- INSERT INTO security_user(id, username, password, salt, user_data_id) VALUES (100,'John_Bravo','$2a$12$CxcNa0EYyx9E1ifQopdBP.eRx4NurdDs7pcBJUTFa1TY4hwdAIM8e','12345');
-- INSERT INTO security_user(id, username, password, user_data_id) VALUES (101,'RBobby','$2a$12$CxcNa0EYyx9E1ifQopdBP.eRx4NurdDs7pcBJUTFa1TY4hwdAIM8e',101);
-- INSERT INTO security_user(id, username, password, user_data_id) VALUES (102,'HSimpson','$2a$12$CxcNa0EYyx9E1ifQopdBP.eRx4NurdDs7pcBJUTFa1TY4hwdAIM8e',102 );
-- INSERT INTO security_user(id, username, password, user_data_id) VALUES (103, 'aeinstein', '$2a$12$Ia0grhCgJ2EmBqaxk99ZHuzvzLbnxo/AoplcncyK4FBydNQomWYf6 ', 103);

-- Students
-- INSERT INTO student (id, first_name, last_name,  gpa, major, email, date_joined,  current_department_id) VALUES (100, 'Johnny', 'Bravo',  3.0, 'Computer Science',  'bravo_jon@google.com', '2019-02-07 05:18:55', 1);
-- INSERT INTO student (id, first_name, last_name,  gpa, major, email, date_joined, current_department_id) VALUES (101, 'Ricky', 'Bobby',  3.9, 'Computer Science', 'bobby_ricky@first.com',  '2019-01-07 05:18:55', 1);
-- INSERT INTO student (id, first_name, last_name,  gpa, major, email, date_joined, current_department_id) VALUES (102, 'Homer', 'Simpson',  3.2, 'Computer Science', 'homer_simpson@springfield.com',  '2017-07-07 05:18:55', 1);

-- Professors
-- INSERT INTO professor(id, first_name, last_name, rating, email, date_joined) VALUES (103,"Albert", "Einstein", 3.1, "einstein_albert@yale.edu",  '2015-01-07 05:18:55');

-- Assign security roles to users
INSERT INTO security_user_roles(user_id, roles_id) Values (1,1);
-- INSERT INTO security_user_roles(user_id, roles_id) Values (2,2);
-- INSERT INTO security_user_roles(user_id, roles_id) Values (3,2);
-- INSERT INTO security_user_roles(user_id, roles_id) Values (4,2);
-- INSERT INTO security_user_roles(user_id, roles_id) Values (5,2);

-- create departments
# INSERT INTO department(id, department_abbreviation, department_name) VALUES (1, 'CSC', 'Computer Science');
#
#
# -- create classes
# INSERT INTO klass(id, subject, class_number) VALUES (1, 'Databases', 422);
#
# -- Assign classes to departments
# INSERT INTO department_department_classes(department_id, department_classes_id) VALUES (1,1);
#
#
#
# -- Create Sessions
# INSERT INTO session(id, start_date, end_date, session_name) VALUES (1, "2019-01-15", "2019-02-15", "JAN_2019");
# INSERT INTO session(id, start_date, end_date, session_name) VALUES (2, "2019-02-15", "2019-03-15", "FEB_2019");
# INSERT INTO session(id, start_date, end_date, session_name) VALUES (3, "2019-04-15", "2019-05-15", "MAR_2019");
#
#
# -- Assign Sessions to a Class
# INSERT INTO klass_session_list(klass_id, session_list_id ) VALUES (1,1);
# INSERT INTO klass_session_list(klass_id, session_list_id ) VALUES (1,2);
# INSERT INTO klass_session_list(klass_id, session_list_id ) VALUES (1,3);
#
# -- assign Students to sessions
# INSERT INTO session_students(session_id, students_id) VALUES (1,100);
# INSERT INTO session_students(session_id, students_id) VALUES (1,101);
# INSERT INTO session_students(session_id, students_id) VALUES (1,102);
#
# INSERT INTO session_students(session_id, students_id) VALUES (2,100);
#
#
# -- Assign professor to sessions
# INSERT INTO session_professors(session_id, professors_id) VALUES (1,103);
#
# -- create comments
# INSERT INTO comment(id, message) VALUES (100, 'This is a comment');
#
#
# -- assign comment to session
# INSERT INTO session_comments(session_id, comments_id) VALUES (1,100)






