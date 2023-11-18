# SpringSecureSignIn

This is a Project for a secure login and registration system using Java Spring with Spring Security.

Features:
- User Roles
- Registration
- Login
- Email-Confirmation using Java Mail Sender
- PostgreSQL Database for Users
- Password Encryption with BCrypt

How to use:
- Change credentials for Database and Email in the application.properties file.
- For testing purpose I use MailDev on Docker but any Mailserver can be configured in the application.properties file

Email-Confirmation can be deactivated in den EmailConfiguration class.

The AppUserController is currently implementing a Thymleaf view for resending the Email.
