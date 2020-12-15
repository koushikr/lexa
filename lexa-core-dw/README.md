# Lexa Dropwizard

If you want to use Lexa as part of a dropwizard service, this bundle helps you bind it with dropwizard. 

### Using Lexa bundle

#### Bootstrap
```java
     LexaBundle lexaBundle = new LexaBundle() {
            
            public JElasticConfiguration getElasticConfiguration(T configuration) {
                ...
            }
        }
            
    @Override
    public void initialize(final Bootstrap...) {
        bootstrap.addBundle(lexaBundle);
    }
```

### Maven Dependency 
Shall be added once test sute is complete. WIP!

### No, I'll still build it..

Well, here you go

#### Build instructions
  - Clone the source:

        git clone github.com/koushikr/jelastic

  - Build

        mvn install

### Version support
| jelastic               |  es transport client|
| -----------------------| ------------------- |
| 7.2.0-4                |  7.2.0               |
| 7.2.0-2                |  7.2.0               |

### Configuration
```yaml
jelastic:
  clusterName: elasticsearch
  servers:
    - localhost:9300
  failOnYellow: false
  settingsFile: ~/configs/settings.xml
  maxResultSize: 10000    
```
