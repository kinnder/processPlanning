java -jar .\application-0.2.0.jar -new_td -d "assemblyLine" -td "assemblyLine_td.owl"

java -jar .\application-0.2.0.jar -new_st -d "assemblyLine" -st "assemblyLine_st.owl"

java -jar .\application-0.2.0.jar -plan -td "assemblyLine_td.owl" -st "assemblyLine_st.owl" -nn "assemblyLine_nn.owl" -p "assemblyLine_p.owl"
