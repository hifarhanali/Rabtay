# Rabtay
Rabtay is an android app similar to the mobile pre-installed app `Contacts` which displays the list of users contacts. In fact, it uses the `Contacts`  app content provider to retrieve list of contacts. 

## Flow
Rabtay is an android app that loads a user's contact information (including name, number, and photo) from the Contact Content Provider and saves them in a local SQLite database with the help of Room ORM. It then fetches the contacts from the database and binds them in a recycler view. Users can also search for contacts either by their name or number.

## Disclaimer
If you find any bugs or have something to add, notify me with a pull request to this repository.

## Tools & Technologies

<div style="display: flex">
  <img src="https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white" />
  <img src="https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white" />
</div>
