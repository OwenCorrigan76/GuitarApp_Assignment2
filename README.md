Name: Owen Corrigan
GitHub Repository - https://github.com/OwenCorrigan76/GuitarApp_Assignment2

## Overview
The app is for guitar enthusiasts that wish to share details about their instruments. 
The user can:
+ Add the make and model of the guitar
+ Upload images of guitars along with make and model of the instrument
+ Add a manufacture date
+ Add the location of the user

## Authentication
The user is first greeted with the login page. There are two login options:
+ Create an account with an email address and password
+ Sign in with a Google account
<br/>

Both methods utilise Firebase authentication at the back end.
<br/>

## App Features
<br/>
Below is a list of app features:

### Feature 1 - Add Guitar
+ Choose a guitar make with a drop down menu
+ The model can be typed in
+ A date picker allows the user to add the guitar's manufacture date
+ The user location can be added by clicking setLocation and moving a Google Maps marker to the desired latitude and longitude coordinates
+ An image can be uploaded and added to the guitar profile
+ AN estimated valuation of a guitar can be added using a number picker


### Feature 2 - List of Guitars  
+ View a card with Valuation, Make, Model, Manufacture date, Lat + Lng details, and Images 
+ Floating add button allows the user to navigate to the Add Guitar page
+ If the user clicks on the card, they are brought to a report card that displays the details about the guitar
+ The image on the right displays the users Google account image if they are logged in with their Google account
+ The image on the left is the image of the guitar that the user has uploaded [b1]
+ Swipe left to delete feature 
+ Swipe right to edit feature


### Feature 3 - Useful Links
+ This page has useful URL links related to the guitars [l1]


### Feature 4 - About This App
+ A basic splash screen with an image and some text


### Feature 5 - Guitar Locations
+ Display a Google Map with user location markers [b2]


### Feature 6 - Guitar Details
+ Display all the details regarding the individual guitar displayed in the clicked card [b4]
  <br/>

## Navigation
<br/>

### Add Guitar Nav Bar Menu
+ Guitar List navigation can be accessed using the Vertical Ellipsis in the top right corner
+ Useful Links can also be accessed using the Vertical Ellipsis in the top right corner
+ Back arrow will bring the user back to the landing page which is the Guitar List page
<br/>

### Guitar List Nav Bar Menu
+ Add Guitar navigation can be accessed using the Vertical Ellipsis in the top right corner
+ Useful Links can also be accessed using the Vertical Ellipsis in the top right corner
+ Left toggle switch turns on and off dark mode [l2] [b3]
+ Right toggle switches from logged in user being displayed, to all users being displayed
+ Hamburger menus in top left corners reveals the nav drawer menu
<br/>

### Nav Drawer Menu
+ Click User Image to change profile image
+ Add Guitar navigates the the Add Guitar page
+ Guitar List navigates to the Guitar List page
+ Guitar Locations navigates to all users location on Google maps [b2]
+ About This App navigates to the About This App splashscreen
+ Sign Out signs the user out of the app
<br/>

### Future Releases Fixes / Features
+ Move useful links to the nav drawer
+ Replace dark mode toggle with a button or move to nav drawer
+ Guitar Locations can display markers on a Google map to indicate users location (if desired)
+ Add audio feature to allow upload of wav / mp3 files
+ Improve the About This App splashscreen

### Known Bugs
b1: Guitar image replaces user image when guitar image is uploaded
<br/>
b2: Google Maps markers not showing yet
<br/>
b3: Dark mode toggle switch needs reworking as it currently does not toggle on and off with a individual clicks
<br/>
b4: Enable user to edit the details in Guitar Detail page
<br/>

### Referenced Video Tutorials
l1: https://www.youtube.com/watch?v=tL9fCKC2lNA
<br/>
l2: https://www.geeksforgeeks.org/how-to-create-a-dark-mode-for-a-custom-android-app-in-kotlin/
