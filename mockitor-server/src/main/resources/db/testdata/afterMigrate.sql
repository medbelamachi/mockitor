insert into application (id, description, name, url)
values (1, 'Onboarding Service', 'onboarding-service', 'http://localhost:8081');

insert into application (id, description, name, url)
values (2, 'Cards Ms', 'cards-ms', 'http://localhost:8081');

insert into dependency (id, application_id, description, name, url)
values (1, 1, 'Party MS', 'party-ms', 'http://localhost:8082');
insert into dependency (id, application_id, description, name, url)
values (2, 1, 'gadz MS', 'gadz-ms', 'http://localhost:8082');
insert into dependency (id, application_id, description, name, url)
values (3, 2, 'liabilities MS', 'liabilities-ms', 'http://localhost:8082');


insert into endpoint (id, dependency_id, data)
values (1, 1,
        '{\"request\":{\"urlPattern\":\"http://localhost:8084/.*\",\"method\":\"POST\",\"headers\":{\"Connection\":{\"equalTo\":\"xxx\"}}},\"response\":{\"status\":200,\"proxyBaseUrl\":\"http://localhost:8084\"}}"');


insert into endpoint (id, dependency_id, data)
values (2, 1,
        '{\"request\":{\"urlPattern\":\"http://localhost:8084/.*\",\"method\":\"POST\",\"headers\":{\"Connection\":{\"equalTo\":\"xxx\"}}},\"response\":{\"status\":200,\"proxyBaseUrl\":\"http://localhost:8084\"}}"');


insert into endpoint (id, dependency_id, data)
values (3, 2,
        '{\"request\":{\"urlPattern\":\"http://localhost:8084/.*\",\"method\":\"POST\",\"headers\":{\"Connection\":{\"equalTo\":\"xxx\"}}},\"response\":{\"status\":200,\"proxyBaseUrl\":\"http://localhost:8084\"}}"');


insert into endpoint (id, dependency_id, data)
values (4, 3,
        '{\"request\":{\"urlPattern\":\"http://localhost:8084/.*\",\"method\":\"POST\",\"headers\":{\"Connection\":{\"equalTo\":\"xxx\"}}},\"response\":{\"status\":200,\"proxyBaseUrl\":\"http://localhost:8084\"}}"');