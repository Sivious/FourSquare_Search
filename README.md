# FourSquare_Search

### INTRODUCTION
I want to divide this dossier in two parts. Architecture and logic. 

I want to add that I have found a bug in foursquare where nobody could create an app to get access to API. I reported it to Foursquare and after a couple of days they solved it 

### ARCHITECTURE
Reading the test rules I saw MVC as a requirement, I was not sure if implementing a pure MVC was a good idea, mainly because it is "mostly" a web architecture oriented so I decided to orient the MVC architecture to an clean architecture approach and SOLID principles, because it gave me really good results previously and requirements seemed to be web orientend. 

Structure: 

- framework
- logic
- model
- ui

##### FRAMEWORK

You will find here all the classes needed by the core of the app to interact with foursquare (foursquare dir.) HTTPRequest (network dir.) and data persistence (data dir.). This classes are completely independent of bussiness logic, my thoughts are into SOLID principles where if I need to change the data persistence, for example, it would be really easy to do and near zero changes in bussiness. 

##### LOGIC

You will find here all the classes related with the bussiness logic, such as the way to ask foursquare for data (venues dir.) and interactors with HTTP Requests. 

##### MODEL

You will find here the bussiness entity modelling. In this case, information related to venues. 

##### UI

You will find here two different kind of classes: presenters and views. 
Views are the classes related exclusive with the user interface, in other words the activities. You will not find here any bussiness logic, just user interface logic. 
Presenters, this kind of classes works like controllers, a tier between the pure bussiness logic and the user interface. 
This architecture is simple, really understandable and very easy to develop and debug. It is based on Robert C. Martin and Uncle Bob knoledges. 


### LOGIC

My first approach to test was to think in how the app would work. My interpretations of the requirements: 
- An activity to login: Just one button to ask Foursquare for authorization using the public API.
- A second activity to search venues: A edittext to fill on it the venue, a button to submit the search string, a googlemap to show the results and toast system to show messages.

##### APP FLOW
- Entering the app, login into Foursquare
- If Ok -> Show the second activity. 
- Search for a venue by its name. (There is no useful foursquare webservice to do this without latitud and longitud so ask google for last known location. 
- From the list of possible venues, get the first (more accurated according to Foursquare Doc)
- Get the most popular (trending venues) close to the venue. 
- Add markers on the map for each venue including the searched.

Firstly, I thought in a search of venues only by their name, showing only the venue with the exactly same name as searched, but the best webservice provided by Foursquare was the search webservice with global intent as parameter. The test done to the webservice showed a not very accuracy result so I decided to use the search by name and location. I needed to integrate the google LastKnownLocation but it would not be a problem because I have worked with maps and locations a lot before.

With the venue retrieved by Foursquare, I could get latitud and longitud from it and search for trending venues around it to show on the map.  

### THINGS I HAD TO DISCART BECAUSE OF TIMING
- Dagger: I am used to work with dependency injection but it's quite expensive (in time terms) to configure, so I decided to discard. 
- A beautiful and friendly interface: I would really love to add a good and beautiful design using material design but in some aspects is toilsome. 
- A loading spin: a waiting spin would be great to report user that the app is doing background jobs such as http requesting. 
- Refactoring: In some code I have done "bad smells". I would love to change the way http url is build. I should use params in fact of hardcode params in url. The use of (runOnUiThread) returning to UI is not the better way to do it, it shall be done in presenter instead the view. 

### CONCLUSIONS

It have been a very interesting code test. I have enjoyed a lot. It showed me my strengths and weakness and the point I need to improve. Very interesting.
