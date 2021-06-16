import os
import subprocess

os.chdir(r'..\application\target')

subprocess.run(['py', r'.\..\..\tests\assemblyLine_xml.py'])
