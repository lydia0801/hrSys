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


insert into department values(1,'总经办');
insert into department values(2,'技术部');
insert into department values(3,'市场部');
insert into department values(4,'销售部');
insert into department values(5,'财务部');
insert into employee values(1001,1,'张三','男','湖南长沙',null);
insert into employee values(1002,2,'陈怡','女','湖南长沙',null);
insert into employee values(1003,3,'丽丽','女','湖南长沙',null);
insert into employee values(1004,4,'李四','男','湖南长沙',null);




insert into userinfo values(1001,'lydia','aa','管理员');
insert into userinfo values(1002,'ly','aa','部门经理');
insert into userinfo values(1003,'navy','aa','普通员工');

commit;


select *  from  employee;
select * from userinfo;
select * from department;

 


select e.*,d.departname from  employee  e inner join department d on d.departid = e.departid  and e.empid =1002   and  d.departname ='技术部';


select e.*,d.departname from  employee  e inner join department d on d.departid = e.departid  and d.departname  = '技术部'  and  e.empid = 1002
