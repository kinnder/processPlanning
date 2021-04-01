java -jar .\application-0.0.1.jar -new_td assemblyLine -td "assemblyLine_td.owl"

java -jar .\application-0.0.1.jar -new_st assemblyLine -st "assemblyLine_st.owl"

java -jar .\application-0.0.1.jar -plan -td "assemblyLine_td.owl" -st "assemblyLine_st.owl" -nn "assemblyLine_nn.owl" -p "assemblyLine_p.owl"
