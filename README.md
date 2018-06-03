# MongoConverter

This application converting sql query into mongodb query.<br />

**How to set up**

For starting this service should download release version of project,install MongoDB and JDK8.<br />
Project use for MongoDB localhost and 27017 port. You can override this settings, load properties file like external for details(https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html).<br />
For starting .jar file use "java -jar <path>" command.<br />

**How to use**

App starting by default on localhost:8080. In query area set your sql query with structure.
```
SELECT [<Projections>] [FROM <Target>]
[WHERE <Condition>*]
[ORDER BY <Fields>* [ASC|DESC] *]
[SKIP <SkipRecords>]
[LIMIT <MaxRecords>]
```
Results shows up in result area.<br />
For adding documents you can also use "/add" endpoint, you need to set collection name and set document but only `ONE`.<br />

**Results**
Document example
```
{
  "name": {
    "first": "John",
    "last": "Backus"
  },
  "contribs": [
    "Fortran",
    "ALGOL",
    "Backus-Naur Form",
    "FP"
  ],
  "awards": [
    {
      "award": "W.W. McDowell Award",
      "year": 1967,
      "by": "IEEE Computer Society"
    }
  ]
}
```
Sql query<br />
```
SELECT [name.first] [FROM test] [WHERE awards.award="W.W. McDowell Award" OR awards.award="Turing Award"] [ORDER BY name [ASC]] [SKIP 2]
[LIMIT 2]
```
Mongo query<br />
```
{$or:[{'awards.award':{$eq:"W.W. McDowell Award"}},{'awards.award':{$eq:"Turing Award"}}]},{'name ':1}
```
Program result<br />
```
[{ "name" : { "first" : "John"}}, { "name" : { "first" : "John"}}]
```

**Travis results**<br />

master ADD: integration test<br />
Commit d570944<br />
Compare db785c4..d570944<br />
Branch master<br />
ned29 authored and committed<br />
