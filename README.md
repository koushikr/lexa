# Lexa

Lexa is a quick, easy to use CMS written atop [jElastic](https://github.com/koushikr/jelastic)

# Capabilities 
  - Create a category graph against a category group
  - Create free data Indexes
  - Update the data in the indexes
  - Perform CRUD using a Query interface on the index.
  
# Key Concepts

  - Category      : A category is a logical grouping of any homogeneous service offering. (Eg: Food, Travel etc) 
  - Manifest      : A manifest is a free data index with a schema which holds onto homogeneous data again to be served to app. A manifest can exist independently regardless of the category. (Eg : Prepaid Recharge Plans, Cities in India, Mobile Operators in India etc)
  - CategoryGroup : A category group is a logical grouping of one or more categories. Eg : FMCG etc
  - Category Graph : A category graph is the forrest representation of the such category groups!  
  
You can also:
  - Map our category grouping into the manifestation layer's own representation on the frontend. 
  - Can alter the Category Graph at run time. 
  - Can use the query interface to provide with your own set of business APIs - atop our query.  
  
Lexa is a lightweight interface on top of [jElastic](https://github.com/koushikr/jelastic) to support for a generic construction of a category graph and its related entities under the assumption that people naturally need all of these things when building a CMS 

> The overriding design goal for Lexa's
> categoryGroup, category and manifest model syntax is to 
> make it as ready as possible to on-board a new category. 
> The idea is that a formatted category definition is 
> publishable as-is, as an entity to the frontend or any client, 
> without having to create any additional boiler plate code
> to support the same or any further instructions.

### Tech

Lexa uses a number of open source projects to work properly:

* [Dropwizard](https://github.com/dropwizard/dropwizard) - To float the application!
* [Elasticsearch](https://www.elastic.co/) - Data store that is used.
* [RabbitMQ](https://www.rabbitmq.com/) - Messaging that just works.
* [Hazelcast](https://hazelcast.com/) - The in-memory data grid.

### Docker
Lexa is very easy to install and deploy in a Docker container.

By default, the Docker will expose port 8080, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
cd lexa
docker build -t lexa/lexa-server:${package.json.version}
```
This will create the Lexa image and pull in the necessary dependencies. Be sure to swap out `${package.json.version}` with the actual version of Lexa

Once done, run the Docker image and map the port to whatever you wish on your host.
