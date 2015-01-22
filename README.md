# cdi-test
Run your test with CDI.
Sample:
```java
@RunWith(CdiJUnit4Runner.class)
public class CdiJUnit4RunnerTest {
	private @Inject RequestBean request;
	private @Inject SessionBean session;
	private @Inject ConversationBean conversation;
	private @Inject ApplicationBean application;
	private @Inject SingletonBean singleton;
	
	@Test
	public void test(){
	  //user your bean in test
	}
}
```


To use must be use maven repository:

```xml
<repository>
	<id>mvn-repo-releases</id>
	<url>https://raw.github.com/clairton/mvn-repo.git/releases</url>
</repository>
<repository>
	<id>mvn-repo-snapshot</id>
	<url>https://raw.github.com/clairton/mvn-repo.git/snapshots</url>
</repository>
```
 And add dependency:
```xml
<dependency>
    <groupId>br.eti.clairton</groupId>
	<artifactId>repository</artifactId>
	<version>0.1.0</version>
</dependency>
```
