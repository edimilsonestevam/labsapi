Feature: Livro

Scenario: Consulta de título
Given eu executo a API Place Holder atraves da URL <url>
When o sistema retorna um Cookie
Then obtenho o codigo da resposta <responseCode>
And obtenho o corpo da resposta <responseBody>


| url	                                      | responseCode | responseBoby	                                  |
| "http://jsonplaceholder.typicode.com/posts" |	     200	 | "laudantium voluptate suscipit sunt enim enim" |