# Spring Reactive App (CRUD) with Webflux and Mysql database

App provides basic CRUD operations connecting to MySql database using reactive relational database driver (R2DBC)


## Pre-Req:
Update properties.yaml
``` properties
spring.r2dbc.url=r2dbc:pool:mysql://<<Your MySQL Host>>:3306/customer
spring.r2dbc.username=<<User Name>>
spring.r2dbc.password=<<Password>>
```
Launch a mysql instance in your local machine. You will need two things:
- **MySQL Server**          (to launch a MySQL POD in localhost)
- **MySQL Workbench**       (recommended)

⬆️ For that purpose you may use [MySQL installer](https://dev.mysql.com/downloads/installer/) for Windows

Once installed and started, create table in your mysql schema:
```sql
CREATE TABLE productos ( id SERIAL PRIMARY KEY, descripcion VARCHAR(100) NOT NULL, nombre VARCHAR(40) NOT NULL, estado VARCHAR(40) NOT NULL, precio NUMERIC(6, 2));
```

Start app: 
```bash 
./mvnw spring-boot:run
```