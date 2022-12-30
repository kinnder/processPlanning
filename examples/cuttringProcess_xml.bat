java -jar .\application-0.3.0.jar -new_td -d "cuttingProcess" -td "cuttingProcess_td.xml"

java -jar .\application-0.3.0.jar -new_st -d "cuttingProcess" -st "cuttingProcess_st.xml"

java -jar .\application-0.3.0.jar -plan -td "cuttingProcess_td.xml" -st "cuttingProcess_st.xml" -nn "cuttingProcess_nn.xml" -p "cuttingProcess_p.xml"

java -jar .\application-0.3.0.jar -verify -td "cuttingProcess_td.xml" -st "cuttingProcess_st.xml" -nn "cuttingProcess_nn.xml" -p "cuttingProcess_p.xml"
