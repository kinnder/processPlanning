[![Build Status](https://travis-ci.com/kinnder/process-engineering.svg?branch=master)](https://travis-ci.com/kinnder/process-engineering)
[![codecov](https://codecov.io/gh/kinnder/process-engineering/branch/master/graph/badge.svg?token=ZpKKwI29vY)](https://codecov.io/gh/kinnder/process-engineering)

# process-planning

## Examples of usage

### AssemblyLine OWL
java -jar .\application-0.0.1.jar -new_td assemblyLine -td "assemblyLine_td.owl"

java -jar .\application-0.0.1.jar -new_st assemblyLine -st "assemblyLine_st.owl"

java -jar .\application-0.0.1.jar -plan -td "assemblyLine_td.owl" -st "assemblyLine_st.owl" -nn "assemblyLine_nn.owl" -p "assemblyLine_p.owl"

### AssemblyLine XML
java -jar .\application-0.0.1.jar -new_td assemblyLine -td "assemblyLine_td.xml"

java -jar .\application-0.0.1.jar -new_st assemblyLine -st "assemblyLine_st.xml"

java -jar .\application-0.0.1.jar -plan -td "assemblyLine_td.xml" -st "assemblyLine_st.xml" -nn "assemblyLine_nn.xml" -p "assemblyLine_p.xml"

### CuttingProcess OWL
java -jar .\application-0.0.1.jar -new_td cuttingProcess -td "cuttingProcess_td.owl"

java -jar .\application-0.0.1.jar -new_st cuttingProcess -st "cuttingProcess_st.owl"

java -jar .\application-0.0.1.jar -plan -td "cuttingProcess_td.owl" -st "cuttingProcess_st.owl" -nn "cuttingProcess_nn.owl" -p "cuttingProcess_p.owl"

### CuttingProcess XML
java -jar .\application-0.0.1.jar -new_td cuttingProcess -td "cuttingProcess_td.xml"

java -jar .\application-0.0.1.jar -new_st cuttingProcess -st "cuttingProcess_st.xml"

java -jar .\application-0.0.1.jar -plan -td "cuttingProcess_td.xml" -st "cuttingProcess_st.xml" -nn "cuttingProcess_nn.xml" -p "cuttingProcess_p.xml"

### MaterialPoints OWL
java -jar .\application-0.0.1.jar -new_td materialPoints -td "materialPoints_td.owl"

java -jar .\application-0.0.1.jar -new_st materialPoints -st "materialPoints_st.owl"

java -jar .\application-0.0.1.jar -plan -td "materialPoints_td.owl" -st "materialPoints_st.owl" -nn "materialPoints_nn.owl" -p "materialPoints_p.owl"

### MaterialPoints XML
java -jar .\application-0.0.1.jar -new_td materialPoints -td "materialPoints_td.xml"

java -jar .\application-0.0.1.jar -new_st materialPoints -st "materialPoints_st.xml"

java -jar .\application-0.0.1.jar -plan -td "materialPoints_td.xml" -st "materialPoints_st.xml" -nn "materialPoints_nn.xml" -p "materialPoints_p.xml"
