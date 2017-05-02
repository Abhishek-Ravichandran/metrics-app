# metrics-app

Simple Web API for Metrics

To deploy the application, build the .war file with `ant metrics-war` and deploy to Tomcat or any other servlet container.

To run JUnit tests for the API, simply run `ant run-metrics-tests`.

## API routes

`POST /createmetric (metricname)` - Creates a metric

`POST /addvalue (metricname) (value)` - Adds a metric

`GET /getmean (metricname)` - Retrieves the mean for a given metric - `O(n)`

`GET /getmedian (metricname)` - Retrieves the median for a given metric - `O(nlgn)`

`GET /getmin (metricname)` - Retrieves the min value for a given metric - `O(n)`

`GET /getmax (metricname)` - Retrieves the max value for a given metric - `O(n)`







