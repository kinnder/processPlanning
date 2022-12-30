java -jar .\application-0.3.0.jar -new_td -d "cuttingProcess" -td "cuttingProcess_td.owl"

java -jar .\application-0.3.0.jar -new_st -d "cuttingProcess" -st "cuttingProcess_st.owl"

java -jar .\application-0.3.0.jar -plan -td "cuttingProcess_td.owl" -st "cuttingProcess_st.owl" -nn "cuttingProcess_nn.owl" -p "cuttingProcess_p.owl"
