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
Use the following repository:
```xml
<repository>
    <id>clojars</id>
    <name>Clojars repository</name>
    <url>https://clojars.org/repo</url>
</repository>
```
Use the following maven dependency
```xml
<dependency>
    <groupId>io.github.lexa</groupId>
    <artifactId>lexa-core-dw</artifactId>
    <version>7.2.0-4</version>
</dependency>
```

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
