use FoodOrdering;


INSERT into Customer VALUES(1,'Rahul Sharma', '56C, Jonathan Way, Boston', 'rahul@cc.com','pass@1','123');
INSERT into Customer VALUES(2,'Sneha Sharma', '56D, Broadan Way, Boston', 'sneha@cc.com','pass@2','1234');
INSERT into Customer VALUES(3,'Brodley Smith', '56D, Joshua Way, Boston', 'broadley@cc.com','pass@134','1235');
INSERT into Customer VALUES(4,'Teja Gumalla', '56E, Labour Way, Boston', 'teja@cc.com','pass@22','123');
INSERT into Customer VALUES(5,'Shail Shah', '565, abc Way, Boston', 'shail@cc.com','pass@14','12355');
INSERT into Customer VALUES(6,'Chaitanya Kaul', '32-C, Jonathan Way, Boston', 'chaitanya@cc.com','pass@1345','1233');
INSERT into Customer VALUES(7,'Deepak Sharma', '56C, Jonathan Way, Boston', 'deepak@cc.com','pass@1ee','1235888');


INSERT into Restaurant VALUES(1,'Desi Restaurant', 'ABC bull street Boston', 'desi@cc.com','pass@1','123');
INSERT into Restaurant VALUES(2,'Boston Shawarma', 'Brodley Street, Boston', 'bostonss@cc.com','pass@2','1234');
INSERT into Restaurant VALUES(3,'Chutneys', 'Symphony street, Boston', 'chutneys@cc.com','pass@134','123sd5');
INSERT into Restaurant VALUES(4,'Desi Buffet House', '66F Labour Way, Boston', 'labourhouse@cc.com','pass@22','123');
INSERT into Restaurant VALUES(5,'Dominos Pizza', '44 Roxburry Crossing', 'domnios@cc.com','pass@14','12355');
INSERT into Restaurant VALUES(6,'Halal Restaurant', '32sdC, Jonathan Way, Boston', 'halalres@cc.com','pass@1345','1233');
INSERT into Restaurant VALUES(7,'Popeyes', '66, Jonathan Way, Boston', 'popeyes@cc.com','pass@1ee','1235888');

INSERT into MenuItem VALUES(1, 'chutney kebab', 2, 'this is a kebab which has green chutney in it');
INSERT into MenuItem VALUES(2, 'Cheese Pizza', 4, 'Simple Cheese pizza with no toppings');
INSERT into MenuItem VALUES(3, 'Burrito', 5, 'Burrito Bowl or a roll');
INSERT into MenuItem VALUES(4, 'Hamburger', 6, 'this is a kebab which has green chutney in it');
INSERT into MenuItem VALUES(5, 'chutney kebab2', 2, 'this is a kebab which has green chutney in it');
INSERT into MenuItem VALUES(6, 'Plain Dosa', 2, 'Plain Dosa served with sambar');
INSERT into MenuItem VALUES(7, 'Naan Pizza', 2, 'THis is a pizza which is made out of naan bread');
INSERT into MenuItem VALUES(8, 'Taco', 5, 'This is a taco');

INSERT into Menu VALUES(1, 1, 20.55);
INSERT into Menu VALUES(5, 2, 12.50);
INSERT into Menu VALUES(7, 3, 8.55);
INSERT into Menu VALUES(7, 4, 5.50);
INSERT into Menu VALUES(1, 5, 20.55);
INSERT into MENU VALUES(1, 6, 12.00);
INSERT into Menu VALUES(3, 7, 16.55);
INSERT into Menu VALUES(4, 8, 14.00);
insert into Menu Values(1,8,5.00);

Insert into Reviews values(1,1,1,"This is a good restaurant",3);
Insert into Reviews values(2,2,1,"This is a great restaurant",4);
Insert into Reviews values(3,3,1,"I like the food here",5);
Insert into Reviews values(4,4,1,"Amazing!",4);