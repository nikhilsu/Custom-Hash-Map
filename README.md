# Custom-Hash-Map
One of the main features that I wanted to add to my own implementation of a Hash Map was to make it Thread-Safe.
Sure, there is a HashTable in Java that ensures that its operations are Thread-safe, however,
I had not come across a Hash Map that has an iterator that is also Thread-safe!

Thus, here I am, implementing a Hash-Map that is not only Thread-safe, but it also packs an iterator that is Thread-safe too!
The Hash Map is implemented using Generics/Parameterized types, and hence can be made of using any concrete-type in Java. 

The main features of this Hash map are summarized as follows:-

1. All Hash map edit operations are Thread-safe!
2. It has a custom iterator which iterates over the Key-Value pair in a **Thread-safe** manner!
3. Hash collisions are handled by using a Double-Linked-List.

#### Highlights of the Project
1. Clean coding technique:-
    1. Using right abstractions.
    2. Excellent use of Generic/Parameterized types in Java!
    3. Made sure every class and every method has the right access specifier.
    4. Never-have-I-ever violated the "Tell don't ask principle".
2. Ensuring Thread-safety like a boss! :)
3. Used some really cool Java 8 features - Streams, Lambdas, etc.
4. Code is covered by test.
    1. Using JUnit as the testing framework.
    2. Mockito for mocking functionality.
5. The build is automated using Gradle, which according to me is far more readable than Maven and the legendary `pom.xml`file!


#### Time complexity
The time complexity of the Hash lookup on average is O(1). However, since a Linked list is being used to handle hash-collisions. The time complexity in the worst case is O(n)! This can be improved by using better techniques for handling hash-collision. We can use something like, say, Linear-Probing to handle hash-collisions and doubling the size of the hashtable everytime its occupancy increases a certain threshold. This would however involve an overhead of rehashing all the existing keys everytime we double the size.


#### Future Work
1. Implement either of the following(better) techniques to handle hash-collision:-
    1. Linear Probing
    2. Quadratic Probing
    3. Random Probing
    4. Double Hashing?
2. Add more utility methods to the CustomHashMap class.
3. Document each class and method! (I haven't documented it yet because I wanted to see how self-explanatory my code can be and whether my code and/or tests can serve as documentation.)
4. May certain parts of the code more performant!
    1. There were times when I faced the trade-off of performance v/s readability(or reuseability). I chose the latter.
## Getting Started

### Dependencies to be installed

```
Gradle 4.2.1 (to build JAR)
```

#### Building the project
The jar of the hash map will be generated into the folder `build/libs/FullStory.jar`

```
$ gradle clean build
```
To run the tests, use the following command:-

```
$ gradle clean test
``` 

Reports will be generated into the `build/reports/test/index.html` file!
