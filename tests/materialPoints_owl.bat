java -jar .\application-0.0.1.jar -new_td materialPoints -td "materialPoints_td.owl"

java -jar .\application-0.0.1.jar -new_st materialPoints -st "materialPoints_st.owl"

java -jar .\application-0.0.1.jar -plan -td "materialPoints_td.owl" -st "materialPoints_st.owl" -nn "materialPoints_nn.owl" -p "materialPoints_p.owl"
