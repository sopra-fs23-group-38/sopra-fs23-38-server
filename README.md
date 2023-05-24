[//]: # (<p align="center">)

[//]: # (  <img src="https://readme-typing-svg.demolab.com/?lines=Sopra+Group+20!;Guess+The+Country!&font=Fira%20Code&center=true&width=600&height=80&duration=4000&pause=500" alt="Example Usage - README Typing SVG">)

[//]: # (</p>)

[//]: # ()
[//]: # (<div align="center">)

[//]: # ()
[//]: # ([![Lines of Code]&#40;https://sonarcloud.io/api/project_badges/measure?project=sopra-fs23-group-20_client&metric=ncloc&#41;]&#40;https://sonarcloud.io/summary/new_code?id=sopra-fs23-group-20_client&#41;)

[//]: # ([![Vulnerabilities]&#40;https://sonarcloud.io/api/project_badges/measure?project=sopra-fs23-group-20_client&metric=vulnerabilities&#41;]&#40;https://sonarcloud.io/summary/new_code?id=sopra-fs23-group-20_client&#41;)

[//]: # ()
[//]: # (</div>)

## Introduction

This is an online Q&A platform based on UZH IFI's study and life, we hope the students can exchange the thoughts on this platform and get the answers they wish for.

## Technologies

- [Springboot](https://spring.io/) - Java framework to create a micro service
- [Gradle](https://gradle.org/) - Automated building and management tool
- [MySQL](https://www.mysql.com/) - Database
- [React](https://reactjs.org/docs/getting-started.html) - Javascript library for the whole frontend
- [Ant Design](https://ant.design/) - React design Component library
- [Github Projects](https://github.com/explore) - Project Management
- [Figma](https://figma.com/) - Mockups
- [Google Cloud](https://cloud.google.com/) - Deployment
- [SonarCloud](https://sonarcloud.io/) - Testing & Feedback of code quality

## High-level components（需要修改）



## Deployment and Database(需要修改)

### Deployment on Google Cloud
Our application is hosted on both [Google Cloud URL](https://sopra-fs23-group-20-client.oa.r.appspot.com/) and [Raspberry Pi URL](https://sopra-fs23-group20-client.pktriot.net/). To differentiate between the server URL for those two versions, a custom build is required for the Raspberry Pi version. This specification is outlined in the "scripts" and "build:custom" sections of our [package.json](package.json) file.

When the "npm run build:custom" command is executed, the REACT_APP_API_URL is automatically set to the Raspberry Pi's hosted server address: [https://sopra-fs23-group20-server.pktriot.net](https://sopra-fs23-group20-server.pktriot.net). This ensures that the [getDomain.ts](src/helpers/getDomain.ts) script sets the correct server URL, which is crucial for establishing a connection between the client and the server.

At some point we will run out of credits in the Google Cloud, in this case only the Raspberry PI version will be accessible.
### MySQL Database
## Launch and Development

## Prerequisites and Installation

For your local development environment, you will need Node.js. You can download it [here](https://nodejs.org). All other dependencies, including React, get installed with:

`npm install`

Run this command before you start your application for the first time. Next, you can start the app with:

`npm run dev`

Now you can open [http://localhost:3000](http://localhost:3000) to view it in the browser.

Notice that the page will reload if you make any edits. You will also see any lint errors in the console (use Google Chrome).

### Testing

Testing is optional, and you can run the tests with `npm run test`.
This launches the test runner in an interactive watch mode. See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

> For macOS user running into a 'fsevents' error: https://github.com/jest-community/vscode-jest/issues/423

### Build

Finally, `npm run build` builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance: the build is minified, and the filenames include hashes.<br>

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

## Illustrations
### Page
The landing page should look like a modern "landing page" by giving the user a short introduction and should make the user clicking on the register button.  <br /> <br />
![Landing Page](src/components/views/images/LandingPage.png)

### Index Page
The index page should be the first page the user sees before logging in. It should contain a list of all the questions that users created. <br /> <br />

### Register Page
The register page should be the first page the user sees after clicking on the register button. It should contain a form where the user can enter his username, password, email and set their avatar. <br /> <br />

### Login Page
The login page should be the first page the user sees after clicking on the login button. It should contain a form where the user can enter his username and password. <br /> <br />

### Search Page
The search page should be the first page the user sees after clicking on the search button. It should contain a form where the user can enter a search term. <br /> <br />


### Create Question Page
The create question page should be the first page the user sees after clicking on the create question button. It should contain a form where the user can enter a question. <br /> <br />


### Answer Question Page
The answer question page should be the first page the user sees after clicking on the answer question button. It should contain a form where the user can enter a answer. <br /> <br />


### User Center Page
The user center page should be the first page the user sees after clicking on the user center button. It should contain a list about questions, answers and comments they created. <br /> <br />

### Chat Page
The chat page should be the first page the user sees after clicking on the chat button. It should contain a box about messages with other users. <br /> <br />

## Roadmap（需要修改）

### Mobile Compatibility

Our web application is mostly mobile-friendly, but some pages may still require improvements in scaling for optimal viewing on different devices. Another idea in this area would be to implement the project as a dedicated mobile app.

### Add additional Hints

So far we have the following 5 hints based on which the users can try to guess a country: Population, Outline, Location, Flag and Capital. Ideas for additional hints: Most famous landmark, Currency, GDP, National Anthem, Neighboring Countries, ...

### Friends System

The option to add friends, compare yourself with them and invite them to a lobby.

### Achievements

To encourage user engagement and acknowledge accomplishments, it would make sense to implement a series of achievements, including: first correct country guess, first game played, first game won, first friend added, 10 correct country guesses, 10 games played, 10 games won, 10 friends added, and more.

## Authors and acknowledgment

### Contributors

- **Hangchen Xie** - [Github](https://github.com/hangchenxie)
- **Yunlong Li** - [Github](https://github.com/1316827294)
- **Yuanzhe Gao** - [Github](https://github.com/ArthasGAO)
- **Qiyue Shang** - [Github](https://github.com/QiyueShang666)


### Supervision

- **Hyeongkyun (Kaden) Kim** - [Github](https://github.com/hk-kaden-kim)

## License(最后改)

[Apache license 2.0](https://github.com/sopra-fs23-group-38/server/blob/6dc8281b0a876fa1d310626a704e0e4bfa08b86d/LICENSE)
[//]: # (<p align="center">)

[//]: # (  <img src="https://readme-typing-svg.demolab.com/?lines=Sopra+Group+20!;Guess+The+Country!&font=Fira%20Code&center=true&width=600&height=80&duration=4000&pause=500" alt="Example Usage - README Typing SVG">)

[//]: # (</p>)

[//]: # ()
[//]: # (<div align="center">)

[//]: # ()
[//]: # ([![Lines of Code]&#40;https://sonarcloud.io/api/project_badges/measure?project=sopra-fs23-group-20_client&metric=ncloc&#41;]&#40;https://sonarcloud.io/summary/new_code?id=sopra-fs23-group-20_client&#41;)

[//]: # ([![Vulnerabilities]&#40;https://sonarcloud.io/api/project_badges/measure?project=sopra-fs23-group-20_client&metric=vulnerabilities&#41;]&#40;https://sonarcloud.io/summary/new_code?id=sopra-fs23-group-20_client&#41;)

[//]: # ()
[//]: # (</div>)

## Introduction

This is an online Q&A platform based on UZH IFI's study and life, we hope the students can exchange the thoughts on this platform and get the answers they wish for.

## Technologies

- [Springboot](https://spring.io/) - Java framework to create a micro service
- [Gradle](https://gradle.org/) - Automated building and management tool
- [MySQL](https://www.mysql.com/) - Database
- [React](https://reactjs.org/docs/getting-started.html) - Javascript library for the whole frontend
- [Ant Design](https://ant.design/) - React design Component library
- [Github Projects](https://github.com/explore) - Project Management
- [Figma](https://figma.com/) - Mockups
- [Google Cloud](https://cloud.google.com/) - Deployment
- [SonarCloud](https://sonarcloud.io/) - Testing & Feedback of code quality

## High-level components（需要修改）



## Deployment and Database(需要修改)

### Deployment on Google Cloud
Our application is hosted on both [Google Cloud URL](https://sopra-fs23-group-20-client.oa.r.appspot.com/) and [Raspberry Pi URL](https://sopra-fs23-group20-client.pktriot.net/). To differentiate between the server URL for those two versions, a custom build is required for the Raspberry Pi version. This specification is outlined in the "scripts" and "build:custom" sections of our [package.json](package.json) file.

When the "npm run build:custom" command is executed, the REACT_APP_API_URL is automatically set to the Raspberry Pi's hosted server address: [https://sopra-fs23-group20-server.pktriot.net](https://sopra-fs23-group20-server.pktriot.net). This ensures that the [getDomain.ts](src/helpers/getDomain.ts) script sets the correct server URL, which is crucial for establishing a connection between the client and the server.

At some point we will run out of credits in the Google Cloud, in this case only the Raspberry PI version will be accessible.
### MySQL Database
## Launch and Development

## Prerequisites and Installation

For your local development environment, you will need Node.js. You can download it [here](https://nodejs.org). All other dependencies, including React, get installed with:

`npm install`

Run this command before you start your application for the first time. Next, you can start the app with:

`npm run dev`

Now you can open [http://localhost:3000](http://localhost:3000) to view it in the browser.

Notice that the page will reload if you make any edits. You will also see any lint errors in the console (use Google Chrome).

### Testing

Testing is optional, and you can run the tests with `npm run test`.
This launches the test runner in an interactive watch mode. See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

> For macOS user running into a 'fsevents' error: https://github.com/jest-community/vscode-jest/issues/423

### Build

Finally, `npm run build` builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance: the build is minified, and the filenames include hashes.<br>

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

## Illustrations
### Page
The landing page should look like a modern "landing page" by giving the user a short introduction and should make the user clicking on the register button.  <br /> <br />
![Landing Page](src/components/views/images/LandingPage.png)

### Index Page
The index page should be the first page the user sees before logging in. It should contain a list of all the questions that users created. <br /> <br />

### Register Page
The register page should be the first page the user sees after clicking on the register button. It should contain a form where the user can enter his username, password, email and set their avatar. <br /> <br />

### Login Page
The login page should be the first page the user sees after clicking on the login button. It should contain a form where the user can enter his username and password. <br /> <br />

### Search Page
The search page should be the first page the user sees after clicking on the search button. It should contain a form where the user can enter a search term. <br /> <br />


### Create Question Page
The create question page should be the first page the user sees after clicking on the create question button. It should contain a form where the user can enter a question. <br /> <br />


### Answer Question Page
The answer question page should be the first page the user sees after clicking on the answer question button. It should contain a form where the user can enter a answer. <br /> <br />


### User Center Page
The user center page should be the first page the user sees after clicking on the user center button. It should contain a list about questions, answers and comments they created. <br /> <br />

### Chat Page
The chat page should be the first page the user sees after clicking on the chat button. It should contain a box about messages with other users. <br /> <br />

## Roadmap（需要修改）

### Mobile Compatibility

Our web application is mostly mobile-friendly, but some pages may still require improvements in scaling for optimal viewing on different devices. Another idea in this area would be to implement the project as a dedicated mobile app.

### Add additional Hints

So far we have the following 5 hints based on which the users can try to guess a country: Population, Outline, Location, Flag and Capital. Ideas for additional hints: Most famous landmark, Currency, GDP, National Anthem, Neighboring Countries, ...

### Friends System

The option to add friends, compare yourself with them and invite them to a lobby.

### Achievements

To encourage user engagement and acknowledge accomplishments, it would make sense to implement a series of achievements, including: first correct country guess, first game played, first game won, first friend added, 10 correct country guesses, 10 games played, 10 games won, 10 friends added, and more.

## Authors and acknowledgment

### Contributors

- **Hangchen Xie** - [Github](https://github.com/hangchenxie)
- **Yunlong Li** - [Github](https://github.com/1316827294)
- **Yuanzhe Gao** - [Github](https://github.com/ArthasGAO)
- **Qiyue Shang** - [Github](https://github.com/QiyueShang666)


### Supervision

- **Hyeongkyun (Kaden) Kim** - [Github](https://github.com/hk-kaden-kim)

## License(最后改)

[Apache license 2.0](https://github.com/sopra-fs23-group-38/server/blob/6dc8281b0a876fa1d310626a704e0e4bfa08b86d/LICENSE)

