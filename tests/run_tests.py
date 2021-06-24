import datetime
import os
import pathlib
import platform
import subprocess

tests_location: str = '.\\..\\..\\tests\\'

os.chdir(r'..\application\target')

testNames = [
    'assemblyLine_xml.py',
    'cuttingProcess_xml.py',
    'materialPoints_xml.py',
    'assemblyLine_owl.py',
    'cuttingProcess_owl.py',
    'materialPoints_owl.py']

pathlib.Path(tests_location + "results\\").mkdir(parents=True, exist_ok=True)

for testName in testNames:
    start_time = datetime.datetime.now().time().strftime('%H:%M:%S')
    print(testName + " started : " + start_time)

    subprocess.run(['py', tests_location + testName])

    end_time = datetime.datetime.now().time().strftime('%H:%M:%S')
    print(testName + " finished : " + end_time)

    elapsedTime = (datetime.datetime.strptime(end_time, '%H:%M:%S') - datetime.datetime.strptime(start_time, '%H:%M:%S'))
    print(testName + " elapsed : " + str(elapsedTime))

    currentDate = datetime.datetime.now().date().strftime("%Y-%m-%d")
    with open(tests_location + "results\\" + platform.node() + ".txt", "a") as myfile:
        myfile.write(currentDate + "; " + testName + "; " + str(elapsedTime) + "\n")
