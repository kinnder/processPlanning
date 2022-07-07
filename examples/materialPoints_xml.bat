java -jar .\application-0.2.0.jar -new_td -d "materialPoints" -td "materialPoints_td.xml"

java -jar .\application-0.2.0.jar -new_st -d "materialPoints" -st "materialPoints_st.xml"

java -jar .\application-0.2.0.jar -plan -td "materialPoints_td.xml" -st "materialPoints_st.xml" -nn "materialPoints_nn.xml" -p "materialPoints_p.xml"

java -jar .\application-0.2.0.jar -verify -td "materialPoints_td.xml" -st "materialPoints_st.xml" -nn "materialPoints_nn.xml" -p "materialPoints_p.xml"
