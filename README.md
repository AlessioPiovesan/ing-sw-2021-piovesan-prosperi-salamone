# Masters Of Renaissance Board Game

<img src="https://craniointernational.com/wp-content/uploads/2019/06/Masters-Of-Renaissance_scatola_ENG-e1560246260470.png" width="192" height="192" align="right" alt="Masters of Renaissance Box"/>

_**Masters Of Renaissance**_ is the final project for the course **Software Engineering** (2020/2021) in the **Computer Science Engineering** program at Politecnico di Milano.

**Instructor**: Gianpaolo Cugola  
**Final Score**: 23

## Project Overview

This project is a Java implementation of the board game *Masters Of Renaissance* by Cranio Games. The full game rules and details can be found [here](http://craniointernational.com/products/masters-of-renaissance/).

### Deliverables:

- Initial UML diagram
- Final UML diagram (generated from the code)
- Complete game implementation, compliant with the rules
- Source code of the game
- Source code for unit tests

## How to Get Started

| [Installation][installation-link] | [Compiling][compiling-link] | [Running][running-link] | [Javadocs][javadocs]       | [Troubleshooting][troubleshooting-link] |
|-----------------------------------|-----------------------------|-------------------------|----------------------------|-----------------------------------------|
| ![Install][installation-image]    | ![Compile][compiling-image] | ![Run][running-image]   | ![Javadoc][javadocs-image] | ![Help][troubleshooting-image]          |

## Implemented Features

| Feature                      | Status                        |
|------------------------------|-------------------------------|
| Basic rules                  | ![GREEN](.github/44bb44.png)  |
| Complete rules               | ![GREEN](.github/44bb44.png)  |
| Socket support               | ![GREEN](.github/44bb44.png)  |
| GUI                          | ![YELLOW](.github/ffdd00.png) |
| CLI                          | ![GREEN](.github/44bb44.png)  |
| Multiple games               | ![GREEN](.github/44bb44.png)  |
| Single player                | ![GREEN](.github/44bb44.png)  |
| Resilience to disconnections | ![GREEN](.github/44bb44.png)  |
| Editor                       | ![RED](.github/f03c15.png)    |
| Persistence                  | ![RED](.github/f03c15.png)    |

### Legend

- ![GREEN](.github/44bb44.png) Implemented
- ![YELLOW](.github/ffdd00.png) In progress
- ![RED](.github/f03c15.png) Not implemented

## Test Coverage

The project is tested following the "methods coverage" criterion.

| Package     | Tested Class | Method Coverage |
|-------------|--------------|-----------------|
| Accessories | Other        | 54/57 (95%)     |
| Controller  | Controller   | 19/31 (61%)     |
| Model       | Board        | 52/73 (71%)     |
| Model       | Game         | 23/40 (57%)     |

## The Team - GC54

- **Matteo Salamone** (10606104) - [@matoss21](https://github.com/matoss21)  
  <a href="mailto:matteo.salamone@mail.polimi.it">matteo.salamone@mail.polimi.it</a>

- **Pier Giorgio Prosperi** (10607931) - [@piergiorgioprosperi](https://github.com/piergiorgioprosperi)  
  <a href="mailto:piergiorgio.prosperi@mail.polimi.it">piergiorgio.prosperi@mail.polimi.it</a>

- **Alessio Piovesan** (10279689) - [@AlessioPiovesan](https://github.com/AlessioPiovesan)  
  <a href="mailto:alessio.piovesan@mail.polimi.it">alessio.piovesan@mail.polimi.it</a>

## Tools Used

- **Draw.io** for UML diagrams
- **IntelliJ IDEA Ultimate** as the main IDE

<!-- Links to images and pages -->
[javadocs-image]: .github/Artboard%202.png
[javadocs]: https://AlessioPiovesan.github.io/MastersOfRenaissance-javadocs/
[installation-link]: https://github.com/piergiorgioprosperi/ing-sw-2021-piovesan-prosperi-salamone/wiki/Installation
[installation-image]: .github/Artboard%201.png
[compiling-image]: .github/Artboard%203.png
[compiling-link]: https://github.com/piergiorgioprosperi/ing-sw-2021-piovesan-prosperi-salamone/Compiling
[running-image]: .github/Artboard%204.png
[running-link]: https://github.com/piergiorgioprosperi/ing-sw-2021-piovesan-prosperi-salamone/wiki/Running
[troubleshooting-link]: https://github.com/piergiorgioprosperi/ing-sw-2021-piovesan-prosperi-salamone/wiki/Troubleshooting
[troubleshooting-image]: .github/Artboard%205.png
