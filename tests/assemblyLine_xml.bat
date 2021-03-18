java -jar .\application-0.0.1.jar -new_td assemblyLine -td "assemblyLine_td.xml"

java -jar .\application-0.0.1.jar -new_st assemblyLine -st "assemblyLine_st.xml"

java -jar .\application-0.0.1.jar -plan -td "assemblyLine_td.xml" -st "assemblyLine_st.xml" -nn "assemblyLine_nn.xml" -p "assemblyLine_p.xml"
