INSERT INTO customer_login(username, password)
VALUES
('MERRLL', 'CHRISTMAS');
									  
INSERT INTO personal_info (customer_id,
						  first_name,
						  last_name,
						  middle_initial,
						  dob,
						  ssn,
						  email,
						  phone_number,
						  beneficiary)
VALUES
								(1,
							   'John',
							   'Masiello',
								'P',
								'1900-01-13',
								'123445678',
								'jp@gmail.com',
								'8884589999',
								'Dad');
--
INSERT INTO application (account_name,
							  customer_id,
							  type)
VALUES
							('College fund',
							 1,
							1);

INSERT INTO account_info (account_name,
						 customer_id,
						 joint_customer_id,
						 type,
						 approved_one_id)
VALUES
						('College fund', 
						1,
												 -1,
												 1,
												  'Banker_Approver_No_1');
												  
SELECT *
FROM customer_login
NATURAL INNER JOIN personal_info
-- NATURAL INNER JOIN application
NATURAL INNER JOIN account_info;

-- SELECT * FROM application

					  
-- UPDATE account_info
-- SET type = 1
-- WHERE customer_id = 1;