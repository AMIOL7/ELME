[![Actions Status](https://github.com/AMIOL7/ELME/actions/workflows/gradle.yml/badge.svg)](https://github.com/AMIOL7/ELME/actions)

# ELME - Logic Circuit Simulation

**ELME** is a graphical application where interconnected logical components are simulated in a node graph.
The user can create their own nodes from entire graphs, building abstractions upon abstractions that way.

The project's goal is to deliver an easy to use application which can be used to model logical circuits on an infinite canvas. We also find it important to provide a free and open-source alternative to already available commercial or abandoned products.

## Planned features
- Infinite canvas
- Collapsible subgraphs
- Useful built-in nodes
- Human-readable save files

## Downloading and using the software

**Running the application requires [Java 17](https://www.java.com/en/download/manual.jsp) installed on your computer!**

You can acquire a pre-built binary version of the application through our [releases page](https://github.com/AMIOL7/ELME/releases).
Once there, look for the **latest** version marked by the green tag.
Under assets you may choose to download the source code, this readme file, but most importantly **ELME.zip** (or ELME.tar). 
After download unpack the archive and to run the application do the following:

### Linux and mac:
Run through the command line:
```bash
./ELME
``` 

### Windows:
Double click ELME.bat file or type:
```bash
  ELME
```

into the terminal.

Of course if you'd like to use an older version you can find every release on the aforementioned page.

**You can find information on how to use the application on our [wiki](https://github.com/AMIOL7/ELME/wiki) page.**

## Building the project
The entire project is written in **Java 17**, thus in order to build the project **JDK 17** is required.
Furthermore the project uses the **gradle** build system so its also a prerequisite.

The easiest (and recommended) way of building the code is to use an **IDE that supports gradle projects**, such as [NetBeans](https://netbeans.apache.org/), [InteliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/). Usually these IDEs also provide the prerequiste packages mentioned above.

### Building from the command line

Once you clone the project, the main directory contains a build script which can be used to build and run the project.

#### On linux and mac:
```bash
./gradlew tasks 	# list tasks
./gradlew build 	# build the project
./gradlew run 		# run the application
```

#### On windows:
```bash
gradlew tasks 		# list tasks
gradlew build 		# build the project
gradlew run 		# run the application
