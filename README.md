# ODPi spec tests

This is a prototype implementation of the ODPi runtime specification with gradle+groovy+java

Before running the tests on the ODPi sandbox, it is suggested to apply the patches that make the tests pass.
````shell
cd patches
./applypatches.sh
````

## Quickstart

1. Download sandbox per https://github.com/odpi/sandbox/wiki
2. Start the sandbox
3. ssh to sandbox
4. useradd testuser1
5. su - testuser1
6. git clone https://github.com/jctanner/odpi-spec-tests.git
7. cd odpi-spec-tests
8. ./runtests.sh
