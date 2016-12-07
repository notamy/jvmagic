To compile:
```
mvn clean package
```
Only builds a .so file for Linux.

Please don't try to understand the mess of maven submodules that makes this work; you'll probably get a bigger headache than me.

---

Running requires tools.jar to be present.
To run:
```
# Or wherever your tools.jar is located.
java -cp ./jvmagic.jar:/usr/lib/jvm/java-8-jdk/lib/tools.jar date.willnot.amy.jvmagic.JVMagic
```