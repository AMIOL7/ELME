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

## Building the project
The entire project is written in **Java 17**, thus in order to build the project **JDK 17** is required.
Furthermore the project uses the **gradle** build system so its also a prerequisite.

The easiest (and recommended) way of building the code is to use an **IDE that supports gradle projects**, such as [NetBeans](https://netbeans.apache.org/), [InteliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/). Usually these IDEs also provide the prerequiste packages mentioned above.

### Building from the command line

Once you clone the project, the main directory contains a build script which can be used to build and run the project.

#### On linux and mac:
```
./gradlew tasks 	# list tasks
./gradlew build 	# build the project
./gradlew run 		# run the application
```

#### On windows:
```
gradlew tasks 		# list tasks
gradlew build 		# build the project
gradlew run 		# run the application
