create table  employee(
       empID   int  primary key,
       departID  int,
       empName varchar2(20),
       empSex  char(2),
       address varchar2(50),
       empImg  blob
);

create table department(
       departID int primary key,
       departName varchar2(20)

);
alter table employee add constraint fk_ed foreign key(departID) references department(departid);
create table userinfo(
       userID int primary key,
       userName varchar2(20) unique,
       pwd varchar2(20),
       userRole varchar2(20)
);


insert into department values(1,'�ܾ���');
insert into department values(2,'������');
insert into department values(3,'�г���');
insert into department values(4,'���۲�');
insert into department values(5,'����');
insert into employee values(1001,1,'����','��','���ϳ�ɳ',null);
insert into employee values(1002,2,'����','Ů','���ϳ�ɳ',null);
insert into employee values(1003,3,'����','Ů','���ϳ�ɳ',null);
insert into employee values(1004,4,'����','��','���ϳ�ɳ',null);




insert into userinfo values(1001,'lydia','aa','����Ա');
insert into userinfo values(1002,'ly','aa','���ž���');
insert into userinfo values(1003,'navy','aa','��ͨԱ��');

commit;


select *  from  employee;
select * from userinfo;
select * from department;

 


select e.*,d.departname from  employee  e inner join department d on d.departid = e.departid  and e.empid =1002   and  d.departname ='������';


select e.*,d.departname from  employee  e inner join department d on d.departid = e.departid  and d.departname  = '������'  and  e.empid = 1002
