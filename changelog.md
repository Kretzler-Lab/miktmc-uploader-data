
# Changelog

## Release 1.3
Brief summary of what's in this release:

### Breaking changes
Breaking changes include any database updates needed, if we need to edit any files on system (like .env or certs, etc). Things that are outside of the code itself that need changed for the system to work.

### Non-breaking changes
Just a place to keep track of things that have changed in the code that we may want to pay special attention to when smoke testing, etc.


## Release v1.2

- Duplicate file prevention
- Bad Fine Uploader state prevention
- Wording on attachments link
- Use name from portal instead of Shibboleth
- Wording fix on failed uploads
- Metadata removal on later-added files
- Fix validation code to skip old files

## Release v1.1.1
This point release fixes a problem where the checksum generation was causing junk files and throwing the package in error. 

- Checksums have been turned off
- .nfsXXX files are ignored during the integrity check

## Release v1.1
* BiopsyID filter
* StudyID and Last Modified added to upload information on front page
* Upload locking (admins only)
* File type restriction on file upload dialog (JPG/JPEG only)
* Curator Data Lake file view button (admins only)
* File deletion (admins and upload owners)
* File adding / replacing (admins and upload owners)
* Duplicate file name restriction
* Table file view with original and HALOLink file names
* Study-dependent StudyID hint text
* New MiKTMC email account for notifications
* Additional information and links to Data Lake added to notification emails

## Release 1.0.1

### Breaking changes

New notifications for unauthorized users require latest version of the Notification Service. 

### Non-breaking changes

User roles are now sent to front end. 

Duplicate biopsy IDs are not allowed unless they are in a different study or data type
