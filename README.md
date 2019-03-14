# Custom-Hash-Map
Implementing a custom hash map in Java. The main features of this Hash map are as follows:-

1. The Hash map implementation is Thread-safe!
2. It has a custom iterator which iterates over the Key-Value pair in a **Thread-safe** manner!
3. Hash collisions are handled by using a Double-Linked-List.

#### Highlights of the Project
1. Clean coding technique:-
    1. Using right abstractions.
    2. Excellent use of Generic/Parameterized types in Java!
    3. Made sure every class and every method has the right access specifier.
    3. Never-have-I-ever violated the "Tell don't ask principle".
2. Ensuring Thread-safety like a boss! :)
3. Used some really cool Java 8 features.
3. Code is covered by test.

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

```bash
$ gradle clean build
```