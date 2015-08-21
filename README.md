# QUICK OVERVIEW #

### Content မာတိကာ ###

1. [What is this Repository [:arrow_heading_down:]](#1-what-is-this-repository)?
2. [Minimum requirements.](#2-minimum-requirements)
3. [MaePaySoh API.](#3-maepaysoh-api)
4. [MaePaySoh SDK Overview.](#maepaysoh-sdk-overview)
5. [How to use this Repository?](#-5-how-to-use-this-repository)
    - 5.1 Add MaePaySoh Android SDK for your Android Project [:arrow_heading_down:](#51)
    - 5.2 Get MaePaySoh API Key
    - 5.3 Setup API Wrapper [:arrow_heading_down:](#53)
    - 5.4 Use of API Helpers
    - 5.4.1 How to use PartyAPIHelper [:arrow_heading_down:](#541)
    - 5.4.2 How to use CandidateAPIHelper [:arrow_heading_down:](#542)
    - 5.4.3 How to use FAQAPIHelper [:arrow_heading_down:](#543)
    - 5.4.4 How to use GeoAPIHelper [:arrow_heading_down:](#544)
6. Contributions

---

### 1. What is this Repository?

This is part of the [Opensource Libraries](http://myanmarapi.github.io/) for [MaePaySoh မဲပေးစို့](http://maepaysoh.org) project.

This repository is an Android Project which comprises of 2 major parts;

 1. Sample Android Application (`sample-app` Module) and
 2. Android SDK (`maepaysohsdk` Module) for MaePaySoh API

The `sample-app` module utilizes `maepaysohsdk` as a dependency.

### 2. Minimum Requirements

Followings are required to build this project folder;

* Android SDK API 14
* Build Tool Version 23.0.0 rc3 or higher
* Gradle Version 2.4

### 3. MaePaySoh API

MaePaySoh API is the primary data provider for this Android Application `sample-app`.
The SDK `maepaysohsdk` module is the client library and complys to [MaePaySoh API specification](http://myanmarapi.github.io/endpoints.html).

API features are tested with [this PostMan Collection](https://github.com/MyanmarAPI/maepaysoh-android/blob/master/MaePaySohAPI-20150821.json.postman_collection) for the development of SDK.

### MaePaySoh SDK Overview

Followings are primary utility functions of SDK to get data from API.

1. `MaePaySohApiWrapper`, this is helper-class-generator.
2. `PartyAPIHelper`, wrapper class for Party API endpoints.
3. `CandidateAPIHelper`, wrapper class for Candidate API endpoints.
4. `FAQAPIHelper`, wrapper class for FAQ API endpoints. 
5. `GeoAPIHelper`, wrapper class for Geolocation endponts.

### <a id="5"></a> 5. How to use this Repository? ###

The SDK in this repository can be used as dependency for your Android Application, or you can fork and transform the Sample Application to further creative ideas.

#### <a id="51"></a> 5.1 Add MaePaySoh Android SDK for your Android Project ####

To use the SDK as dependency for your own Android Project.

* Clone this repository `maepaysoh-android`.
* Create your own new Android Project in Android Studio.
* Open `File > New > Import Module`.
* Select `maepaysohsdk` folder in `maepaysoh-android` project folder.
* Open `File > Project Structure` to open `Project Structure` dialog.
* Select your own project module (e.g app) under `Modules` selection.
* Select `Dependencies` tab.
* Add new `Module depedency` by clicking `+` button.
* Select `maepaysohsdk` and click `OK`

#### <a id="52"></a> 5.2 Get MaePaySoh API Key ####

Own API key is required to connect to Mae Pay Soh API. It is required to put into SDK to connect to API. You can get your own APK Key at [MaePaySoh Website](http://maepaysoh.org/dashboard/applications/create).

#### <a id="53"></a> 5.3 Setup API Wrapper ####

You can setup the API Wrapper as follow;
```java
MaePaySohApiWrapper apiWrapper = new MaePaySohApiWrapper(this);
apiWrapper.setApiKey(API_KEY); // Put your own API Key
apiWrapper.setFont(MaePaySohApiWrapper.FONT.unicode); // Set Unicode/Zawgyi
```

#### <a id="54"></a> 5.4 Use of API Helpers ####

`APIHelper`s are generated from `MaePaySohApiWrapper` object.

##### <a id="541"></a> 5.4.1 How to use PartyAPIHelper #####

Party API Helper is created as follow;

```java
PartyAPIHelper partyApiHelper = apiWrapper.getPartyApiHelper();
```

Followings methods are available for PartyAPIHelper

* `getParties()` returns `List<Party>` list of `Party` Objects.
* `getParties(PartyAPIPropertiesMap)` returns `List<Party>` list of `Party` Objects for search criterias provided in PropertiesMap.
* `getPartiesAsync(callbackFunction)` pre-included AsyncTask call to provide Callback Function.
* `getPartiesAsync(PartyAPIPropertiesMap, callbackFunction)`
* `getPartiesFromCache` loads `List<Party>` from cache in case of Offline.
* `searchPartiesFromCache(string)` returns `List<Party>` list of `Party` Objects in cache, matches with search `string`.

##### <a id="542"></a> 5.4.2 How to use CandidateAPIHelper  #####

Candidate API Helper is created as follow;

```java
CandidateAPIHelper candidateApiHelper = apiWrapper.getCandidateAPIHelper();
```

Following methods are available for CandidateAPIHelper;

* `getCandidates` retrieve candidate list from API.
* `getCandidatesAsync` function with AsyncTask, which accepts callback function.
* `getCandidatesFromCache` retrieve candidate list from cached data.
* `searchCandidateFromCache` search candidate list from cached data.
* `getCandidateById` retrieve candidate by given ID from API.
* `getCandidateByIdAsync` function with AsyncTask, which accepts callback function.
* `getCandidateByIdFromCache` retrieve candidate for given ID from cached data.

##### <a id="543"></a> 5.4.3 How to use FAQAPIHelper  #####

FAQ API Helper is created as follow;

```java
FAQAPIHelper faqApiHelper = apiWrapper.getFAQAPIHelper();
```

Following methods are available for FAQ API Helper.

* `getFaqs` retrieve FAQ data from API.
* `getFaqsAsync` included AsyncTask, which accepts callback function.
* `getFaqsFromCache` retrieve FAQ data from cache.
* `searchFaq` search in FAQ data from API.
* `searchFaqsAsync`  included AsyncTask, which accepts callback function.
* `searchFaqFromCache` search in FAQ data from cache.
* `getFaqById` retrieve FAQ entry for given ID from API.
* `getFaqByIdAsync` included AsyncTask, which accepts callback function.

##### <a id="544"></a> 5.4.4 How to use GeoAPIHelper  #####


### <a id="6"></a> 6. Contributions ###

