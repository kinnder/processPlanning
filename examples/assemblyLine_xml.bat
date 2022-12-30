java -jar .\application-0.3.0.jar -new_td -d "assemblyLine" -td "assemblyLine_td.xml"

java -jar .\application-0.3.0.jar -new_st -d "assemblyLine" -st "assemblyLine_st.xml"

java -jar .\application-0.3.0.jar -plan -td "assemblyLine_td.xml" -st "assemblyLine_st.xml" -nn "assemblyLine_nn.xml" -p "assemblyLine_p.xml"

java -jar .\application-0.3.0.jar -verify -td "assemblyLine_td.xml" -st "assemblyLine_st.xml" -nn "assemblyLine_nn.xml" -p "assemblyLine_p.xml"
