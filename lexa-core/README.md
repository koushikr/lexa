# Lexa Core

## Capabilities 
  - Create a category graph against a category group
  - Create free data Indexes
  - Update the data in the indexes
  - Perform CRUD using a Query interface on the index.
  
## Key Concepts

  - Category      : A category is a logical grouping of any homogeneous service offering. (Eg: Food, Travel etc) 
  - Manifest      : A manifest is a free data index with a schema which holds onto homogeneous data again to be served to app. A manifest can exist independently regardless of the category. (Eg : Prepaid Recharge Plans, Cities in India, Mobile Operators in India etc)
  - serviceType   : A category group is a logical grouping of one or more categories. Eg : FMCG etc
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

## Build instructions
  - Clone the source:

        git clone github.com/koushikr/jelastic

  - Build

        mvn install
