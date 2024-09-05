# Project Startup Document

## Project Name

**Dubbo Microservices Mall**

## Project Background

This project aims to build a microservices mall system based on Java and Dubbo. By adopting a modular design, different business functions are divided into independent services, enhancing the maintainability and scalability of the system. The system includes core business modules such as advertisements, shopping cart, currency conversion, email notifications, payments, product catalogs, recommendations, logistics, and checkout.

## Project Goals

The goals of this project are:

1. Build a fully functional and scalable microservices mall system.
2. Ensure efficient communication between service modules through Dubbo, guaranteeing system stability and high availability.
3. Provide a user-friendly interface, allowing users to access the frontend page via a web browser and perform shopping operations.
4. Support multiple currency conversions and online payment functions.

## Service Module Overview

1. **adService**: Advertisement service, responsible for managing and displaying advertisement content.
2. **cartService**: Shopping cart service, responsible for handling user shopping cart operations, such as adding and removing items.
3. **currencyService**: Currency service, responsible for providing conversion functions between multiple currencies.
4. **emailService**: Email service, responsible for sending order confirmations, promotional information, and other email notifications.
5. **paymentService**: Payment service, responsible for processing user online payment operations.
6. **productCatalogsService**: Product catalog service, responsible for managing product classifications and information display.
7. **recommendationService**: Recommendation service, providing product recommendations based on user behavior.
8. **shippingService**: Logistics service, handling order logistics information and delivery services.
9. **checkoutService**: Checkout service, summarizing the contents of the shopping cart, generating orders, and handling the checkout process.
10. **fronted**: Frontend service, providing the user interface for browsing and shopping.

## Service Startup Order

To ensure the correct startup of the system, the services should be started in the following order:

1. Start `adService`
2. Start `cartService`
3. Start `currencyService`
4. Start `emailService`
5. Start `paymentService`
6. Start `productCatalogsService`
7. Start `recommendationService`
8. Start `shippingService`
9. Start `checkoutService`
10. Start `fronted`

**Note: The module boot order is reversible.**

## Accessing the Frontend Page

After all services are started, the mall's frontend page can be accessed at: http://localhost:9000/static

## Project Configuration

### Environment Requirements

- JDK 17+
- Apache Dubbo 3.0+
- Spring Boot 3.0+

### Configuration Files

Each service’s configuration file is located in `src/main/resources/application.yml`. Please adjust the configuration information according to your environment.
