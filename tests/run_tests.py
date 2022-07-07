import datetime
import os
import pathlib
import platform
import subprocess

tests_location: str = '.\\..\\..\\tests\\'

os.chdir(r'..\application\target')

test_names = [
    'assemblyLine_xml.py',
    'cuttingProcess_xml.py',
    'materialPoints_xml.py',
    'assemblyLine_owl.py',
    'cuttingProcess_owl.py',
    'materialPoints_owl.py',
    'convertCommand.py',
    'usageHelp.py']

pathlib.Path(tests_location + "results\\").mkdir(parents=True, exist_ok=True)

tests_passed = True

for test_name in test_names:
    start_time = datetime.datetime.now().time().strftime('%H:%M:%S')
    print(test_name + " started : " + start_time)

    test_passed = True
    try:
        subprocess.run(['py', tests_location + test_name], check=True)
    except (subprocess.CalledProcessError, FileNotFoundError) as exception:
        test_passed = False
        tests_passed = False

    end_time = datetime.datetime.now().time().strftime('%H:%M:%S')
    print(test_name + " finished : " + end_time)

    elapsed_time = (datetime.datetime.strptime(end_time, '%H:%M:%S') - datetime.datetime.strptime(start_time, '%H:%M:%S'))
    print(test_name + " elapsed : " + str(elapsed_time))

    current_date = datetime.datetime.now().date().strftime("%Y-%m-%d")
    with open(tests_location + "results\\" + platform.node() + ".txt", "a") as report_file:
        report_file.write(current_date + "; " + test_name + "; " + str(elapsed_time) + "; " + str(test_passed) + "\n")

if tests_passed:
    print("SUCCESS")
else:
    print("FAILURE")
