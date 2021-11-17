from subprocess import check_output

application: str = 'application-0.1.0.jar'

task_domain: str = 'assemblyLine'

file_format: str = 'owl'

task_description_file: str = task_domain + '_td.' + file_format
system_transformation_file: str = task_domain + '_st.' + file_format
node_network_file: str = task_domain + '_nn.' + file_format
process_file: str = task_domain + '_p.' + file_format

print(task_domain + ' ' + file_format + ' started')

check_output(['java',
              '-jar', application,
              '-new_td', task_domain,
              '-td', task_description_file])

check_output(['java',
              '-jar', application,
              '-new_st', task_domain,
              '-st', system_transformation_file])

check_output(['java',
              '-jar', application,
              '-plan',
              '-td', task_description_file,
              '-st', system_transformation_file,
              '-nn', node_network_file,
              '-p', process_file])

with open(task_description_file):
    pass

with open(system_transformation_file):
    pass

with open(node_network_file):
    pass

with open(process_file):
    pass

print(task_domain + ' ' + file_format + ' completed')
