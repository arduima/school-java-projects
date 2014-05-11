stevens-esql
============

### Setup
#### Clone the Repo
`cd` into the directory where you want to save the project  and `git clone https://github.com/arduima/stevens-esql.git`
#### Create PostgreSQL User and DB
[Download](http://www.postgresql.org/download/) and install the PostgreSQL database for your specific OS.  
Connect with the *postgres* user: `psql postgres`  
Create User: `CREATE USER esql WITH PASSWORD 'password' CREATEDB;`  
Check if user was created: `\du`  
Create database with the new role: `CREATE DATABASE esql OWNER esql;`  
Check if database was created `\list`  
Connect to the new database: `\connect esql`  
Create the *sales* table from `sdap.sql` file: `\i {your-dir}/stevens-esql/Database/sdap.sql`  
Grant full privilege to the *esql* user: `GRANT ALL PRIVILEGES ON TABLE sales TO esql;`

