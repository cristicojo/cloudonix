Programming Test
Cloudonix.io

OVERVIEW
The test involves writing a simple Vert.x REST service that compiles a list of words and performs
simple text analysis on them.
● Please implement the program in Java. See Appendix A for an example project.
● The program can be published on GitHub or a comparable source code hosting service,
or you can email it back.
● Make sure all IO operations use the Vert.x promises/Future API.
● Check the algorithms performance - the responsiveness of the service should not
degrade too much when the data set increases significantly. The service should perform
well when handling at least 100,000 words.

REQUIREMENTS
1. The program will expose an HTTP REST interface that responds to POST requests on the
URL /analyze.
2. When an HTTP client POSTs a JSON object with the string property “text”, the server will
compare the content of the provided “text” field to the list of words previously provided
through the same API, and return a JSON response containing an object with two fields:
a. The field “value” will contain the word closest to the word provided in the
request in terms of total character value, where character values are listed as a=1,
b=2 and so on.
b. The field “lexical” will contain the word closest to the word provided in the
request in terms of lexical closeness - i.e. that word that sorts lexically closest to
the provided request.

3. The server will store any word submitted in a local file, so it can be compared against
future requests, even across server restarts.
4. If no words are found to match against (as in the first request), the server will return null
for both response fields.
