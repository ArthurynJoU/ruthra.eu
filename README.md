# ruthra.eu

My personal portfolio website, with a serverless contact form backend written in Java.

Live site: **https://www.ruthra.eu**

<!-- TODO: add a screenshot of the home page, e.g. ![Home page](docs/screenshot.png) -->

## Tech stack

**Frontend**
- HTML, CSS and vanilla JavaScript
- [Vite](https://vite.dev/) for local development and builds
- Hosted on Cloudflare Workers, deployed automatically from GitHub

**Backend (contact form)**
- Java 21 on AWS Lambda
- Amazon API Gateway
- Amazon SES

**Infrastructure**
- DNS on Cloudflare, domain registered at Infomaniak
- Email hosting at Infomaniak

## Architecture

The form sends the data as JSON with `fetch`. The Lambda function validates every field again on the server side, then sends an email through SES. The visitor's address is set as `Reply-To`, so a reply goes straight to them. The result is shown to the visitor in a toast notification.

## Running locally

**Website**

```bash
npm install
npm run dev
```

Then open http://localhost:5173.

**Backend**

```bash
cd contact-api
mvn test      # run the validator tests
mvn package   # build the deployable JAR (with dependencies)
```

## License

© 2026 Artur Paulouski. All rights reserved.
