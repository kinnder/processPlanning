import os
import subprocess

tests_location: str = '.\\..\\..\\tests\\'

os.chdir(r'..\application\target')

subprocess.run(['py', tests_location + 'assemblyLine_xml.py'])
subprocess.run(['py', tests_location + 'cuttingProcess_xml.py'])
