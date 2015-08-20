# README #

### What is this repository for? ###

* This repository contains 2 parts.
 - Sample Android Application (maepaysoh-sample-app Module) and
 - Android SDK (maepaysoh-sdk Module) for MaePaySoh API

Sample Android Application uses SDK functions.

### Minimum Requirements ###

* Android SDK API 14
* Build Tool Version 23.0.0 rc3 or higher
* Gradle Version 2.4

### MaePaySoh API ###

`maepaysohsdk` is programmed to be compitable with MaePaySoh API, specified at http://myanmarapi.github.io/endpoints.html

### How To Add MaePaySoh Android SDK for your Android Project ###

* Clone this repository "maepaysoh-android".
* Create your own new Android Project in Android Studio.
* Open File > New > Import Module.
* Select "maepaysoh-sdk" folder in "maepaysoh-android" project folder.
* Open File > Project Structure to open "Project Structure" dialog.
* Select your own project module (e.g app) under "Modules" selection.
* Select "Dependencies" tab.
* Add new "Module depedency" by clicking "+" button.
* Select "maepaysoh-sdk" and click "OK"

### MaePaySoh API Key ###

You will need your own API key to use Android SDK for API.
Get your own APK Key at http://myanmarapi.github.io/

### Basic Structure of SDK ###

SDK includes following classes;

* `MaePaySohApiWrapper`, this is helper class generator for various API endpoints.
* `PartyAPIHelper`, wrapper class for Party API endpoints.
* `CandidateAPIHelper`, wrapper class for Candidate API endpoints.
* `FAQAPIHelper`, wrapper class for FAQ API endpoints. 

### Setting Up API Wrapper ###

You can setup the API Wrapper as follow;
```java
MaePaySohApiWrapper apiWrapper = new MaePaySohApiWrapper(this);
apiWrapper.setApiKey(API_KEY); // Put your own API Key
apiWrapper.setFont(MaePaySohApiWrapper.FONT.unicode); // Set Unicode/Zawgyi response from server
```

### Setup and Use of API Helpers ###

#### Party API Helper ####

You can setup Party API Helper as follow;
```java
PartyAPIHelper partyWrapper = apiWrapper.getPartyApiHelper();
```

Followings methods are available for PartyAPIHelper
* `getParties` returns `List<Party>` list of `Party` Objects.
* `getPartiesAsync` pre-includes AsyncTask call to provide Callback Function.
* `getPartiesFromCache` loads `List<Party>` from cache in case of Offline.

#### Candidate API Helper ####

#### FAQ API Helper ####

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact
