# Weedmaps Android Code Challenge!

Hi there!  Thank you for taking the time to conduct the Weedmaps Android code challenge.  Please use this as a foundation to help you save time setting up your workspace; this project already contains some common dependencies and frameworks used in most Android projects.  
**If there are other dependencies and/or frameworks that you'd like to introduce/use please feel free to add them!**

# Dependencies
 - Kotlin Ext
 - Kotlin Coroutines
 - Mockito Kotlin
 - Koin
 - Retrofit
 - Moshi
 - Glide
 - Constraint Layout

## First Thing First
Register for and use the Yelp Fusion API [https://www.yelp.com/developers/documentation/v3](https://www.yelp.com/developers/documentation/v3)

## Requirements

 1. Provide a simple search view and display the search result of businesses as a list.
 2. The list must endlessly scroll based on the API's paging functionality.
 3. Each business item in the list should have: **name of the business**, **an image of the business**, and **the top review of the business(reference [here](https://www.yelp.com/developers/documentation/v3/business_reviews) for how to get the business review)**.
 4. Include Unit Tests for parts of your code with important functionality (no need to get 100% code coverage on the whole project). 

## Notes

 - **Important:** The Fusion API has a **query-per-second limit of 5**. Keep this in mind when configuring your API requests, otherwise you’ll receive a 429 HTTP error code for some of your requests. It would be a good idea to throttle the speed of your subsequent network requests.

## Links

 - [Yelp Fusion API Documentation](https://www.yelp.com/developers/documentation/v3)

## Project Submission Instructions

Please package your APK and send that along with a link to the project on Github.
