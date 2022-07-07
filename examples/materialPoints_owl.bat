java -jar .\application-0.2.0.jar -new_td -d "materialPoints" -td "materialPoints_td.owl"

java -jar .\application-0.2.0.jar -new_st -d "materialPoints" -st "materialPoints_st.owl"

java -jar .\application-0.2.0.jar -plan -td "materialPoints_td.owl" -st "materialPoints_st.owl" -nn "materialPoints_nn.owl" -p "materialPoints_p.owl"
