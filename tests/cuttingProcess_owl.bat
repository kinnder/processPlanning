java -jar .\application-0.0.1.jar -new_td cuttingProcess -td "cuttingProcess_td.owl"

java -jar .\application-0.0.1.jar -new_st cuttingProcess -st "cuttingProcess_st.owl"

java -jar .\application-0.0.1.jar -plan -td "cuttingProcess_td.owl" -st "cuttingProcess_st.owl" -nn "cuttingProcess_nn.owl" -p "cuttingProcess_p.owl"
