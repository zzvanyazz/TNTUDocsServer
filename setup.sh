set -e
service mysql start
mysql < setup.sql
service mysql stop