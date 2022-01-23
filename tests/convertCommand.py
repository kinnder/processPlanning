from os import replace
from os.path import getsize
from subprocess import check_output
from sys import exit

application: str = 'application-0.2.0.jar'

task_domain: str = 'assemblyLine'

print('convert command test started')

# Решить задачу для AssemblyLine но с собственными именами

check_output(['java',
              '-jar', application,
              '-new_td',
              '-d', task_domain,
              '-td', 'al_td.xml'])

check_output(['java',
              '-jar', application,
              '-new_st',
              '-d', task_domain,
              '-st', 'al_st.xml'])

check_output(['java',
              '-jar', application,
              '-plan',
              '-td', 'al_td.xml',
              '-st', 'al_st.xml',
              '-nn', 'al_nn.xml',
              '-p', 'al_p.xml'])

# Конвертировать файлы в owl-формат

check_output(['java',
              '-jar', application,
              '-convert',
              '-td', 'al_td.xml',
              '-st', 'al_st.xml',
              '-nn', 'al_nn.xml',
              '-p', 'al_p.xml'])

# Переименовать полученные owl-файлы

replace("al_td.owl", "al_converted_td.owl")
replace("al_st.owl", "al_converted_st.owl")
replace("al_nn.owl", "al_converted_nn.owl")
replace("al_p.owl", "al_converted_p.owl")

# Конвертировать файлы в xml-формат

check_output(['java',
              '-jar', application,
              '-convert',
              '-td', 'al_converted_td.owl',
              '-st', 'al_converted_st.owl',
              '-nn', 'al_converted_nn.owl',
              '-p', 'al_converted_p.owl'])

# Сравнить размеры файлов
if getsize('al_td.xml') != getsize('al_converted_td.xml'):
    print('taskDescription conversion failed')
    exit(1)

if getsize('al_st.xml') != getsize('al_converted_st.xml'):
    print('systemTransformations conversion failed')
    exit(1)

if getsize('al_nn.xml') != getsize('al_converted_nn.xml'):
    print('nodeNetwork conversion failed')
    exit(1)

if getsize('al_p.xml') != getsize('al_converted_p.xml'):
    print('process conversion failed')
    exit(1)

print('convert command test completed')
