# interview-test-master
 
ReadMe

About my solution

Running the application and going to http://localhost:8080/swagger-ui.html. You will see "busy-flights-controller" and " GET /api/1.0/busy-flights/list" endpoint. Please fill the field correctly and execute the method. You will see a json array with 2 elements will be shown. In this list, there will be flights from both CrazyAir and ToughJet sorted by fare.

My approach

1. I used the "spring-cloud-starter-config" dependency for getting configs from config server. 
2. I used "spring-cloud-starter-netflix-eureka-client" dependency to register "Eureka-Server". Eureka client is disabled.  
	2.1 If you want to change configuration, you can see bootstrap and application file in the resources. 
3. I used Swagger for documentation and ease of use. (http://localhost:8080/swagger-ui.html)
4. I created FlightSupplierService interface and FlightSupplier enum for each flight supplier implement it. 
	Follow the steps below to add a new supplier.
	4.1. The flight supplier' name adds in the FlightSupplier.
	4.2. Create a new service and impelement the FlightSupplierService interface.
	4.3. Create a new converter service for new flight supplier.
	4.4. Create a new feign client interface and fallback service for new flight supplier. When the service couldn't reach flight supplier service, we run the fallback scenario.
5. Exception handling
	5.1 When an exception defined in the service is thrown, the GlobalExceptionHandler class will catch this exception and handle it.
	5.2 If the supplier has own exception, we should define in exceptions folder and than we add a new exception handling method in GlobalExceptionHandler class.
6. Multi-language support
	6.1 You can see three languages support in the resources folder.
	6.2 If you want to add new language, you should create new resource file with named Message. Please add the new language to the Language enum.
7. Test
	7.1 Controller test
		7.1.1 I added controller tests. I tested request parameters validation.
		7.1.2 "Accept-Language" request header tested for multilanguage.
		7.1.3 Sorting the results by price was tested. 
	7.2 Service test
		7.2.1 The service was tested for the presence of flights.
		7.2.2 The service was tested for the absence of flights. The exception was thrown when the flight was not found. 
