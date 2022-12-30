import sys
from subprocess import check_output

application: str = 'application-0.3.0.jar'

test_name: str = 'usageHelp'

test_result: int

print(test_name + ' started')

actual_output = check_output(['java', '-jar', application, '-h'])

expected_output = b'application builds plan for [taskDescription] with [systemTransformations] and puts result in [' \
                  b'process]\nusage:\n      h, help                       prints usage\n     td, task-description     ' \
                  b'      file with description of the task\n     st, system-transformations     file with ' \
                  b'description of the system transformations\n      p, process                    output file with ' \
                  b'process\n     nn, node-network               output file with node network\n   plan,              ' \
                  b'              plan process\n new_st, new-system-transformations create new file with predefined ' \
                  b'system transformations\n new_td, new-task-description       create new file with predefined task ' \
                  b'description\n verify,                            verify xml-files with according ' \
                  b'xml-schemas\nconvert,                            convert files between formats: xml to owl and ' \
                  b'owl to xml\n    gui,                            show graphical user interface\n      d, ' \
                  b'domain                     defines domain for predefined data: materialPoints, cuttingProcess or ' \
                  b'assemblyLine\n\r\n'

if actual_output == expected_output:
    test_result = 0
else:
    print('fail')
    print('actual   : ' + str(actual_output))
    print('expected : ' + str(expected_output))
    test_result = 1

print(test_name + ' completed')
sys.exit(test_result)
